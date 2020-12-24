package com.lilypuree.decorative_blocks.blocks;

import com.lilypuree.decorative_blocks.datagen.types.IWoodType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.EmptyBlockReader;
import net.minecraft.world.IBlockReader;

public class BeamBlock extends RotatedPillarBlock {

    private IWoodType woodType;

    public BeamBlock(Block.Properties properties, IWoodType woodType) {
        super(properties);
        this.woodType = woodType;
    }

    public IWoodType getWoodType() {
        return woodType;
    }

    @Override
    public boolean isFlammable(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return woodType.isFlammable();
    }

    @Override
    public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        if (woodType.isFlammable()) {
            return 20;
        } else return super.getFlammability(state, world, pos, face);
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        if (woodType.isFlammable()) {
            return 5;
        } else return super.getFireSpreadSpeed(state, world, pos, face);
    }

}
