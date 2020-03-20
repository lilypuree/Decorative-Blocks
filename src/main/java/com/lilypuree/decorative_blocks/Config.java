package com.lilypuree.decorative_blocks;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class Config {
    public static final String CATEGORY_GENERAL = "general";

    public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec CLIENT_CONFIG;

    public static ForgeConfigSpec.ConfigValue<String> BONFIRE_ACTIVATOR;
    public static ForgeConfigSpec.ConfigValue<Integer> GOLD_SPAWN_BLOCK_RADIUS;
    public static ForgeConfigSpec.ConfigValue<Integer> GOLD_GATHER_RADIUS;

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

        GOLD_SPAWN_BLOCK_RADIUS = COMMON_BUILDER.comment("The radius gold sparkles will prevent mob spawning (value between 1 to 8)")
                .defineInRange("gold_spawn_block_radius", 4, 1, 8);

        GOLD_GATHER_RADIUS = COMMON_BUILDER.comment("The radius gold sparkles will be gathered when the item is used while sneaking")
                .defineInRange("gold_gather_radius", 3, 1, 6);
    }
}
