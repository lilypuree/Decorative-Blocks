package lilypuree.decorative_blocks;

import lilypuree.decorative_blocks.client.ClientSetup;
import lilypuree.decorative_blocks.entity.EmptyRenderer;
import lilypuree.decorative_blocks.fluid.ThatchFluid;
import lilypuree.decorative_blocks.registration.Registration;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.RenderType;


public class ClientInitializer implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(Registration.DUMMY_ENTITY_TYPE.get(), EmptyRenderer::new);
        ClientSetup.initRenderLayers();
        ClientSetup.initItemPropertyFunctions();
//        KeyBindingHelper.registerKeyBinding(ClientSetup.switchItemState);
        registerThatchlike(Registration.referenceHolder);
    }

    public static void registerThatchlike(ThatchFluid.FluidReferenceHolder referenceHolder) {
        BlockRenderLayerMap.INSTANCE.putBlock(referenceHolder.getLiquidBlock(), RenderType.solid());
        FluidRenderHandlerRegistry.INSTANCE.register(referenceHolder.getSourceFluid(), referenceHolder.getFlowingFluid(),
                new SimpleFluidRenderHandler(referenceHolder.thatchStillTexture(), referenceHolder.thatchFlowingTexture()));
    }
}
