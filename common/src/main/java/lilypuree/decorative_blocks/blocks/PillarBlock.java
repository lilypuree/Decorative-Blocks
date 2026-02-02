package lilypuree.decorative_blocks.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PillarBlock extends Block implements SimpleWaterloggedBlock {
    public static MapCodec<PillarBlock> CODEC = simpleCodec(PillarBlock::new);

    @Override
    protected MapCodec<? extends Block> codec() {
        return CODEC;
    }
    
    protected final VoxelShape PILLAR_SHAPE = Block.box(2D, 0.0D, 2D, 14D, 16.0D, 14D);

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;




    public PillarBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return PILLAR_SHAPE;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState ifluidstate = context.getLevel().getFluidState(context.getClickedPos());
        boolean flag = ifluidstate.is(FluidTags.WATER) && ifluidstate.getAmount() == 8;
        return this.defaultBlockState().setValue(WATERLOGGED, flag);
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
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    @Override
    public boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
        return false;
    }
}
