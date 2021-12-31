package lilypuree.decorative_blocks.datagen.types;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public enum VanillaWoodTypes implements IWoodType {
    OAK("oak"), BIRCH("birch") {
        @Override
        public MaterialColor getMaterialColor() {
            return MaterialColor.SAND;
        }
    }, SPRUCE("spruce") {
        @Override
        public MaterialColor getMaterialColor() {
            return MaterialColor.PODZOL;
        }
    },
    ACACIA("acacia") {
        @Override
        public MaterialColor getMaterialColor() {
            return MaterialColor.COLOR_ORANGE;
        }
    }, JUNGLE("jungle") {
        @Override
        public MaterialColor getMaterialColor() {
            return MaterialColor.DIRT;
        }
    },
    DARK_OAK("dark_oak") {
        @Override
        public MaterialColor getMaterialColor() {
            return MaterialColor.COLOR_BROWN;
        }
    },
    CRIMSON("crimson") {
        @Override
        public boolean isFlammable() {
            return false;
        }

        @Override
        public Material getMaterial() {
            return Material.NETHER_WOOD;
        }

        @Override
        public MaterialColor getMaterialColor() {
            return MaterialColor.NETHER;
        }
    }, WARPED("warped") {
        @Override
        public boolean isFlammable() {
            return false;
        }

        @Override
        public Material getMaterial() {
            return Material.NETHER_WOOD;
        }

        @Override
        public MaterialColor getMaterialColor() {
            return MaterialColor.COLOR_CYAN;
        }
    };

    private final String name;

    private VanillaWoodTypes(String name) {
        this.name = name;
    }

    public String toString() {
        return this.getName();
    }

    @Override
    public String namespace() {
        return "minecraft";
    }

    public String getName() {
        return this.name;
    }



    public static VanillaWoodTypes withName(String name) {
        if (name.equalsIgnoreCase("oak")) return OAK;
        else if (name.equalsIgnoreCase("birch")) return BIRCH;
        else if (name.equalsIgnoreCase("spruce")) return SPRUCE;
        else if (name.equalsIgnoreCase("acacia")) return ACACIA;
        else if (name.equalsIgnoreCase("jungle")) return JUNGLE;
        else if (name.equalsIgnoreCase("dark")) return DARK_OAK;
        else if (name.equalsIgnoreCase("crimson")) return CRIMSON;
        else if (name.equalsIgnoreCase("warped")) return WARPED;
        return OAK;
    }

    public static VanillaWoodTypes fromPath(String path) {
        String[] paths = path.split("_");
        return withName(paths[0]);
    }

    public Block getLog() {
        switch (this) {
            case OAK:
                return Blocks.OAK_LOG;
            case SPRUCE:
                return Blocks.SPRUCE_LOG;
            case BIRCH:
                return Blocks.BIRCH_LOG;
            case JUNGLE:
                return Blocks.JUNGLE_LOG;
            case DARK_OAK:
                return Blocks.DARK_OAK_LOG;
            case ACACIA:
                return Blocks.ACACIA_LOG;
            case CRIMSON:
                return Blocks.CRIMSON_STEM;
            case WARPED:
                return Blocks.WARPED_STEM;
        }
        return Blocks.OAK_LOG;
    }

    public Block getStrippedLog() {
        switch (this) {
            case OAK:
                return Blocks.STRIPPED_OAK_LOG;
            case SPRUCE:
                return Blocks.STRIPPED_SPRUCE_LOG;
            case BIRCH:
                return Blocks.STRIPPED_BIRCH_LOG;
            case JUNGLE:
                return Blocks.STRIPPED_JUNGLE_LOG;
            case DARK_OAK:
                return Blocks.STRIPPED_DARK_OAK_LOG;
            case ACACIA:
                return Blocks.STRIPPED_ACACIA_LOG;
            case CRIMSON:
                return Blocks.STRIPPED_CRIMSON_STEM;
            case WARPED:
                return Blocks.STRIPPED_WARPED_STEM;
        }
        return Blocks.STRIPPED_OAK_LOG;
    }

    public Block getSlab() {
        switch (this) {
            case OAK:
                return Blocks.OAK_SLAB;
            case SPRUCE:
                return Blocks.SPRUCE_SLAB;
            case BIRCH:
                return Blocks.BIRCH_SLAB;
            case JUNGLE:
                return Blocks.JUNGLE_SLAB;
            case DARK_OAK:
                return Blocks.DARK_OAK_SLAB;
            case ACACIA:
                return Blocks.ACACIA_SLAB;
            case CRIMSON:
                return Blocks.CRIMSON_SLAB;
            case WARPED:
                return Blocks.WARPED_SLAB;
        }
        return Blocks.OAK_SLAB;
    }

    public Block getFence() {
        switch (this) {
            case OAK:
                return Blocks.OAK_FENCE;
            case SPRUCE:
                return Blocks.SPRUCE_FENCE;
            case BIRCH:
                return Blocks.BIRCH_FENCE;
            case JUNGLE:
                return Blocks.JUNGLE_FENCE;
            case DARK_OAK:
                return Blocks.DARK_OAK_FENCE;
            case ACACIA:
                return Blocks.ACACIA_FENCE;
            case CRIMSON:
                return Blocks.CRIMSON_FENCE;
            case WARPED:
                return Blocks.WARPED_FENCE;
        }
        return Blocks.OAK_FENCE;
    }

    public Block getPlanks() {
        switch (this) {
            case OAK:
                return Blocks.OAK_PLANKS;
            case SPRUCE:
                return Blocks.SPRUCE_PLANKS;
            case BIRCH:
                return Blocks.BIRCH_PLANKS;
            case JUNGLE:
                return Blocks.JUNGLE_PLANKS;
            case DARK_OAK:
                return Blocks.DARK_OAK_PLANKS;
            case ACACIA:
                return Blocks.ACACIA_PLANKS;
            case CRIMSON:
                return Blocks.CRIMSON_PLANKS;
            case WARPED:
                return Blocks.WARPED_PLANKS;
        }
        return Blocks.OAK_PLANKS;
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public boolean isFlammable() {
        return true;
    }


}
