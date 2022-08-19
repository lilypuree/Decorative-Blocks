package lilypuree.decorative_blocks.events;

import lilypuree.decorative_blocks.Constants;
import lilypuree.decorative_blocks.core.setup.ModSetup;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Constants.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CallBacks {
    @SubscribeEvent
    public static void onRightClick(PlayerInteractEvent.RightClickBlock event) {
        int result = ModSetup.onRightClick(event.getLevel(), event.getEntity(), event.getHand(), event.getItemStack(), event.getPos());
        if (result == -2) {
            event.setCanceled(true);
        } else if (result == -1) {
            event.setUseBlock(Event.Result.DENY);
        } else if (result == 1) {
            event.setUseBlock(Event.Result.ALLOW);
        }
    }

    @SubscribeEvent
    public static void onLeftClick(PlayerInteractEvent.LeftClickBlock event){
        if (ModSetup.onLeftClick(event.getLevel(), event.getPos())){
            event.setCanceled(true);
        }
    }
}
