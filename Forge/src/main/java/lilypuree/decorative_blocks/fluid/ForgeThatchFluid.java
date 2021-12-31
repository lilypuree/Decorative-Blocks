package lilypuree.decorative_blocks.fluid;

import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.fluids.FluidAttributes;

public class ForgeThatchFluid {
    public static class Flowing extends ThatchFluid.Flowing{
        public Flowing(FluidReferenceHolder referenceHolder) {
            super(referenceHolder);
        }
        @Override
        protected FluidAttributes createAttributes() {
            return FluidAttributes.builder(referenceHolder.thatchStillTexture, referenceHolder.thatchFlowingTexture).overlay(referenceHolder.thatchStillTexture).sound(SoundEvents.CROP_BREAK, SoundEvents.CROP_BREAK).density(200).viscosity(2000).build(this);
        }
    }

    public static class Source extends ThatchFluid.Source{
        public Source(FluidReferenceHolder referenceHolder) {
            super(referenceHolder);
        }
        @Override
        protected FluidAttributes createAttributes() {
            return FluidAttributes.builder(referenceHolder.thatchStillTexture, referenceHolder.thatchFlowingTexture).overlay(referenceHolder.thatchStillTexture).sound(SoundEvents.CROP_BREAK, SoundEvents.CROP_BREAK).density(200).viscosity(2000).build(this);
        }
    }
}
