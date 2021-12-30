package com.lilypuree.decorative_blocks.core;

import com.lilypuree.decorative_blocks.DecorativeBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;

public class DBTags {
    public static void init() {
        DBTags.Blocks.init();
        DBTags.Items.init();
    }

    public static class Blocks {
        public static final ITag.INamedTag<Block> PALISADES = tag("palisades");
        public static final ITag.INamedTag<Block> SUPPORTS = tag("supports");
        public static final ITag.INamedTag<Block> SEATS = tag("seats");
        public static final ITag.INamedTag<Block> BEAMS = tag("beams");
        public static final ITag.INamedTag<Block> CHANDELIERS = tag("chandeliers");
        private static void init() {
        }

        private static ITag.INamedTag<Block> tag(String name) {
            return BlockTags.bind(DecorativeBlocks.MODID + ":" + name);
        }
    }

    public static class Items {
        public static final ITag.INamedTag<Item> PALISADES = tag("palisades");
        public static final ITag.INamedTag<Item> SUPPORTS = tag("supports");
        public static final ITag.INamedTag<Item> SEATS = tag("seats");
        public static final ITag.INamedTag<Item> BEAMS = tag("beams");
        public static final ITag.INamedTag<Item> CHANDELIERS = tag("chandeliers");

        private static void init() {
        }

        private static ITag.INamedTag<Item> tag(String name) {
            return ItemTags.bind(DecorativeBlocks.MODID + ":" + name);
        }
    }
}
