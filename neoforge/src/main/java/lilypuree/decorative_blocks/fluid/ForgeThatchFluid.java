package lilypuree.decorative_blocks.fluid;

import net.minecraft.sounds.SoundEvents;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.FluidType;

public class ForgeThatchFluid {
    private static final FluidType.Properties FLUID_PROPERTIES = FluidType.Properties.create()
            .sound(SoundActions.BUCKET_FILL, SoundEvents.CROP_BREAK)
            .sound(SoundActions.BUCKET_EMPTY, SoundEvents.CROP_BREAK)
            .density(200)
            .viscosity(2000)
            .motionScale(1)
            .fallDistanceModifier(1)
            .canPushEntity(false)
            .canSwim(false)
            .canDrown(false);

    public static class Flowing extends ThatchFluid.Flowing{
        private final FluidType fluidType;

        public Flowing(FluidReferenceHolder referenceHolder) {
            super(referenceHolder);
            this.fluidType = new DBFluidType(FLUID_PROPERTIES, referenceHolder.thatchStillTexture(), referenceHolder.thatchFlowingTexture(), referenceHolder.thatchStillTexture(), referenceHolder.color());
        }

        @Override
        public FluidType getFluidType() {
            return this.fluidType;
        }
    }

    public static class Source extends ThatchFluid.Source{
        private final FluidType fluidType;

        public Source(FluidReferenceHolder referenceHolder) {
            super(referenceHolder);
            this.fluidType = new DBFluidType(FLUID_PROPERTIES, referenceHolder.thatchStillTexture(), referenceHolder.thatchFlowingTexture(), referenceHolder.thatchStillTexture(), referenceHolder.color());
        }

        @Override
        public FluidType getFluidType() {
            return fluidType;
        }



    }
}
