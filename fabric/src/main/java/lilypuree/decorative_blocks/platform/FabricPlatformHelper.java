package lilypuree.decorative_blocks.platform;

import lilypuree.decorative_blocks.Constants;
import lilypuree.decorative_blocks.FabricThatchFluidBlock;
import lilypuree.decorative_blocks.entity.DummyEntityForSitting;
import lilypuree.decorative_blocks.fluid.ThatchFluid;
import lilypuree.decorative_blocks.platform.services.IPlatformHelper;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public boolean isModLoaded(String modid) {
        return FabricLoader.getInstance().isModLoaded(modid);
    }

    @Override
    public <I, T extends I> Supplier<T> register(Registry<I> registry, String name, Supplier<T> sup) {
        T object = Registry.register(registry, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name), sup.get());
        return () -> object;
    }

    @Override
    public GameRules.Key<GameRules.BooleanValue> registerGameRule(String name, GameRules.Category category, boolean defaultValue) {
        return GameRuleRegistry.register(name, category, GameRuleFactory.createBooleanRule(defaultValue));
    }

    @Override
    public DummyEntityForSitting createDummyEntity(EntityType<DummyEntityForSitting> type, Level level) {
        return new DummyEntityForSitting(type, level);
    }

    @Override
    public LiquidBlock createThatchFluidBlock(Supplier<ThatchFluid.Source> fluid, BlockBehaviour.Properties properties) {
        return new FabricThatchFluidBlock(fluid.get(), properties);
    }

//    @Override
//    public ThatchFluid.Flowing createThatchFlowingFluid(ThatchFluid.FluidReferenceHolder referenceHolder) {
//        return new ThatchFluid.Flowing(referenceHolder);
//    }
//
//    @Override
//    public ThatchFluid.Source createThatchStillFluid(ThatchFluid.FluidReferenceHolder referenceHolder) {
//        return new ThatchFluid.Source(referenceHolder);
//    }

    @Override
    public CreativeModeTab.Builder createModTab() {
        return FabricItemGroup.builder();
    }

    @Override
    public void setRenderLayer(Block block, RenderType renderType) {
        BlockRenderLayerMap.INSTANCE.putBlock(block, renderType);
    }

    @Override
    public void registerItemFunc(Item item, ResourceLocation name, ItemPropertyFunction func) {
        ItemProperties.register(item, name, func::call);
    }

    @Override
    public TagKey<Item> getShearTag() {
        return ConventionalItemTags.SHEARS;
    }
}
