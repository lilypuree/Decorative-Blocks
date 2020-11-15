package com.lilypuree.decorative_blocks.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class SupportBlock extends HorizontalBlock implements IWaterLoggable {
    private static final double d0 = 3D;
    private static final double d1 = 13D;
    private static final double d2 = 4D;
    private static final double d3 = 12D;
    public static final VoxelShape TOP = Block.makeCuboidShape(0, d1, 0, 16D, 16D, 16D);
    public static final VoxelShape BOTTOM = Block.makeCuboidShape(0, 0, 0, 16D, 16D - d1, 16D);
    public static final VoxelShape NORTH_PART = Block.makeCuboidShape(d2, 0, d1, d3, d1, 16D);
    public static final VoxelShape SOUTH_PART = Block.makeCuboidShape(d2, 0, 0, d3, d1, d0);
    public static final VoxelShape EAST_PART = Block.makeCuboidShape(0, 0, d2, d0, d1, d3);
    public static final VoxelShape WEST_PART = Block.makeCuboidShape(d1, 0, d2, 16D, d1, d3);
    public static final VoxelShape NORTH_SHAPE = VoxelShapes.or(TOP, NORTH_PART);
    public static final VoxelShape SOUTH_SHAPE = VoxelShapes.or(TOP, SOUTH_PART);
    public static final VoxelShape EAST_SHAPE = VoxelShapes.or(TOP, EAST_PART);
    public static final VoxelShape WEST_SHAPE = VoxelShapes.or(TOP, WEST_PART);
    public static final VoxelShape NORTH_DOWN_SHAPE = VoxelShapes.or(BOTTOM, NORTH_PART);
    public static final VoxelShape SOUTH_DOWN_SHAPE = VoxelShapes.or(BOTTOM, SOUTH_PART);
    public static final VoxelShape EAST_DOWN_SHAPE = VoxelShapes.or(BOTTOM, EAST_PART);
    public static final VoxelShape WEST_DOWN_SHAPE = VoxelShapes.or(BOTTOM, WEST_PART);

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty UP = BlockStateProperties.UP;

    private boolean flammable;

    public SupportBlock(Block.Properties properties, boolean flammable) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(WATERLOGGED, Boolean.FALSE).with(UP, Boolean.TRUE));
        this.flammable = flammable;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Direction facing = state.get(HORIZONTAL_FACING);
        if (state.get(UP)) {
            switch (facing) {
                case NORTH:
                    return NORTH_SHAPE;
                case SOUTH:
                    return SOUTH_SHAPE;
                case EAST:
                    return EAST_SHAPE;
                case WEST:
                    return WEST_SHAPE;
            }
        } else {
            switch (facing) {
                case NORTH:
                    return NORTH_SHAPE;
                case SOUTH:
                    return SOUTH_SHAPE;
                case EAST:
                    return EAST_SHAPE;
                case WEST:
                    return WEST_SHAPE;
            }
        }
        return NORTH_SHAPE;
    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState blockstate = context.getWorld().getBlockState(context.getPos());
        FluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
        boolean flag = ifluidstate.isTagged(FluidTags.WATER) && ifluidstate.getLevel() == 8;

        PlayerEntity player = context.getPlayer();
//        boolean crouch = player != null && player.isCrouching();
        return this.getDefaultState().with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite()).with(WATERLOGGED, flag);
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, WATERLOGGED, UP);
    }

    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return !state.get(WATERLOGGED);
    }

    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    @Override
    public boolean isFlammable(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return flammable;
    }

    @Override
    public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return 20;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return 5;
    }

    @Override
    public boolean allowsMovement(BlockState p_196266_1_, IBlockReader p_196266_2_, BlockPos p_196266_3_, PathType p_196266_4_) {
        return false;
    }
}
