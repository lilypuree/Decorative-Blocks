package lilypuree.decorative_blocks.platform.services;

import lilypuree.decorative_blocks.entity.DummyEntityForSitting;
import lilypuree.decorative_blocks.fluid.ThatchFluid;
import lilypuree.decorative_blocks.registration.RegistryObject;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;

import java.util.function.Supplier;

public interface IPlatformHelper {
    DummyEntityForSitting createDummyEntity(EntityType<DummyEntityForSitting> type, Level level);

    LiquidBlock createThatchFluidBlock(RegistryObject<FlowingFluid> fluid, BlockBehaviour.Properties properties);

    ThatchFluid createThatchFlowingFluid(ThatchFluid.FluidReferenceHolder referenceHolder);

    ThatchFluid createThatchStillFluid(ThatchFluid.FluidReferenceHolder referenceHolder);

    CreativeModeTab createModTab(String name, Supplier<ItemStack> icon);

    void setRenderLayer(Block block, RenderType renderType);

    void registerItemFunc(Item item, ResourceLocation name, ItemPropertyFunction func);
}
