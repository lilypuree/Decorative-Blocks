package lilypuree.decorative_blocks.core.setup;

import lilypuree.decorative_blocks.CommonAPI;
import lilypuree.decorative_blocks.CommonConfig;
import lilypuree.decorative_blocks.blocks.*;
import lilypuree.decorative_blocks.core.DBBlocks;
import lilypuree.decorative_blocks.core.DBItems;
import lilypuree.decorative_blocks.core.Registration;
import lilypuree.decorative_blocks.items.BlockstateCopyItem;
import lilypuree.decorative_blocks.mixin.FireBlockInvoker;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class ModSetup {

    public static void init() {
        CommonAPI.addThatchlikeFluid(Registration.referenceHolder);
        CommonAPI.bonfireMap.put(Blocks.FIRE, DBBlocks.BONFIRE);
        CommonAPI.bonfireMap.put(Blocks.SOUL_FIRE, DBBlocks.SOUL_BONFIRE);

        Registry.BLOCK.forEach(block -> {
            if (block instanceof PalisadeBlock) {
                BlockstateCopyItem.addProperties(block, PalisadeBlock.NORTH, PalisadeBlock.EAST, PalisadeBlock.SOUTH, PalisadeBlock.WEST);
            } else if (block instanceof SeatBlock) {
                BlockstateCopyItem.addProperties(block, SeatBlock.FACING, SeatBlock.POST, SeatBlock.ATTACHED);
            } else if (block instanceof SupportBlock) {
                BlockstateCopyItem.addProperties(block, SupportBlock.HORIZONTAL_SHAPE, SupportBlock.VERTICAL_SHAPE, SupportBlock.FACING, SupportBlock.UP);
            }
        });

        FireBlockInvoker invoker = ((FireBlockInvoker)((Object) Blocks.FIRE));
        Registry.BLOCK.forEach(block -> {
            if (block instanceof IWoodenBlock woodenBlock) {
                if (woodenBlock.getWoodType().isFlammable()) {
                    invoker.invokeSetFlammable(block, 5, 20);
                }
            }
        });
        invoker.invokeSetFlammable(DBBlocks.LATTICE, 5, 20);
        invoker.invokeSetFlammable(Registration.THATCH, 60, 80);
    }


    //return true if cancel left click
    public static boolean onLeftClick(Level world, BlockPos pos) {
        Block block = world.getBlockState(pos).getBlock();
        if (block instanceof BonfireBlock) {
            world.levelEvent(null, 1009, pos, 0);
            world.removeBlock(pos, false);
            return true;
        }
        return false;
    }

    /**
     * @return 0: no action
     * -2 : cancel right click
     * -1 : deny block use
     * 1 : allow block use
     */
    public static int onRightClick(Level world, Player player, InteractionHand hand, ItemStack itemStack, BlockPos pos) {
        if (!world.hasChunkAt(pos)) {
            return 0;
        }
        Block block = world.getBlockState(pos).getBlock();
        Item item = itemStack.getItem();
        if (item == Items.SHEARS && CommonAPI.shearMap.containsKey(block)) {
            if (world.isClientSide) {
                player.swing(hand);
            } else if (CommonConfig.THATCH_ENABLED) {
                world.setBlockAndUpdate(pos, CommonAPI.shearMap.get(block).getFluidBlock().defaultBlockState());
                world.playSound(null, pos, SoundEvents.CROP_BREAK, SoundSource.BLOCKS, 1.2f, 1.0f);
                itemStack.hurtAndBreak(1, player, (entity) -> {
                    entity.broadcastBreakEvent(EquipmentSlot.MAINHAND);
                });
            }
        }
        if (item == DBItems.BLOCKSTATE_COPY_ITEM) {
            return -1;
        }
        return 0;
    }


    public static boolean isBonfireActivator(ItemStack item) {
        if (bonfireActivatorItem == null) {
            checkBonfireActivatorConfig();
            return isBonfireActivator(item);
        }
        return bonfireActivatorItem != Items.AIR
                && bonfireActivatorItem == item.getItem();
    }

    public static boolean sendMessageOnThrow(Player player, ItemEntity thrown) {
        if (bonfireActivatorItem == null) {
            if (!checkBonfireActivatorConfig()) {
                player.sendMessage(new TranslatableComponent("message.decorative_blocks.invalid_bonfire_activator_config"), player.getUUID());
                return false;
            }
        }
        return isBonfireActivator(thrown.getItem());
    }

    private static Item bonfireActivatorItem = null;

    public static boolean checkBonfireActivatorConfig() {
        ResourceLocation bonfireActivatorResourceLocation = ResourceLocation.tryParse(CommonConfig.BONFIRE_ACTIVATOR);
        if (bonfireActivatorResourceLocation != null) {
            if (Registry.ITEM.containsKey(bonfireActivatorResourceLocation)) {
                bonfireActivatorItem = Registry.ITEM.get(bonfireActivatorResourceLocation);
                return true;
            }
        }
        bonfireActivatorItem = Items.AIR;
        return false;
    }

}
