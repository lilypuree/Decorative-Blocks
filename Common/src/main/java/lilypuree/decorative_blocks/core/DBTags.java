package lilypuree.decorative_blocks.core;

import lilypuree.decorative_blocks.Constants;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class DBTags {
    public static class Blocks {
        public static TagKey<Block> PALISADES;
        public static TagKey<Block> SUPPORTS;
        public static TagKey<Block> SEATS;
        public static TagKey<Block> BEAMS;
        public static TagKey<Block> CHANDELIERS;
        public static TagKey<Block> BRAZIERS;
        public static TagKey<Block> BONFIRES;

        public static void init(){
            PALISADES = TagKey.create(Registry.BLOCK_REGISTRY, id("palisades"));
            SUPPORTS = TagKey.create(Registry.BLOCK_REGISTRY, id("supports"));
            SEATS = TagKey.create(Registry.BLOCK_REGISTRY, id("seats"));
            BEAMS = TagKey.create(Registry.BLOCK_REGISTRY, id("beams"));
            CHANDELIERS = TagKey.create(Registry.BLOCK_REGISTRY, id("chandeliers"));
            BRAZIERS = TagKey.create(Registry.BLOCK_REGISTRY, id("braziers"));
            BONFIRES = TagKey.create(Registry.BLOCK_REGISTRY, id("bonfires"));
        }
    }

    public static class Items {
        public static TagKey<Item> PALISADES;
        public static TagKey<Item> SUPPORTS;
        public static TagKey<Item> SEATS;
        public static TagKey<Item> BEAMS;
        public static TagKey<Item> CHANDELIERS;

        public static void init() {
            PALISADES = TagKey.create(Registry.ITEM_REGISTRY, id("palisades"));
            SUPPORTS = TagKey.create(Registry.ITEM_REGISTRY, id("supports"));
            SEATS = TagKey.create(Registry.ITEM_REGISTRY, id("seats"));
            BEAMS = TagKey.create(Registry.ITEM_REGISTRY, id("beams"));
            CHANDELIERS = TagKey.create(Registry.ITEM_REGISTRY, id("chandeliers"));
        }
    }

    public static class Fluids {
        public static TagKey<Fluid> THATCH;

        public static void init() {
            THATCH = TagKey.create(Registry.FLUID_REGISTRY, id("thatch"));
        }
    }

    public static void init() {
        Blocks.init();
        Items.init();
        Fluids.init();
    }

    private static ResourceLocation id(String path) {
        return new ResourceLocation(Constants.MODID, path);
    }
}
