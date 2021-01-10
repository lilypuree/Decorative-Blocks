package com.lilypuree.decorative_blocks.setup;

import com.lilypuree.decorative_blocks.DecorativeBlocks;
import com.lilypuree.decorative_blocks.entity.DummyEntityForSitting;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = DecorativeBlocks.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {

    public static void init(final FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(Registration.DUMMY_ENTITY_TYPE.get(), EmptyRenderer::new);


        RenderTypeLookup.setRenderLayer(Registration.BAR_PANEL.get(), RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(Registration.BONFIRE.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(Registration.SOUL_BONFIRE.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(Registration.LATTICE.get(), RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(Registration.BRAZIER.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(Registration.SOUL_BRAZIER.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(Registration.CHAIN.get(), RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(Registration.CHANDELIER.get(), RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(Registration.SOUL_CHANDELIER.get(), RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(Registration.THATCH.get(), RenderType.getSolid());
    }


    private static class EmptyRenderer extends EntityRenderer<DummyEntityForSitting> {
        protected EmptyRenderer(EntityRendererManager renderManager) {
            super(renderManager);
        }

        @Override
        public boolean shouldRender(DummyEntityForSitting livingEntityIn, ClippingHelper camera, double camX, double camY, double camZ) {
            return false;
        }

        @Override
        public ResourceLocation getEntityTexture(DummyEntityForSitting entity) {
            return null;
        }
    }
}
