package com.lilypuree.decorative_blocks.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class PillarBlock extends Block implements IWaterLoggable {
    private final VoxelShape PILLAR_SHAPE = Block.makeCuboidShape(2D, 0.0D, 2D, 14D, 16.0D, 14D);

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;


    public PillarBlock(Block.Properties properties){
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(WATERLOGGED, Boolean.valueOf(false)));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return PILLAR_SHAPE;
    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState blockstate = context.getWorld().getBlockState(context.getPos());
        IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
        boolean flag = ifluidstate.isTagged(FluidTags.WATER) && ifluidstate.getLevel() == 8;

        return this.getDefaultState().with(WATERLOGGED, Boolean.valueOf(flag));
    }


    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return !state.get(WATERLOGGED);
    }

    public IFluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    @Override
    public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        return false;
    }
}
