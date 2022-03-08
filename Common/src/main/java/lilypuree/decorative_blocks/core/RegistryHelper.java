package lilypuree.decorative_blocks.core;

import lilypuree.decorative_blocks.Constants;
import net.minecraft.resources.ResourceLocation;

@FunctionalInterface
public interface RegistryHelper<T> {
    public void register(T entry, ResourceLocation name);

    default void register(T entry, String name) {
        register(entry, new ResourceLocation(Constants.MODID, name));
    }
}
