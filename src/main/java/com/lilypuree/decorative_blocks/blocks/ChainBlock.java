package com.lilypuree.decorative_blocks.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;

import javax.annotation.Nullable;

public class ChainBlock extends RotatedPillarBlock implements IWaterLoggable {
    private static final double d0 = 4D;
    private static final double d1 = 12D;
    protected static final VoxelShape CHAIN_SHAPE_X = Block.makeCuboidShape(0, d0, d0, 16, d1, d1);
    protected static final VoxelShape CHAIN_SHAPE_Y = Block.makeCuboidShape(d0, 0, d0, d1, 16, d1);
    protected static final VoxelShape CHAIN_SHAPE_Z = Block.makeCuboidShape(d0, d0, 0, d1, d1, 16);

    private static final VoxelShape CHAIN_COLLISION_SHAPE = Block.makeCuboidShape(6D, 0D, 6D, 10D, 16, 10D);

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public ChainBlock(Block.Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(WATERLOGGED, Boolean.FALSE));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Direction.Axis axis = state.get(AXIS);
        switch (axis) {
            case X:
                return CHAIN_SHAPE_X;
            case Z:
                return CHAIN_SHAPE_Z;
            case Y:
            default:
                return CHAIN_SHAPE_Y;
        }
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        if (state.get(AXIS) == Direction.Axis.Y) {
            return CHAIN_COLLISION_SHAPE;
        }
        return super.getCollisionShape(state, worldIn, pos, context);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        FluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
        boolean flag = ifluidstate.isTagged(FluidTags.WATER) && ifluidstate.getLevel() == 8;
        return super.getStateForPlacement(context).with(WATERLOGGED, flag);
    }

    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return !state.get(WATERLOGGED);
    }

    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(WATERLOGGED);
    }

}
