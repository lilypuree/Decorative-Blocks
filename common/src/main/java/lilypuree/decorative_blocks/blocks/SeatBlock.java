package lilypuree.decorative_blocks.blocks;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lilypuree.decorative_blocks.blocks.state.ModBlockProperties;
import lilypuree.decorative_blocks.entity.DummyEntityForSitting;
import lilypuree.decorative_blocks.items.SwitchableBlockItem;
import lilypuree.decorative_blocks.registration.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class SeatBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock, IWoodenBlock {
    public static final MapCodec<SeatBlock> CODEC = RecordCodecBuilder.mapCodec(
            inst -> inst.group(WoodType.CODEC.fieldOf("wood_type").forGetter(block -> block.woodType), propertiesCodec()).apply(inst, SeatBlock::new)
    );

    protected static final VoxelShape POST_SHAPE = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 4.0D, 10.0D);
    protected static final VoxelShape TOP_POST = Block.box(6.0D, 7.0D, 6.0D, 10.0D, 16.0D, 10.0D);
    protected static final VoxelShape JOIST_NS = Block.box(0, 4.0D, 4D, 16D, 7D, 12D);
    protected static final VoxelShape JOIST_EW = Block.box(4.0D, 4.0D, 0D, 12D, 7D, 16D);
    protected static final VoxelShape SEAT_NS = Shapes.or(POST_SHAPE, JOIST_NS);
    protected static final VoxelShape SEAT_EW = Shapes.or(POST_SHAPE, JOIST_EW);
    protected static final VoxelShape JOIST_POST_NS = Shapes.or(TOP_POST, JOIST_NS);
    protected static final VoxelShape JOIST_POST_EW = Shapes.or(TOP_POST, JOIST_EW);
    protected static final VoxelShape SEAT_POST_NS = Shapes.or(SEAT_NS, TOP_POST);
    protected static final VoxelShape SEAT_POST_EW = Shapes.or(SEAT_EW, TOP_POST);

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty OCCUPIED = BlockStateProperties.OCCUPIED;
    public static final BooleanProperty ATTACHED = BlockStateProperties.ATTACHED;
    public static final BooleanProperty POST = ModBlockProperties.POST;

    private WoodType woodType;

    public SeatBlock(WoodType woodType, Properties properties) {
        super(properties);
        this.woodType = woodType;
        this.registerDefaultState(this.getStateDefinition().any().setValue(WATERLOGGED, false).setValue(OCCUPIED, false).setValue(ATTACHED, false).setValue(POST, false));
    }

    @Override
    public WoodType getWoodType() {
        return woodType;
    }


    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        Direction facing = state.getValue(FACING);
        boolean attached = state.getValue(ATTACHED);
        boolean post = state.getValue(POST);
        return switch (facing) {
            case NORTH, SOUTH -> (attached) ? (post ? SEAT_POST_NS : SEAT_NS) : (post ? JOIST_POST_NS : JOIST_NS);
            case EAST, WEST -> (attached) ? (post ? SEAT_POST_EW : SEAT_EW) : (post ? JOIST_POST_EW : JOIST_EW);
            default -> SEAT_NS;
        };
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        FluidState ifluidstate = world.getFluidState(pos);
        ItemStack stack = context.getItemInHand();
        boolean waterloggedFlag = ifluidstate.is(FluidTags.WATER) && ifluidstate.getAmount() == 8;
        boolean attachedFlag = isInAttachablePos(world, pos);

        Direction facingDir = context.getClickedFace();
        Direction placementDir;
        if (facingDir == Direction.DOWN || facingDir == Direction.UP) {
            placementDir = context.getHorizontalDirection().getOpposite();
        } else {
            placementDir = facingDir.getClockWise();
        }

        BlockState blockstate = this.defaultBlockState().setValue(FACING, placementDir).setValue(WATERLOGGED, waterloggedFlag).setValue(OCCUPIED, false).setValue(ATTACHED, attachedFlag);
        if (stack.getItem() instanceof SwitchableBlockItem) {
            blockstate = ((SwitchableBlockItem) stack.getItem()).getSwitchedState(blockstate, stack);
        }
        return blockstate;
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.getValue(WATERLOGGED)) {
            worldIn.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        }
        if (facing == Direction.DOWN) {
            return stateIn.setValue(ATTACHED, isInAttachablePos(worldIn, currentPos));
        } else {
            return stateIn;
        }
    }

    private boolean isInAttachablePos(LevelReader worldIn, BlockPos pos) {
        if (worldIn.getBlockState(pos.below()).getBlock() == Blocks.LANTERN) {
            return true;
        }
        return Block.canSupportCenter(worldIn, pos.below(), Direction.UP);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, OCCUPIED, ATTACHED, POST);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return !state.getValue(WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        Item item = stack.getItem();

        if (!level.isClientSide && item instanceof BlockItem && ((BlockItem) item).getBlock() instanceof LanternBlock) {
            if (hit.getDirection() != Direction.DOWN || !level.getBlockState(pos.below()).isAir()) {
                return ItemInteractionResult.FAIL;
            }

            BlockState newState = state.setValue(ATTACHED, Boolean.TRUE);
            level.setBlockAndUpdate(pos, newState);
            level.sendBlockUpdated(pos, state, newState, 3);
            level.setBlock(pos.below(), (((BlockItem) item).getBlock()).defaultBlockState().setValue(BlockStateProperties.HANGING, Boolean.TRUE), Block.UPDATE_ALL | Block.UPDATE_KNOWN_SHAPE);
            stack.consume(1, player);
            return ItemInteractionResult.SUCCESS;
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hit);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (!level.isClientSide && hit.getDirection() == Direction.UP && !state.getValue(OCCUPIED)
                && !state.getValue(POST) && level.getBlockState(pos.above()).isAir() && isPlayerInRange(player, pos)) {
            DummyEntityForSitting seat = Registration.DUMMY_ENTITY_TYPE.get().create(level);
            seat.setSeatPos(pos);
            level.addFreshEntity(seat);
            player.startRiding(seat);
            return InteractionResult.SUCCESS;
        }
        return super.useWithoutItem(state, level, pos, player, hit);
    }

    private static boolean isPlayerInRange(Player player, BlockPos pos) {
        Vec3 position = pos.getCenter();
        int blockReachDistance = 2;

        AABB range = AABB.ofSize(position, blockReachDistance, blockReachDistance, blockReachDistance);
        return range.contains(player.position());
    }

    @Override
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();
        List<DummyEntityForSitting> entities = worldIn.getEntitiesOfClass(DummyEntityForSitting.class, new AABB(x, y, z, x + 1, y + 1, z + 1));
        for (DummyEntityForSitting entity : entities) {
            entity.remove(Entity.RemovalReason.DISCARDED);
        }
        super.onRemove(state, worldIn, pos, newState, isMoving);
    }

    @Override
    public boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
        return false;
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }
}
