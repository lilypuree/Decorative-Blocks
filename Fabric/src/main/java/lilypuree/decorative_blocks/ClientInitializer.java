package lilypuree.decorative_blocks;

import lilypuree.decorative_blocks.core.DBBlocks;
import lilypuree.decorative_blocks.core.Registration;
import lilypuree.decorative_blocks.entity.EmptyRenderer;
import lilypuree.decorative_blocks.fluid.ThatchFluid;
import lilypuree.decorative_blocks.items.SeatItem;
import lilypuree.decorative_blocks.items.SupportItem;
import lilypuree.decorative_blocks.mixin.ItemPropertiesFabricInvoker;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.Block;


public class ClientInitializer implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(Registration.DUMMY_ENTITY_TYPE, EmptyRenderer::new);

        BlockRenderLayerMap.INSTANCE.putBlock(DBBlocks.BAR_PANEL, RenderType.cutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(DBBlocks.LATTICE, RenderType.cutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(DBBlocks.BONFIRE, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(DBBlocks.SOUL_BONFIRE, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(DBBlocks.BRAZIER, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(DBBlocks.SOUL_BRAZIER, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(DBBlocks.CHANDELIER, RenderType.cutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(DBBlocks.SOUL_CHANDELIER, RenderType.cutoutMipped());

        registerThatchlike(Registration.referenceHolder);

        DBBlocks.SUPPORTS.values().forEach(ClientInitializer::registerSupportItemProperties);
        DBBlocks.SEATS.values().forEach(ClientInitializer::registerSeatItemProperties);
    }

    public static void registerSupportItemProperties(Block block) {
        ItemPropertiesFabricInvoker.invokeRegister(block.asItem(), SupportItem.OVERRIDE_TAG, (stack, level, entity, i) -> {
            return stack.hasTag() ? stack.getTag().getInt(SupportItem.OVERRIDE_TAG.getPath()) : 0.0f;
        });
    }
    public static void registerSeatItemProperties(Block block) {
        ItemPropertiesFabricInvoker.invokeRegister(block.asItem(), SeatItem.OVERRIDE_TAG, (stack, world, livingEntity, i) -> {
            return stack.hasTag() ? stack.getTag().getInt(SeatItem.OVERRIDE_TAG.getPath()) : 0.0f;
        });
    }

    public static void registerThatchlike(ThatchFluid.FluidReferenceHolder referenceHolder) {
        BlockRenderLayerMap.INSTANCE.putBlock(referenceHolder.getFluidBlock(), RenderType.solid());
        ClientSpriteRegistryCallback.event(InventoryMenu.BLOCK_ATLAS).register((atlasTexture, registry) -> {
            registry.register(referenceHolder.thatchStillTexture);
            registry.register(referenceHolder.thatchFlowingTexture);
        });
        FluidRenderHandlerRegistry.INSTANCE.register(referenceHolder.getStillFluid(), referenceHolder.getFlowingFluid(),
                new SimpleFluidRenderHandler(referenceHolder.thatchStillTexture, referenceHolder.thatchFlowingTexture));
    }
}
