package lilypuree.decorative_blocks.core;

import lilypuree.decorative_blocks.Constants;
import lilypuree.decorative_blocks.mixin.BlockTagsInvoker;
import lilypuree.decorative_blocks.mixin.ItemTagsInvoker;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class DBTags {
    public static void init() {
        Blocks.init();
        Items.init();
    }

    public static class Blocks {
        public static final Tag.Named<Block> PALISADES = tag("palisades");
        public static final Tag.Named<Block> SUPPORTS = tag("supports");
        public static final Tag.Named<Block> SEATS = tag("seats");
        public static final Tag.Named<Block> BEAMS = tag("beams");
        public static final Tag.Named<Block> CHANDELIERS = tag("chandeliers");
        private static void init() {
        }

        private static Tag.Named<Block> tag(String name) {
            return BlockTagsInvoker.invokeBind(Constants.MODID + ":" + name);
        }
    }

    public static class Items {
        public static final Tag.Named<Item> PALISADES = tag("palisades");
        public static final Tag.Named<Item> SUPPORTS = tag("supports");
        public static final Tag.Named<Item> SEATS = tag("seats");
        public static final Tag.Named<Item> BEAMS = tag("beams");
        public static final Tag.Named<Item> CHANDELIERS = tag("chandeliers");

        private static void init() {
        }

        private static Tag.Named<Item> tag(String name) {
            return ItemTagsInvoker.invokeBind(Constants.MODID + ":" + name);
        }
    }
}
