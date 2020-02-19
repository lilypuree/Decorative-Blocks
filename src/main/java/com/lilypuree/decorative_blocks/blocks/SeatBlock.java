package com.lilypuree.decorative_blocks.blocks;

import com.lilypuree.decorative_blocks.entity.DummyEntityForSitting;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class SeatBlock extends HorizontalBlock implements IWaterLoggable{
    private static final VoxelShape SEAT_SHAPE_NS = Block.makeCuboidShape(0, 0.0D, 4D, 16D, 7D, 12D);
    private static final VoxelShape SEAT_SHAPE_EW = Block.makeCuboidShape(4D, 0.0D, 0, 12D, 7D, 16D);

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty OCCUPIED = BlockStateProperties.OCCUPIED;

    public SeatBlock(Block.Properties properties){
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Direction facing = state.get(HORIZONTAL_FACING);
        switch (facing){
            case NORTH:case SOUTH: return SEAT_SHAPE_NS;
            case EAST:case WEST: return SEAT_SHAPE_EW;
        }
        return SEAT_SHAPE_NS;
    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState blockstate = context.getWorld().getBlockState(context.getPos());
        IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
        boolean flag = ifluidstate.isTagged(FluidTags.WATER) && ifluidstate.getLevel() == 8;

        return this.getDefaultState().with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite()).with(WATERLOGGED, Boolean.valueOf(flag)).with(OCCUPIED, Boolean.FALSE);
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, WATERLOGGED, OCCUPIED);
    }

    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return !state.get(WATERLOGGED);
    }

    public IFluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack heldItem = player.getHeldItem(handIn);
        BlockState upperBlock = worldIn.getBlockState(pos.up());
        boolean canSit = hit.getFace() == Direction.UP && !state.get(OCCUPIED) && heldItem.isEmpty() && upperBlock.isAir(worldIn,pos.up()) && isPlayerInRange(player, pos);
        if(!worldIn.isRemote() && canSit){
            DummyEntityForSitting seat = new DummyEntityForSitting(worldIn, pos);
            worldIn.addEntity(seat);
            player.startRiding(seat);
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }

    private static boolean isPlayerInRange(PlayerEntity player, BlockPos pos)
    {
        BlockPos playerPos = player.getPosition();
        int blockReachDistance = 2;

        if(blockReachDistance == 0) //player has to stand on top of the block
            return playerPos.getY() - pos.getY() <= 1 && playerPos.getX() - pos.getX() == 0 && playerPos.getZ() - pos.getZ() == 0;

        pos = pos.add(0.5D, 0.5D, 0.5D);

        AxisAlignedBB range = new AxisAlignedBB(pos.getX() + blockReachDistance, pos.getY() + blockReachDistance, pos.getZ() + blockReachDistance, pos.getX() - blockReachDistance, pos.getY() - blockReachDistance, pos.getZ() - blockReachDistance);

        playerPos = playerPos.add(0.5D, 0.5D, 0.5D);
        return range.minX <= playerPos.getX() && range.minY <= playerPos.getY() && range.minZ <= playerPos.getZ() && range.maxX >= playerPos.getX() && range.maxY >= playerPos.getY() && range.maxZ >= playerPos.getZ();
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();
        List<DummyEntityForSitting> entities = worldIn.getEntitiesWithinAABB(DummyEntityForSitting.class, new AxisAlignedBB(x, y, z, x,y,z));
        for(DummyEntityForSitting entity : entities){
            entity.remove();
        }
        super.onReplaced(state, worldIn, pos, newState, isMoving);
    }


    @Override
    public boolean isFlammable(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return true;
    }

    @Override
    public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return 20;
    }
}
