package lilypuree.decorative_blocks.mixin;

import lilypuree.decorative_blocks.registration.DBBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.BlockStateBase.class)
public class BlockStateBaseMixin {
    @Inject(method = "getOffset", at = @At("HEAD"), cancellable = true)
    public void onGetOffset(BlockGetter level, BlockPos pos, CallbackInfoReturnable<Vec3> cir) {
        if (level.getBlockState(pos.below()).is(DBBlocks.TABLE_POT.get())) {
            cir.setReturnValue(Vec3.ZERO);
        }
    }
}
