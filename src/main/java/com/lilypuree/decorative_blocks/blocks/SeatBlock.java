package com.lilypuree.decorative_blocks.blocks;

import com.lilypuree.decorative_blocks.datagen.types.IWoodType;
import com.lilypuree.decorative_blocks.entity.DummyEntityForSitting;
import com.lilypuree.decorative_blocks.items.SwitchableBlockItem;
import com.lilypuree.decorative_blocks.state.ModBlockProperties;
import net.minecraft.block.*;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
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
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class SeatBlock extends HorizontalBlock implements IWaterLoggable, IWoodenBlock {
    protected static final VoxelShape POST_SHAPE = Block.makeCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 4.0D, 10.0D);
    protected static final VoxelShape TOP_POST = Block.makeCuboidShape(6.0D, 7.0D, 6.0D, 10.0D, 16.0D, 10.0D);
    protected static final VoxelShape JOIST_NS = Block.makeCuboidShape(0, 4.0D, 4D, 16D, 7D, 12D);
    protected static final VoxelShape JOIST_EW = Block.makeCuboidShape(4.0D, 4.0D, 0D, 12D, 7D, 16D);
    protected static final VoxelShape SEAT_NS = VoxelShapes.or(POST_SHAPE, JOIST_NS);
    protected static final VoxelShape SEAT_EW = VoxelShapes.or(POST_SHAPE, JOIST_EW);
    protected static final VoxelShape JOIST_POST_NS = VoxelShapes.or(TOP_POST, JOIST_NS);
    protected static final VoxelShape JOIST_POST_EW = VoxelShapes.or(TOP_POST, JOIST_NS);
    protected static final VoxelShape SEAT_POST_NS = VoxelShapes.or(SEAT_NS, TOP_POST);
    protected static final VoxelShape SEAT_POST_EW = VoxelShapes.or(SEAT_EW, TOP_POST);

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty OCCUPIED = BlockStateProperties.OCCUPIED;
    public static final BooleanProperty ATTACHED = BlockStateProperties.ATTACHED;
    public static final BooleanProperty POST = ModBlockProperties.POST;

    private IWoodType woodType;

    public SeatBlock(Block.Properties properties, IWoodType woodType) {
        super(properties);
        this.woodType = woodType;
        this.setDefaultState(this.getStateContainer().getBaseState().with(WATERLOGGED, false).with(OCCUPIED, false).with(ATTACHED, false).with(POST, false));
    }

    @Override
    public IWoodType getWoodType() {
        return woodType;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Direction facing = state.get(HORIZONTAL_FACING);
        boolean attached = state.get(ATTACHED);
        boolean post = state.get(POST);
        switch (facing) {
            case NORTH:
            case SOUTH:
                return (attached) ? (post ? SEAT_POST_NS : SEAT_NS) : (post ? JOIST_POST_NS : JOIST_NS);
            case EAST:
            case WEST:
                return (attached) ? (post ? SEAT_POST_EW : SEAT_EW) : (post ? JOIST_POST_EW : JOIST_EW);
        }
        return SEAT_NS;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getPos();
        FluidState ifluidstate = world.getFluidState(pos);
        ItemStack stack = context.getItem();
        boolean waterloggedFlag = ifluidstate.isTagged(FluidTags.WATER) && ifluidstate.getLevel() == 8;
        boolean attachedFlag = isInAttachablePos(world, pos);

        Direction facingDir = context.getFace();
        Direction placementDir;
        if (facingDir == Direction.DOWN || facingDir == Direction.UP) {
            placementDir = context.getPlacementHorizontalFacing().getOpposite();
        } else {
            placementDir = facingDir.rotateY();
        }

        BlockState blockstate = this.getDefaultState().with(HORIZONTAL_FACING, placementDir).with(WATERLOGGED, waterloggedFlag).with(OCCUPIED, false).with(ATTACHED, attachedFlag);
        if (stack.getItem() instanceof SwitchableBlockItem) {
            blockstate = ((SwitchableBlockItem<?, ?>) stack.getItem()).getSwitchedState(blockstate, stack);
        }
        return blockstate;
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.get(WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }
        if (facing == Direction.DOWN) {
            return stateIn.with(ATTACHED, isInAttachablePos(worldIn, currentPos));
        } else {
            return stateIn;
        }
    }

    private boolean isInAttachablePos(IWorldReader worldIn, BlockPos pos) {
        if (worldIn.getBlockState(pos.down()).getBlock() == Blocks.LANTERN) {
            return true;
        }
        return Block.hasEnoughSolidSide(worldIn, pos.down(), Direction.UP);
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, WATERLOGGED, OCCUPIED, ATTACHED, POST);
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
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack heldItem = player.getHeldItem(handIn);
        BlockState upperBlock = worldIn.getBlockState(pos.up());
        boolean canSit = hit.getFace() == Direction.UP && !state.get(OCCUPIED) && !state.get(POST) && heldItem.isEmpty() && upperBlock.isAir(worldIn, pos.up()) && isPlayerInRange(player, pos);
        Item item = heldItem.getItem();
        boolean isSeatAttachableItem = item instanceof BlockItem && ((BlockItem) item).getBlock() instanceof LanternBlock;
        boolean canAttachLantern = hit.getFace() == Direction.DOWN && isSeatAttachableItem && worldIn.getBlockState(pos.down()).isAir(worldIn, pos.down());
        if (!worldIn.isRemote()) {
            if (canSit) {
                DummyEntityForSitting seat = new DummyEntityForSitting(worldIn, pos);
                worldIn.addEntity(seat);
                player.startRiding(seat);
                return ActionResultType.SUCCESS;
            } else if (canAttachLantern) {
                BlockState newState = state.with(ATTACHED, Boolean.TRUE);
                worldIn.setBlockState(pos, newState);
                worldIn.notifyBlockUpdate(pos, state, newState, 3);
                worldIn.setBlockState(pos.down(), (((BlockItem) item).getBlock()).getDefaultState().with(BlockStateProperties.HANGING, Boolean.TRUE), 16);
                if (!player.isCreative()) {
                    heldItem.shrink(1);
                }
                return ActionResultType.SUCCESS;
            }
        }

        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }


    private static boolean isPlayerInRange(PlayerEntity player, BlockPos pos) {
        BlockPos playerPos = player.getPosition();
        int blockReachDistance = 2;

        if (blockReachDistance == 0) //player has to stand on top of the block
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
        List<DummyEntityForSitting> entities = worldIn.getEntitiesWithinAABB(DummyEntityForSitting.class, new AxisAlignedBB(x, y, z, x, y, z));
        for (DummyEntityForSitting entity : entities) {
            entity.remove();
        }
        super.onReplaced(state, worldIn, pos, newState, isMoving);
    }

    @Override
    public boolean allowsMovement(BlockState p_196266_1_, IBlockReader p_196266_2_, BlockPos p_196266_3_, PathType p_196266_4_) {
        return false;
    }
}
