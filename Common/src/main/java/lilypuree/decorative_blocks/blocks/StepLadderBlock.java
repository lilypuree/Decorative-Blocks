package lilypuree.decorative_blocks.blocks;

import lilypuree.decorative_blocks.blocks.state.ModBlockProperties;
import lilypuree.decorative_blocks.items.SwitchableBlockItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class StepLadderBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock {

    public static final BooleanProperty NARROW = ModBlockProperties.NARROW;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final VoxelShape[] NARROW_SHAPES;
    protected static final VoxelShape[] FULL_SHAPES;

    public StepLadderBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(NARROW, false).setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, NARROW, WATERLOGGED);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        Direction facing = state.getValue(FACING);
        boolean narrow = state.getValue(NARROW);
        if (narrow)
            return NARROW_SHAPES[facing.get2DDataValue()];
        else return FULL_SHAPES[facing.get2DDataValue()];
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        FluidState fluidState = level.getFluidState(pos);
        ItemStack stack = context.getItemInHand();
        boolean waterloggedFlag = fluidState.is(FluidTags.WATER) && fluidState.getAmount() == 8;
        Direction facingDir = context.getClickedFace();
        Direction placementDir;
        if (facingDir == Direction.DOWN || facingDir == Direction.UP) {
            placementDir = context.getHorizontalDirection();
        } else {
            placementDir = facingDir.getOpposite();
        }

        BlockState blockstate = this.defaultBlockState().setValue(FACING, placementDir).setValue(WATERLOGGED, waterloggedFlag);
        if (stack.getItem() instanceof SwitchableBlockItem<?, ?> switchableItem) {
            blockstate = switchableItem.getSwitchedState(blockstate, stack);
        }

        return blockstate;
    }

    public static void onStepLadderActivation(BlockState state, Level level, BlockPos pos) {
        level.setBlockAndUpdate(pos, state.cycle(NARROW));
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.getValue(WATERLOGGED)) {
            worldIn.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        }
        return stateIn;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
        return false;
    }

    static {
        FULL_SHAPES = new VoxelShape[4];
        NARROW_SHAPES = new VoxelShape[4];
        Direction.Plane.HORIZONTAL.stream().forEach(dir -> {
            int x = dir.getStepX();
            int z = dir.getStepZ();
            FULL_SHAPES[dir.get2DDataValue()] = createLadderShape(x, z, 16);
            NARROW_SHAPES[dir.get2DDataValue()] = createLadderShape(x, z, 10);
        });
    }

    private static VoxelShape createLadderShape(int x, int z, int length) {
        double w = length / 2.0D;
        double x1 = x == 0 ? 8D - w : 8 - 5 * x;
        double x2 = x == 0 ? 8D + w : 8 - 3 * x;
        double z1 = z == 0 ? 8D - w : 8 - 5 * z;
        double z2 = z == 0 ? 8D + w : 8 - 3 * z;

        VoxelShape box1 = Block.box(Math.min(x1, x2), 3D, Math.min(z1, z2), Math.max(x1, x2), 5D, Math.max(z1, z2));
        x1 = 16 - x1;
        x2 = 16 - x2;
        z1 = 16 - z1;
        z2 = 16 - z2;
        VoxelShape box2 = Block.box(Math.min(x1, x2), 11D, Math.min(z1, z2), Math.max(x1, x2), 13D, Math.max(z1, z2));
        return Shapes.or(box1, box2);
    }
}
