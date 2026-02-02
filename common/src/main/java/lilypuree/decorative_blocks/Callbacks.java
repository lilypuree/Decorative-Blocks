package lilypuree.decorative_blocks;

import lilypuree.decorative_blocks.blocks.BonfireBlock;
import lilypuree.decorative_blocks.blocks.SupportBlock;
import lilypuree.decorative_blocks.fluid.ThatchBlock;
import lilypuree.decorative_blocks.mixin.ItemAccessor;
import lilypuree.decorative_blocks.platform.Services;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class Callbacks {


    //return true if cancel left click
    public static boolean onLeftClick(Level level, BlockPos pos) {
        Block block = level.getBlockState(pos).getBlock();
        if (block instanceof BonfireBlock) {
            level.levelEvent(null, 1009, pos, 0);
            level.removeBlock(pos, false);
            return true;
        }
        return false;
    }

    public static InteractionResultHolder<ItemStack> onUseItem(Level level, Player player, ItemStack item) {
        if (item.is(ItemTags.HOES)) {
            return pickupThatch(level, player, item);
        }
        return InteractionResultHolder.pass(item);
    }

    public static InteractionResult onRightClickBlock(Player player, Level level, ItemStack item, BlockHitResult hitResult) {
        BlockPos pos = hitResult.getBlockPos();
        BlockState state = level.getBlockState(pos);
        Block block = state.getBlock();

        if (item.is(Services.PLATFORM.getShearTag()) && CommonAPI.shearMap.containsKey(block)) {
            return shearThatch(player, level, item, pos, block);
        } else if (item.is(ItemTags.AXES) && block instanceof SupportBlock) {
            SupportBlock.onSupportActivation(state, level, pos, player, hitResult.getLocation());
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return InteractionResult.PASS;
    }


    private static InteractionResult shearThatch(Player player, Level level, ItemStack itemStack, BlockPos pos, Block block) {
        if (!level.getGameRules().getBoolean(CommonAPI.RULE_DISABLE_THATCH)) {
            level.setBlockAndUpdate(pos, CommonAPI.shearMap.get(block).getLiquidBlock().defaultBlockState());
            level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
            player.playSound(SoundEvents.CROP_BREAK, 1.2F, 1.0F);
            itemStack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return InteractionResult.PASS;
    }

    private static InteractionResultHolder<ItemStack> pickupThatch(Level level, Player player, ItemStack item) {
        BlockHitResult blockHitResult = ItemAccessor.getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
        if (blockHitResult.getType() == HitResult.Type.BLOCK) {
            BlockPos hit = blockHitResult.getBlockPos();
            Direction dir = blockHitResult.getDirection();
            BlockPos thatchPos = hit.relative(dir);

            if (level.mayInteract(player, hit) && player.mayUseItemAt(thatchPos, dir, item)) {
                BlockState hitBlock = level.getBlockState(hit);
                if (hitBlock.getBlock() instanceof ThatchBlock) {
                    if (hitBlock.getValue(LiquidBlock.LEVEL) == 0) {
                        player.playSound(SoundEvents.CROP_BREAK, 1.2f, 1.0F);
                        level.gameEvent(player, GameEvent.BLOCK_DESTROY, hit);
                        level.setBlock(hit, Blocks.AIR.defaultBlockState(), 11);
                        return InteractionResultHolder.sidedSuccess(item, level.isClientSide);
                    }
                }
            }
        }
        return InteractionResultHolder.pass(item);
    }
}
