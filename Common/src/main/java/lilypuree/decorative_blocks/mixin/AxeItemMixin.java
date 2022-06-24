package lilypuree.decorative_blocks.mixin;

import lilypuree.decorative_blocks.blocks.SupportBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(AxeItem.class)
public class AxeItemMixin extends DiggerItem {

    protected AxeItemMixin(float $$0, float $$1, Tier $$2, TagKey<Block> $$3, Properties $$4) {
        super($$0, $$1, $$2, $$3, $$4);
    }
    @Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
    public void useOn(UseOnContext ctx, CallbackInfoReturnable<InteractionResult> cir) {
        Level level = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();
        BlockState state = level.getBlockState(pos);
        if (state.getBlock() instanceof SupportBlock) {
            SupportBlock.onSupportActivation(state, level, pos, ctx.getPlayer(), ctx.getClickLocation());
            cir.setReturnValue(InteractionResult.sidedSuccess(level.isClientSide));
        }
    }
}
