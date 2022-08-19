package lilypuree.decorative_blocks.events;

import lilypuree.decorative_blocks.Constants;
import lilypuree.decorative_blocks.client.ClientSetup;
import lilypuree.decorative_blocks.core.Registration;
import lilypuree.decorative_blocks.entity.EmptyRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Constants.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientEventHandler {
    public static void clientSetup(FMLClientSetupEvent e) {
        e.enqueueWork(() -> {
            ClientSetup.initRenderLayers();
            ClientSetup.initItemPropertyFunctions();
        });
    }

    public static void onEntityRendererRegistry(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(Registration.DUMMY_ENTITY_TYPE.get(), EmptyRenderer::new);
    }
}
