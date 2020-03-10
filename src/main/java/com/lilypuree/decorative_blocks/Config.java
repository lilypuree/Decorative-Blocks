package com.lilypuree.decorative_blocks;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod.EventBusSubscriber
public class Config {
    public static final String CATEGORY_GENERAL = "general";

    public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec CLIENT_CONFIG;

    public static ForgeConfigSpec.ConfigValue<String> BONFIRE_ACTIVATOR;

    static {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
        COMMON_BUILDER.comment("Decorative Blocks Configs").push(CATEGORY_GENERAL);
        setUpBonfireActivatorConfig(COMMON_BUILDER);
        COMMON_BUILDER.pop();
        COMMON_CONFIG = COMMON_BUILDER.build();
    }


    private static void setUpBonfireActivatorConfig(ForgeConfigSpec.Builder COMMON_BUILDER) {

        BONFIRE_ACTIVATOR = COMMON_BUILDER.comment("Bonfire Activator (define a resource location")
                .define("bonfire activator", "minecraft:blaze_powder");

    }
}
