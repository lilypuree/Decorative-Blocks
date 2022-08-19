package lilypuree.decorative_blocks.core;

import lilypuree.decorative_blocks.Constants;
import lilypuree.decorative_blocks.entity.DummyEntityForSitting;
import lilypuree.decorative_blocks.fluid.ThatchFluid;
import lilypuree.decorative_blocks.platform.Services;
import lilypuree.decorative_blocks.registration.RegistrationProvider;
import lilypuree.decorative_blocks.registration.RegistryObject;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;

public class Registration {

    private static final RegistrationProvider<EntityType<?>> ENTITY_REGISTRY = RegistrationProvider.get(Registry.ENTITY_TYPE, Constants.MODID);
    private static final RegistrationProvider<Fluid> FLUID_REGISTRY = RegistrationProvider.get(Registry.FLUID, Constants.MODID);
    private static final ResourceLocation thatchStillTexture = new ResourceLocation(Constants.MODID, "block/thatch_still");
    private static final ResourceLocation thatchFlowingTexture = new ResourceLocation(Constants.MODID, "block/thatch_flowing");

    public static final ThatchFluid.FluidReferenceHolder referenceHolder;
    public static final RegistryObject<FlowingFluid> FLOWING_THATCH;
    public static final RegistryObject<FlowingFluid> STILL_THATCH;

    static {
        //using lambdas, not method references which will access the field values of FLOWING_THATCH and STILL_THATCH which are still null.
        referenceHolder = new ThatchFluid.FluidReferenceHolder(() -> Blocks.HAY_BLOCK, () -> DBBlocks.THATCH.get(), () -> Registration.FLOWING_THATCH.get(), () -> Registration.STILL_THATCH.get(), thatchStillTexture, thatchFlowingTexture, 0xAC8D08);
        FLOWING_THATCH = FLUID_REGISTRY.register("flowing_thatch", () -> Services.PLATFORM.createThatchFlowingFluid(referenceHolder));
        STILL_THATCH = FLUID_REGISTRY.register("thatch", () -> Services.PLATFORM.createThatchStillFluid(referenceHolder));
    }

    public static RegistryObject<EntityType<DummyEntityForSitting>> DUMMY_ENTITY_TYPE = ENTITY_REGISTRY.register("dummy_entity", () -> EntityType.Builder.of(Services.PLATFORM::createDummyEntity, MobCategory.MISC)
            .clientTrackingRange(256)
            .updateInterval(20)
            .sized(0.0001F, 0.0001F)
            .build(Constants.MODID + ":dummy"));

    public static void init() {
    }
}
