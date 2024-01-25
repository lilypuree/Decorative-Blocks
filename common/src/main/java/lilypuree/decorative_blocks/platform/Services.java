package lilypuree.decorative_blocks.platform;

import lilypuree.decorative_blocks.Constants;
import lilypuree.decorative_blocks.platform.services.IPlatformHelper;
import lilypuree.decorative_blocks.platform.services.SoulFiredHelper;

import java.util.ServiceLoader;

public class Services {
    public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);
    public static final SoulFiredHelper SOULFIRED = load(SoulFiredHelper.class);

    public static <T> T load(Class<T> clazz) {

        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        Constants.LOG.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}
