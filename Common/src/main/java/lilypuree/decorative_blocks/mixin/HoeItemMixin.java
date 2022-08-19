package lilypuree.decorative_blocks.mixin;

import lilypuree.decorative_blocks.fluid.ThatchBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HoeItem.class)
public abstract class HoeItemMixin extends DiggerItem {

    protected HoeItemMixin(float $$0, float $$1, Tier $$2, TagKey<Block> $$3, Properties $$4) {
        super($$0, $$1, $$2, $$3, $$4);
    }

    @Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
    private void whenUsed(UseOnContext ctx, CallbackInfoReturnable<InteractionResult> cir) {
        Level level = ctx.getLevel();
        Player player = ctx.getPlayer();
        HitResult rayTraceResult = Item.getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
        if (rayTraceResult.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockHitResult = (BlockHitResult) rayTraceResult;
            BlockPos hit = blockHitResult.getBlockPos();
            Direction dir = blockHitResult.getDirection();
            BlockPos thatchPos = hit.relative(dir);

            if (level.mayInteract(player, hit) && player.mayUseItemAt(thatchPos, dir, ctx.getItemInHand())) {
                BlockState hitBlock = level.getBlockState(hit);
                if (hitBlock.getBlock() instanceof ThatchBlock) {
                    if (hitBlock.getValue(LiquidBlock.LEVEL) == 0) {
                        if (level.isClientSide()) {
                            player.swing(ctx.getHand());
                        } else {
                            level.playSound(null, hit, SoundEvents.CROP_BREAK, SoundSource.BLOCKS, 1.2f, 1.0f);
                            level.setBlock(hit, Blocks.AIR.defaultBlockState(), 11);
                        }
                    }
                    cir.setReturnValue(InteractionResult.sidedSuccess(level.isClientSide));
                }
            }
        }
    }
}
