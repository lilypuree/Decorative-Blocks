package lilypuree.decorative_blocks.config;

import com.mojang.authlib.minecraft.TelemetrySession;
import lilypuree.decorative_blocks.CommonConfig;
import lilypuree.decorative_blocks.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public interface Config {
    Item getBonfireActivatorItem();

    boolean isThatchEnabled();

    public static Config get() {
        return Services.PLATFORM.getConfig();
    }

    default Item parseItem(String location) {
        ResourceLocation resourceLocation = ResourceLocation.tryParse(location);
        if (location != null && BuiltInRegistries.ITEM.containsKey(resourceLocation))
            return BuiltInRegistries.ITEM.get(resourceLocation);
        else
            return Items.AIR;
    }
}
