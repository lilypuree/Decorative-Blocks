package com.lilypuree.decorative_blocks;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
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

}
