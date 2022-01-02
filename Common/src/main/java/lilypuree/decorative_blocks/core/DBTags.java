package lilypuree.decorative_blocks.core;

import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class DBTags {


    public static class Blocks {
        public static Tag.Named<Block> PALISADES;
        public static Tag.Named<Block> SUPPORTS;
        public static Tag.Named<Block> SEATS;
        public static Tag.Named<Block> BEAMS;
        public static Tag.Named<Block> CHANDELIERS;
        public static Tag.Named<Block> BRAZIERS;
        public static Tag.Named<Block> BONFIRES;
    }

    public static class Items {
        public static Tag.Named<Item> PALISADES;
        public static Tag.Named<Item> SUPPORTS;
        public static Tag.Named<Item> SEATS;
        public static Tag.Named<Item> BEAMS;
        public static Tag.Named<Item> CHANDELIERS;
    }

    public static class Fluids {
        public static Tag.Named<Fluid> THATCH;
    }
}
