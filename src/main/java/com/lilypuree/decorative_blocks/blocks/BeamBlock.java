package com.lilypuree.decorative_blocks.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class BeamBlock extends RotatedPillarBlock {

    private boolean flammable;

    public BeamBlock(Block.Properties properties, boolean flammable) {
        super(properties);
        this.flammable = flammable;
    }

    @Override
    public boolean isFlammable(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return flammable;
    }

    @Override
    public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        if (flammable) {
            return 20;
        } else return super.getFlammability(state, world, pos, face);
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        if (flammable) {
            return 5;
        } else return super.getFireSpreadSpeed(state, world, pos, face);
    }
}
