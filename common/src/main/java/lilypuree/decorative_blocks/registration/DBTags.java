package lilypuree.decorative_blocks.registration;

import lilypuree.decorative_blocks.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class DBTags {
    public static class Blocks {
        public static TagKey<Block> PALISADES_THAT_BURN;
        public static TagKey<Block> SUPPORTS_THAT_BURN;
        public static TagKey<Block> SEATS_THAT_BURN;
        public static TagKey<Block> BEAMS_THAT_BURN;
        public static TagKey<Block> PALISADES;
        public static TagKey<Block> SUPPORTS;
        public static TagKey<Block> SEATS;
        public static TagKey<Block> BEAMS;
        public static TagKey<Block> CHANDELIERS;
        public static TagKey<Block> BRAZIERS;
        public static TagKey<Block> BONFIRES;

        public static void init() {
            PALISADES_THAT_BURN = TagKey.create(Registries.BLOCK, id("palisades_that_burn"));
            SUPPORTS_THAT_BURN = TagKey.create(Registries.BLOCK, id("supports_that_burn"));
            SEATS_THAT_BURN = TagKey.create(Registries.BLOCK, id("seats_that_burn"));
            BEAMS_THAT_BURN = TagKey.create(Registries.BLOCK, id("beams_that_burn"));
            PALISADES = TagKey.create(Registries.BLOCK, id("palisades"));
            SUPPORTS = TagKey.create(Registries.BLOCK, id("supports"));
            SEATS = TagKey.create(Registries.BLOCK, id("seats"));
            BEAMS = TagKey.create(Registries.BLOCK, id("beams"));
            CHANDELIERS = TagKey.create(Registries.BLOCK, id("chandeliers"));
            BRAZIERS = TagKey.create(Registries.BLOCK, id("braziers"));
            BONFIRES = TagKey.create(Registries.BLOCK, id("bonfires"));
        }
    }

    public static class Items {
        public static TagKey<Item> PALISADES_THAT_BURN;
        public static TagKey<Item> SUPPORTS_THAT_BURN;
        public static TagKey<Item> SEATS_THAT_BURN;
        public static TagKey<Item> BEAMS_THAT_BURN;
        public static TagKey<Item> PALISADES;
        public static TagKey<Item> SUPPORTS;
        public static TagKey<Item> SEATS;
        public static TagKey<Item> BEAMS;
        public static TagKey<Item> CHANDELIERS;
        public static TagKey<Item> BONFIRE_ACTIVATORS;

        public static void init() {
            PALISADES_THAT_BURN = TagKey.create(Registries.ITEM, id("palisades_that_burn"));
            SUPPORTS_THAT_BURN = TagKey.create(Registries.ITEM, id("supports_that_burn"));
            SEATS_THAT_BURN = TagKey.create(Registries.ITEM, id("seats_that_burn"));
            BEAMS_THAT_BURN = TagKey.create(Registries.ITEM, id("beams_that_burn"));
            PALISADES = TagKey.create(Registries.ITEM, id("palisades"));
            SUPPORTS = TagKey.create(Registries.ITEM, id("supports"));
            SEATS = TagKey.create(Registries.ITEM, id("seats"));
            BEAMS = TagKey.create(Registries.ITEM, id("beams"));
            CHANDELIERS = TagKey.create(Registries.ITEM, id("chandeliers"));
            BONFIRE_ACTIVATORS = TagKey.create(Registries.ITEM, id("bonfire_activators"));
        }
    }

    public static class Fluids {
        public static TagKey<Fluid> THATCH;

        public static void init() {
            THATCH = TagKey.create(Registries.FLUID, id("thatch"));
        }
    }

    public static void init() {
        Blocks.init();
        Items.init();
        Fluids.init();
    }

    private static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, path);
    }
}
