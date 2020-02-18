package com.lilypuree.decorative_blocks.setup;

import com.lilypuree.decorative_blocks.DecorativeBlocks;
import com.lilypuree.decorative_blocks.entity.DummyEntityForSitting;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.culling.ClippingHelperImpl;
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

        RenderTypeLookup.setRenderLayer(Registration.BAR_PANEL.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(Registration.BRAZIER.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(Registration.CHAIN.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(Registration.CHANDELIER.get(), RenderType.getCutoutMipped());
    }

    private static class EmptyRenderer extends EntityRenderer<DummyEntityForSitting>
    {
        protected EmptyRenderer(EntityRendererManager renderManager)
        {
            super(renderManager);
        }

        @Override
        public boolean shouldRender(DummyEntityForSitting p_225626_1_, ClippingHelperImpl p_225626_2_, double p_225626_3_, double p_225626_5_, double p_225626_7_) //shouldRender
        {
            return false;
        }

        @Override
        public ResourceLocation getEntityTexture(DummyEntityForSitting entity)
        {
            return null;
        }
    }
}
