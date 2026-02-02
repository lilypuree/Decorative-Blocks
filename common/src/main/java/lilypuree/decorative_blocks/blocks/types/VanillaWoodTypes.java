package lilypuree.decorative_blocks.blocks.types;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.WoodType;

import java.util.Set;

public class VanillaWoodTypes {
    private VanillaWoodTypes() {

    }

    public static Set<WoodType> VANILLA = Set.of(
            WoodType.OAK,
            WoodType.SPRUCE,
            WoodType.BIRCH,
            WoodType.ACACIA,
            WoodType.CHERRY,
            WoodType.JUNGLE,
            WoodType.DARK_OAK,
            WoodType.CRIMSON,
            WoodType.WARPED,
            WoodType.MANGROVE,
            WoodType.BAMBOO);

    private static ResourceLocation mcLoc(String name) {
        return ResourceLocation.withDefaultNamespace(name);
    }

    public static Block getLog(WoodType woodType) {
        if (woodType == WoodType.BAMBOO) {
            return Blocks.STRIPPED_BAMBOO_BLOCK;
        } else {
            String suffix = isNetherWood(woodType) ? "_stem" : "_log";
            return BuiltInRegistries.BLOCK.get(mcLoc(woodType.name() + suffix));
        }
    }

    public static Block getStrippedLog(WoodType woodType) {
        String suffix = isNetherWood(woodType) ? "_stem" : "_log";
        return BuiltInRegistries.BLOCK.get(mcLoc("stripped_" + woodType.name() + suffix));
    }

    public static Block getSlab(WoodType woodType) {
        return BuiltInRegistries.BLOCK.get(mcLoc(woodType.name() + "_slab"));
    }

    public static Block getFence(WoodType woodType) {
        return BuiltInRegistries.BLOCK.get(mcLoc(woodType.name() + "_fence"));
    }

    public static Block getPlanks(WoodType woodType) {
        return BuiltInRegistries.BLOCK.get(mcLoc(woodType.name() + "_planks"));
    }

    public static boolean isNetherWood(WoodType woodType) {
        return woodType == WoodType.CRIMSON || woodType == WoodType.WARPED;
    }
}
