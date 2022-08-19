package lilypuree.decorative_blocks.platform;

import lilypuree.decorative_blocks.Constants;
import lilypuree.decorative_blocks.FabricThatchFluidBlock;
import lilypuree.decorative_blocks.core.DBItems;
import lilypuree.decorative_blocks.entity.DummyEntityForSitting;
import lilypuree.decorative_blocks.fluid.ThatchFluid;
import lilypuree.decorative_blocks.platform.services.IPlatformHelper;
import lilypuree.decorative_blocks.registration.RegistryObject;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
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

public class FabricPlatformHelper implements IPlatformHelper {
    @Override
    public DummyEntityForSitting createDummyEntity(EntityType<DummyEntityForSitting> type, Level level) {
        return new DummyEntityForSitting(type, level) {
            @Override
            public Packet<?> getAddEntityPacket() {
                return new ClientboundAddEntityPacket(this);
            }
        };
    }

    @Override
    public LiquidBlock createThatchFluidBlock(RegistryObject<FlowingFluid> fluid, BlockBehaviour.Properties properties) {
        return new FabricThatchFluidBlock(fluid.get(), properties);
    }

    @Override
    public ThatchFluid createThatchFlowingFluid(ThatchFluid.FluidReferenceHolder referenceHolder) {
        return new ThatchFluid.Flowing(referenceHolder);
    }

    @Override
    public ThatchFluid createThatchStillFluid(ThatchFluid.FluidReferenceHolder referenceHolder) {
        return new ThatchFluid.Source(referenceHolder);
    }

    @Override
    public CreativeModeTab createModTab(String name, Supplier<ItemStack> icon) {
        return FabricItemGroupBuilder.build(new ResourceLocation(Constants.MODID, name), icon);
    }

    @Override
    public void setRenderLayer(Block block, RenderType renderType) {
        BlockRenderLayerMap.INSTANCE.putBlock(block, renderType);
    }

    @Override
    public void registerItemFunc(Item item, ResourceLocation name, ItemPropertyFunction func) {
        ItemProperties.register(item, name, func::call);
    }
}
