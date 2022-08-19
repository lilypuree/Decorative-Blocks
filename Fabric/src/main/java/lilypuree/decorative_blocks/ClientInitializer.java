package lilypuree.decorative_blocks;

import lilypuree.decorative_blocks.blocks.SeatBlock;
import lilypuree.decorative_blocks.blocks.SupportBlock;
import lilypuree.decorative_blocks.client.ClientSetup;
import lilypuree.decorative_blocks.core.DBBlocks;
import lilypuree.decorative_blocks.core.Registration;
import lilypuree.decorative_blocks.entity.EmptyRenderer;
import lilypuree.decorative_blocks.fluid.ThatchFluid;
import lilypuree.decorative_blocks.items.SeatItem;
import lilypuree.decorative_blocks.items.SupportItem;
import lilypuree.decorative_blocks.registration.BlockRegistryObject;
import lilypuree.decorative_blocks.registration.RegistryObject;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.Registry;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.Block;


public class ClientInitializer implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(Registration.DUMMY_ENTITY_TYPE.get(), EmptyRenderer::new);
        ClientSetup.initRenderLayers();
        ClientSetup.initItemPropertyFunctions();
        registerThatchlike(Registration.referenceHolder);
    }

    public static void registerThatchlike(ThatchFluid.FluidReferenceHolder referenceHolder) {
        BlockRenderLayerMap.INSTANCE.putBlock(referenceHolder.getLiquidBlock(), RenderType.solid());
        ClientSpriteRegistryCallback.event(InventoryMenu.BLOCK_ATLAS).register((atlasTexture, registry) -> {
            registry.register(referenceHolder.thatchStillTexture());
            registry.register(referenceHolder.thatchFlowingTexture());
        });
        FluidRenderHandlerRegistry.INSTANCE.register(referenceHolder.getSourceFluid(), referenceHolder.getFlowingFluid(),
                new SimpleFluidRenderHandler(referenceHolder.thatchStillTexture(), referenceHolder.thatchFlowingTexture()));
    }
}
