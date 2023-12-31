package lilypuree.decorative_blocks;

import lilypuree.decorative_blocks.config.Config;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

public class ForgeConfig implements Config {
    private Item bonfireActivator;

    public static ForgeConfig INSTANCE = new ForgeConfig();
    public static ForgeConfigSpec COMMON_CONFIG;

    public ForgeConfigSpec.ConfigValue<String> BONFIRE_ACTIVATOR;
    public ForgeConfigSpec.ConfigValue<Boolean> THATCH_ENABLED;

    private ForgeConfig() {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
        COMMON_BUILDER.comment("Decorative Blocks Configs").push("general");

        BONFIRE_ACTIVATOR = COMMON_BUILDER.comment("Bonfire Activator (define a resource location")
                .define("bonfire activator", "minecraft:blaze_powder");

        THATCH_ENABLED = COMMON_BUILDER.comment("Enable thatch creation on shearing hay bale")
                .define("thatch enabled", true);

        COMMON_BUILDER.pop();
        COMMON_CONFIG = COMMON_BUILDER.build();
    }

    @Override
    public Item getBonfireActivatorItem() {
        return bonfireActivator;
    }

    @Override
    public boolean isThatchEnabled() {
        return THATCH_ENABLED.get();
    }

    @SubscribeEvent
    public void onLoad(ModConfigEvent.Loading event) {
        bonfireActivator = this.parseItem(BONFIRE_ACTIVATOR.get());
        if (bonfireActivator == Items.AIR)
            Constants.LOG.warn("Bonfire activator config wrong!");
    }

    @SubscribeEvent
    public void onReload(ModConfigEvent.Loading event) {
        bonfireActivator = this.parseItem(BONFIRE_ACTIVATOR.get());
        if (bonfireActivator == Items.AIR)
            Constants.LOG.warn("Bonfire activator config wrong!");
    }
}
