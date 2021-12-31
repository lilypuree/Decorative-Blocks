package lilypuree.decorative_blocks;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = Constants.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    public static final String CATEGORY_GENERAL = "general";

    public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec CLIENT_CONFIG;

    public static ForgeConfigSpec.ConfigValue<String> BONFIRE_ACTIVATOR;
    public static ForgeConfigSpec.ConfigValue<Boolean> THATCH_ENABLED;
    static {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
        COMMON_BUILDER.comment("Decorative Blocks Configs").push(CATEGORY_GENERAL);
        setUpBonfireActivatorConfig(COMMON_BUILDER);

        THATCH_ENABLED = COMMON_BUILDER.comment("Disable thatch creation on shearing hay bale")
                .define("thatch enabled", true);

        COMMON_BUILDER.pop();
        COMMON_CONFIG = COMMON_BUILDER.build();
    }
    private static void setUpBonfireActivatorConfig(ForgeConfigSpec.Builder COMMON_BUILDER) {
        BONFIRE_ACTIVATOR = COMMON_BUILDER.comment("Bonfire Activator (define a resource location")
                .define("bonfire activator", "minecraft:blaze_powder");

    }

    @SubscribeEvent
    public static void onLoad(ModConfigEvent.Loading event){
       CommonConfig.BONFIRE_ACTIVATOR = BONFIRE_ACTIVATOR.get();
       CommonConfig.THATCH_ENABLED = THATCH_ENABLED.get();
    }

    @SubscribeEvent
    public static void onReload(ModConfigEvent.Loading event){
        CommonConfig.BONFIRE_ACTIVATOR = BONFIRE_ACTIVATOR.get();
        CommonConfig.THATCH_ENABLED = THATCH_ENABLED.get();
    }
}
