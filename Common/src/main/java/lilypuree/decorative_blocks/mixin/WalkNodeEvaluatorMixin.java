package lilypuree.decorative_blocks.mixin;

import lilypuree.decorative_blocks.blocks.BrazierBlock;
import lilypuree.decorative_blocks.registration.DBTags;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WalkNodeEvaluator.class)
public class WalkNodeEvaluatorMixin {

    @Inject(method = "isBurningBlock", at = @At("HEAD"), cancellable = true)
    private static void onCheckBurningBlock(BlockState block, CallbackInfoReturnable<Boolean> cir) {
        if (DBTags.Blocks.BONFIRES != null){  //Tagkeys are initialized
            if (BrazierBlock.isLitBrazier(block)) cir.setReturnValue(true);
            if (block.is(DBTags.Blocks.BONFIRES)) cir.setReturnValue(true);
        }
    }
}
