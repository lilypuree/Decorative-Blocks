package com.lilypuree.decorative_blocks.blocks;

import com.lilypuree.decorative_blocks.datagen.types.IWoodType;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public interface IWoodenBlock {

    IWoodType getWoodType();
}
