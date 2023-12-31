package lilypuree.decorative_blocks.core;

import lilypuree.decorative_blocks.Constants;
import lilypuree.decorative_blocks.entity.DummyEntityForSitting;
import lilypuree.decorative_blocks.fluid.ThatchFluid;
import lilypuree.decorative_blocks.platform.Services;
import lilypuree.decorative_blocks.registration.RegistrationProvider;
import lilypuree.decorative_blocks.registration.RegistryObject;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;

public class Registration {

    private static final RegistrationProvider<EntityType<?>> ENTITY_REGISTRY = RegistrationProvider.get(Registries.ENTITY_TYPE, Constants.MOD_ID);
    private static final RegistrationProvider<Fluid> FLUID_REGISTRY = RegistrationProvider.get(Registries.FLUID, Constants.MOD_ID);
    private static final RegistrationProvider<CreativeModeTab> CREATIVE_MODE_TAB_REGISTRY = RegistrationProvider.get(Registries.CREATIVE_MODE_TAB, Constants.MOD_ID);

    private static final ResourceLocation thatchStillTexture = new ResourceLocation(Constants.MOD_ID, "block/thatch_still");
    private static final ResourceLocation thatchFlowingTexture = new ResourceLocation(Constants.MOD_ID, "block/thatch_flowing");

    public static final ThatchFluid.FluidReferenceHolder referenceHolder;
    public static final RegistryObject<FlowingFluid> FLOWING_THATCH;
    public static final RegistryObject<FlowingFluid> STILL_THATCH;

    public static final RegistryObject<CreativeModeTab> ITEM_GROUP;

    static {
        //using lambdas, not method references which will access the field values of FLOWING_THATCH and STILL_THATCH which are still null.
        referenceHolder = new ThatchFluid.FluidReferenceHolder(() -> Blocks.HAY_BLOCK, () -> DBBlocks.THATCH.get(), () -> Registration.FLOWING_THATCH.get(), () -> Registration.STILL_THATCH.get(), thatchStillTexture, thatchFlowingTexture, 0xAC8D08);
        FLOWING_THATCH = FLUID_REGISTRY.register("flowing_thatch", () -> Services.PLATFORM.createThatchFlowingFluid(referenceHolder));
        STILL_THATCH = FLUID_REGISTRY.register("thatch", () -> Services.PLATFORM.createThatchStillFluid(referenceHolder));
        ITEM_GROUP = CREATIVE_MODE_TAB_REGISTRY.register("general", Registration::getTab);
    }

    public static RegistryObject<EntityType<DummyEntityForSitting>> DUMMY_ENTITY_TYPE = ENTITY_REGISTRY.register("dummy_entity", () -> EntityType.Builder.of(Services.PLATFORM::createDummyEntity, MobCategory.MISC)
            .clientTrackingRange(256)
            .updateInterval(20)
            .sized(0.0001F, 0.0001F)
            .build(Constants.MOD_ID + ":dummy"));

    public static void init() {
    }

    private static CreativeModeTab getTab() {
        CreativeModeTab.Builder builder = Services.PLATFORM.createModTab();
        builder.icon(() -> DBItems.BRAZIER.get().getDefaultInstance()).title(Component.translatable(String.format("itemGroup.%s.general", Constants.MOD_ID)));
        builder.displayItems((itemDisplayParameters, output) -> {
            output.accept(DBItems.CHANDELIER::get);
            output.accept(DBItems.SOUL_CHANDELIER::get);
            output.accept(DBItems.BRAZIER::get);
            output.accept(DBItems.SOUL_BRAZIER::get);
            output.accept(DBItems.BAR_PANEL::get);
            output.accept(DBItems.LATTICE::get);
            output.accept(DBItems.CHAIN::get);
            output.accept(DBItems.STONE_PILLAR::get);
            output.accept(DBItems.ROCKY_DIRT::get);
            DBItems.BEAM_ITEMBLOCKS.values().forEach(regObject -> output.accept(regObject::get));
            DBItems.SEAT_ITEMBLOCKS.values().forEach(regObject -> output.accept(regObject::get));
            DBItems.SUPPORT_ITEMBLOCKS.values().forEach(regObject -> output.accept(regObject::get));
            DBItems.PALISADE_ITEMBLOCKS.values().forEach(regObject -> output.accept(regObject::get));
        });
        return builder.build();
    }
}
