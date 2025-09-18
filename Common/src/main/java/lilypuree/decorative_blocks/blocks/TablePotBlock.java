package lilypuree.decorative_blocks.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class TablePotBlock extends Block implements SimpleWaterloggedBlock {
    protected static final VoxelShape TABLEPOT_SHAPE = Shapes.or(
            Block.box(0D, 0.0D, 0D, 16D, 10D, 16D),
            Block.box(3D, 10D, 3D, 13D, 16D, 13D));
    protected static final VoxelShape TABLEPOT_SUPPORT_SHAPE = Shapes.block();

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public TablePotBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(WATERLOGGED, false));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState ifluidstate = context.getLevel().getFluidState(context.getClickedPos());
        boolean waterloggedFlag = ifluidstate.is(FluidTags.WATER) && ifluidstate.getAmount() == 8;
        return defaultBlockState().setValue(WATERLOGGED, waterloggedFlag);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.getValue(WATERLOGGED)) {
            worldIn.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        }
        return stateIn;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
        return TABLEPOT_SHAPE;
    }

    @Override
    public VoxelShape getBlockSupportShape(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
        return TABLEPOT_SUPPORT_SHAPE;
    }
}
