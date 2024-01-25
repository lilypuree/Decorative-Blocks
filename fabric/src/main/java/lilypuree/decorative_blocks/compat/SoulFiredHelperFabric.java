package lilypuree.decorative_blocks.compat;

import crystalspider.soulfired.api.FireManager;
import lilypuree.decorative_blocks.platform.services.SoulFiredHelper;
import net.minecraft.world.entity.Entity;

public class SoulFiredHelperFabric implements SoulFiredHelper {
    @Override
    public void setSecondsOnFire(Entity entity, int seconds, boolean isSoul) {
        FireManager.setOnFire(entity, seconds, isSoul ? FireManager.SOUL_FIRE_TYPE : FireManager.DEFAULT_FIRE_TYPE);
    }
}
