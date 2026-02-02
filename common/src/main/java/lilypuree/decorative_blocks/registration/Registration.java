package lilypuree.decorative_blocks.registration;

import lilypuree.decorative_blocks.Constants;
import lilypuree.decorative_blocks.entity.DummyEntityForSitting;
import lilypuree.decorative_blocks.fluid.ThatchFluid;
import lilypuree.decorative_blocks.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.Supplier;

public class Registration {
    private static final ResourceLocation thatchStillTexture = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "block/thatch_still");
    private static final ResourceLocation thatchFlowingTexture = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "block/thatch_flowing");

    public static final ThatchFluid.FluidReferenceHolder referenceHolder;
    public static final Supplier<ThatchFluid.Flowing> FLOWING_THATCH;
    public static final Supplier<ThatchFluid.Source> STILL_THATCH;
    public static Supplier<LiquidBlock> THATCH_BLOCK;

    public static final Supplier<CreativeModeTab> ITEM_GROUP;

    static {
        ITEM_GROUP = Services.PLATFORM.register(BuiltInRegistries.CREATIVE_MODE_TAB, "general", Registration::getTab);

        BlockBehaviour.Properties thatchProperties = Block.Properties.of().liquid().replaceable().noCollission().randomTicks().noLootTable()
                .mapColor(MapColor.COLOR_YELLOW).pushReaction(PushReaction.DESTROY).strength(100.0F);

        //using lambdas, not method references which will access the field values of FLOWING_THATCH and STILL_THATCH which are still null.
        referenceHolder = new ThatchFluid.FluidReferenceHolder(() -> Blocks.HAY_BLOCK, () -> Registration.THATCH_BLOCK.get(), () -> Registration.FLOWING_THATCH.get(), () -> Registration.STILL_THATCH.get(), thatchStillTexture, thatchFlowingTexture, 0xAC8D08);
        FLOWING_THATCH = Services.PLATFORM.register(BuiltInRegistries.FLUID, "flowing_thatch", () -> Services.PLATFORM.createThatchFlowingFluid(referenceHolder));
        STILL_THATCH = Services.PLATFORM.register(BuiltInRegistries.FLUID, "thatch", () -> Services.PLATFORM.createThatchStillFluid(referenceHolder));
        THATCH_BLOCK = Services.PLATFORM.register(BuiltInRegistries.BLOCK, "thatch", () -> Services.PLATFORM.createThatchFluidBlock(Registration.STILL_THATCH, thatchProperties));
    }

    public static Supplier<EntityType<DummyEntityForSitting>> DUMMY_ENTITY_TYPE = Services.PLATFORM.register(BuiltInRegistries.ENTITY_TYPE, "dummy_entity",
            () -> EntityType.Builder.of(Services.PLATFORM::createDummyEntity, MobCategory.MISC)
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
