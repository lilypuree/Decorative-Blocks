package com.lilypuree.decorative_blocks.datagen.types;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

public enum WoodTypes {
    OAK("oak"), BIRCH("birch"), SPRUCE("spruce"), ACACIA("acacia"), JUNGLE("jungle"), DARK_OAK("dark_oak");

    private final String name;

    private WoodTypes(String name) {
        this.name = name;
    }

    public String toString() {
        return this.getName();
    }

    public String getName() {
        return this.name;
    }

    public static WoodTypes withName(String name) {
        if (name.equalsIgnoreCase("oak")) return OAK;
        else if (name.equalsIgnoreCase("birch")) return BIRCH;
        else if (name.equalsIgnoreCase("spruce")) return SPRUCE;
        else if (name.equalsIgnoreCase("acacia")) return ACACIA;
        else if (name.equalsIgnoreCase("jungle")) return JUNGLE;
        else if (name.equalsIgnoreCase("dark")) return DARK_OAK;
        return OAK;
    }

    public static WoodTypes fromPath(String path) {
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
        }
        return Blocks.OAK_PLANKS;
    }
}
