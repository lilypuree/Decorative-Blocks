package lilypuree.decorative_blocks.blocks;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CrossCollisionBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;

public class PalisadeBlock extends CrossCollisionBlock implements IWoodenBlock {
    public static final MapCodec<PalisadeBlock> CODEC = RecordCodecBuilder.mapCodec(
            inst -> inst.group(WoodType.CODEC.fieldOf("wood_type").forGetter(block -> block.woodType), propertiesCodec()).apply(inst, PalisadeBlock::new)
    );
    @Override
    protected MapCodec<? extends CrossCollisionBlock> codec() {
        return CODEC;
    }
    
    private WoodType woodType;

    public PalisadeBlock(WoodType woodType, Properties properties) {
        super(3.0F, 3.0F, 16.0F, 16.0F, 24.0F, properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(NORTH, Boolean.FALSE).setValue(EAST, Boolean.FALSE).setValue(SOUTH, Boolean.FALSE).setValue(WEST, Boolean.FALSE).setValue(WATERLOGGED, Boolean.FALSE));
        this.woodType = woodType;
    }

    @Override
    public WoodType getWoodType() {
        return woodType;
    }

    @Override
    public boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
        return false;
    }

    public boolean canConnect(BlockState state, boolean flag0, Direction direction) {
        Block block = state.getBlock();
//        boolean flag = block.isIn(BlockTags.FENCES) && p_220111_1_.getMaterial() == this.material;
        boolean flag = block instanceof PalisadeBlock || block instanceof FenceGateBlock && FenceGateBlock.connectsToDirection(state, direction);
        return !isExceptionForConnection(state) && flag0 || flag;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockGetter iblockreader = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        FluidState ifluidstate = context.getLevel().getFluidState(context.getClickedPos());
        BlockPos blockpos1 = blockpos.north();
        BlockPos blockpos2 = blockpos.east();
        BlockPos blockpos3 = blockpos.south();
        BlockPos blockpos4 = blockpos.west();
        BlockState blockstate = iblockreader.getBlockState(blockpos1);
        BlockState blockstate1 = iblockreader.getBlockState(blockpos2);
        BlockState blockstate2 = iblockreader.getBlockState(blockpos3);
        BlockState blockstate3 = iblockreader.getBlockState(blockpos4);
        return super.getStateForPlacement(context)
                .setValue(NORTH, this.canConnect(blockstate, blockstate.isFaceSturdy(iblockreader, blockpos1, Direction.SOUTH), Direction.SOUTH))
                .setValue(EAST, this.canConnect(blockstate1, blockstate1.isFaceSturdy(iblockreader, blockpos2, Direction.WEST), Direction.WEST))
                .setValue(SOUTH, this.canConnect(blockstate2, blockstate2.isFaceSturdy(iblockreader, blockpos3, Direction.NORTH), Direction.NORTH))
                .setValue(WEST, this.canConnect(blockstate3, blockstate3.isFaceSturdy(iblockreader, blockpos4, Direction.EAST), Direction.EAST))
                .setValue(WATERLOGGED, ifluidstate.getType() == Fluids.WATER);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.getValue(WATERLOGGED)) {
            worldIn.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        }
        return facing.getAxis().getPlane() == Direction.Plane.HORIZONTAL ?
                stateIn.setValue(PROPERTY_BY_DIRECTION.get(facing), this.canConnect(facingState, facingState.isFaceSturdy(worldIn, facingPos, facing.getOpposite()), facing.getOpposite()))
                : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, WEST, SOUTH, WATERLOGGED);
    }

  
}
