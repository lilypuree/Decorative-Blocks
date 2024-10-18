package lilypuree.decorative_blocks.compat;

import it.crystalnest.soul_fire_d.api.FireManager;
import net.minecraft.world.entity.Entity;

public class SoulFired {
    public static void setSecondsOnFire(Entity entity, int seconds, boolean isSoul) {
        FireManager.setOnFire(entity, seconds, isSoul ? FireManager.SOUL_FIRE_TYPE : FireManager.DEFAULT_FIRE_TYPE);
    }
}
