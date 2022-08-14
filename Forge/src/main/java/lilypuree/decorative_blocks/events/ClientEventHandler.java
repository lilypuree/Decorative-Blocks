package lilypuree.decorative_blocks.events;

import lilypuree.decorative_blocks.Constants;
import lilypuree.decorative_blocks.blocks.SeatBlock;
import lilypuree.decorative_blocks.blocks.SupportBlock;
import lilypuree.decorative_blocks.client.FogHelper;
import lilypuree.decorative_blocks.core.DBBlocks;
import lilypuree.decorative_blocks.core.Registration;
import lilypuree.decorative_blocks.entity.EmptyRenderer;
import lilypuree.decorative_blocks.items.SeatItem;
import lilypuree.decorative_blocks.items.SupportItem;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.Registry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Constants.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientEventHandler {

    public static void initRenderLayers(FMLClientSetupEvent e) {
        ItemBlockRenderTypes.setRenderLayer(DBBlocks.BAR_PANEL, RenderType.cutoutMipped());
        ItemBlockRenderTypes.setRenderLayer(DBBlocks.LATTICE, RenderType.cutoutMipped());
        ItemBlockRenderTypes.setRenderLayer(DBBlocks.BONFIRE, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(DBBlocks.SOUL_BONFIRE, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(DBBlocks.BRAZIER, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(DBBlocks.SOUL_BRAZIER, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(DBBlocks.CHANDELIER, RenderType.cutoutMipped());
        ItemBlockRenderTypes.setRenderLayer(DBBlocks.SOUL_CHANDELIER, RenderType.cutoutMipped());
        ItemBlockRenderTypes.setRenderLayer(Registration.THATCH, RenderType.solid());
    }

    public static void registerItemFunc(FMLClientSetupEvent e) {
        e.enqueueWork(() -> {
            Registry.BLOCK.forEach(block -> {
                if (block instanceof SupportBlock) {
                    ItemProperties.register(block.asItem(), SupportItem.OVERRIDE_TAG, (stack, level, entity, i) -> {
                        return stack.hasTag() ? stack.getTag().getInt(SupportItem.OVERRIDE_TAG.getPath()) : 0.0f;
                    });
                } else if (block instanceof SeatBlock) {
                    ItemProperties.register(block.asItem(), SeatItem.OVERRIDE_TAG, (stack, world, livingEntity, i) -> {
                        return stack.hasTag() ? stack.getTag().getInt(SeatItem.OVERRIDE_TAG.getPath()) : 0.0f;
                    });
                }
            });
        });
    }

    public static void onEntityRendererRegistry(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(Registration.DUMMY_ENTITY_TYPE, EmptyRenderer::new);
    }
}
