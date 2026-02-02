package lilypuree.decorative_blocks.events;

import lilypuree.decorative_blocks.Constants;
import lilypuree.decorative_blocks.client.ClientSetup;
import lilypuree.decorative_blocks.entity.EmptyRenderer;
import lilypuree.decorative_blocks.registration.Registration;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@EventBusSubscriber(value = Dist.CLIENT, modid = Constants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ClientEventHandler {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent e) {
        e.enqueueWork(() -> {
            ClientSetup.initRenderLayers();
            ClientSetup.initItemPropertyFunctions();
        });
    }

    @SubscribeEvent
    public static void onEntityRendererRegistry(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(Registration.DUMMY_ENTITY_TYPE.get(), EmptyRenderer::new);
    }

    @SubscribeEvent
    public static void onKeyMappingRegistry(RegisterKeyMappingsEvent event) {
//        event.register(ClientSetup.switchItemState);
    }
}
