package com.lilypuree.decorative_blocks.blocks;

import com.lilypuree.decorative_blocks.datagen.types.IWoodType;
import com.lilypuree.decorative_blocks.entity.DummyEntityForSitting;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import java.util.List;

public class SeatPostBlock extends HorizontalBlock implements IWaterLoggable {
    protected static final VoxelShape POST_SHAPE = Block.makeCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D);
    protected static final VoxelShape JOIST_SHAPE_NS = Block.makeCuboidShape(0, 4.0D, 4D, 16D, 7D, 12D);
    protected static final VoxelShape JOIST_SHAPE_EW = Block.makeCuboidShape(4.0D, 4.0D, 0D, 12D, 7D, 16D);
    protected static final VoxelShape SEAT_SHAPE_NS = VoxelShapes.or(POST_SHAPE, JOIST_SHAPE_NS);
    protected static final VoxelShape SEAT_SHAPE_EW = VoxelShapes.or(POST_SHAPE, JOIST_SHAPE_EW);

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private IWoodType woodType;

    public SeatPostBlock(Properties properties, IWoodType woodType) {
        super(properties);
        this.woodType = woodType;
    }

    public IWoodType getWoodType() {
        return woodType;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Direction facing = state.get(HORIZONTAL_FACING);
        switch (facing) {
            case NORTH:
            case SOUTH:
                return SEAT_SHAPE_NS;
            case EAST:
            case WEST:
                return SEAT_SHAPE_EW;
        }
        return SEAT_SHAPE_NS;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getPos();
        BlockState blockstate = world.getBlockState(pos);
        FluidState ifluidstate = world.getFluidState(pos);
        boolean waterloggedFlag = ifluidstate.isTagged(FluidTags.WATER) && ifluidstate.getLevel() == 8;

        Direction facingDir = context.getFace();
        Direction placementDir;
        if (facingDir == Direction.DOWN || facingDir == Direction.UP) {
            placementDir = context.getPlacementHorizontalFacing().getOpposite();
        } else {
            placementDir = facingDir.rotateY();
        }

        return this.getDefaultState().with(HORIZONTAL_FACING, placementDir).with(WATERLOGGED, Boolean.valueOf(waterloggedFlag));
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.get(WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }
        return stateIn;
    }

    private boolean isInAttachablePos(IWorldReader worldIn, BlockPos pos) {
        if (worldIn.getBlockState(pos.down()).getBlock() == Blocks.LANTERN) {
            return true;
        }
        return Block.hasEnoughSolidSide(worldIn, pos.down(), Direction.UP);
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, WATERLOGGED);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return !state.get(WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
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
