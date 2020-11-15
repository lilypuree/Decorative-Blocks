package com.lilypuree.decorative_blocks.datagen.types;

import net.minecraft.block.Block;

public interface IWoodType {
    String toString();

    Block getLog();
    Block getStrippedLog();
    Block getSlab();
    Block getFence();
    Block getPlanks();

    boolean isAvailable();

    boolean isFlammable();
}
