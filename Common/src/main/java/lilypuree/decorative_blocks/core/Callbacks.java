package lilypuree.decorative_blocks.core;

import lilypuree.decorative_blocks.CommonAPI;
import lilypuree.decorative_blocks.blocks.BonfireBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class Callbacks {


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
            } else if (!world.getGameRules().getBoolean(CommonAPI.RULE_DISABLE_THATCH)) {
                world.setBlockAndUpdate(pos, CommonAPI.shearMap.get(block).getLiquidBlock().defaultBlockState());
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

}
