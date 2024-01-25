package lilypuree.decorative_blocks.fluid;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;

import java.util.function.Supplier;

public abstract class ThatchFluid extends FlowingFluid {
    protected FluidReferenceHolder referenceHolder;

    public ThatchFluid(FluidReferenceHolder referenceHolder) {
        this.referenceHolder = referenceHolder;
    }

    @Override
    public Fluid getFlowing() {
        return referenceHolder.flowing().get();
    }

    @Override
    public Fluid getSource() {
        return referenceHolder.source().get();
    }

    public Item getBucket() {
        return Items.BUCKET;
    }

    public FluidReferenceHolder getReferenceHolder() {
        return referenceHolder;
    }

    @Override

    protected void beforeDestroyingBlock(LevelAccessor worldIn, BlockPos pos, BlockState state) {
        BlockEntity tileentity = state.hasBlockEntity() ? worldIn.getBlockEntity(pos) : null;
        Block.dropResources(state, worldIn, pos, tileentity);
    }

    @Override
    public int getSlopeFindDistance(LevelReader worldIn) {
        return 2;
    }

    @Override
    public BlockState createLegacyBlock(FluidState state) {
        return referenceHolder.liquidBlock.get().defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(state));
    }

    public boolean isSame(Fluid fluidIn) {
        return fluidIn == getFlowing() || fluidIn == getSource();
    }

    @Override
    public boolean isSource(FluidState fluidState) {
        return false;
    }

    @Override
    public int getDropOff(LevelReader worldIn) {
        return 4;
    }

    @Override
    public boolean canBeReplacedWith(FluidState p_215665_1_, BlockGetter p_215665_2_, BlockPos p_215665_3_, Fluid p_215665_4_, Direction p_215665_5_) {
        return false;
    }

    @Override
    public int getTickDelay(LevelReader level) {
        return 8;
    }

    @Override
    protected boolean canConvertToSource(Level level) {
        return false;
    }

    @Override
    protected void spreadTo(LevelAccessor worldIn, BlockPos pos, BlockState blockStateIn, Direction direction, FluidState fluidStateIn) {
        if (direction == Direction.DOWN) {
            boolean shouldFlowInto = false;


            for (Direction dir : Direction.Plane.HORIZONTAL) {
                BlockPos supportPos = pos.relative(dir);
                BlockState supportBlock = worldIn.getBlockState(supportPos);
                FluidState sourceFluid = worldIn.getFluidState(supportPos.above());
                if (supportBlock.isFaceSturdy(worldIn, supportPos, dir.getOpposite()) && !sourceFluid.isEmpty()) {
                    shouldFlowInto = true;
                }
            }
//            IFluidState fluidState = worldIn.getFluidState(pos.up());
//            if (fluidState.get(FALLING)) {
//                return;
//            }
            if (!shouldFlowInto) {
                return;
            }
        }
        if (blockStateIn.getBlock() instanceof LiquidBlockContainer) {
            ((LiquidBlockContainer) blockStateIn.getBlock()).placeLiquid(worldIn, pos, blockStateIn, fluidStateIn);
        } else {
            if (!blockStateIn.isAir()) {
                this.beforeDestroyingBlock(worldIn, pos, blockStateIn);
            }

            worldIn.setBlock(pos, fluidStateIn.createLegacyBlock(), 3);
        }
    }

    @Override
    protected boolean isRandomlyTicking() {
        return true;
    }

    @Override
    protected float getExplosionResistance() {
        return 100.0F;
    }

    public static class Flowing extends ThatchFluid {
        public Flowing(FluidReferenceHolder referenceHolder) {
            super(referenceHolder);
        }

        @Override
        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }

        @Override
        public int getAmount(FluidState fluidState) {
            return fluidState.getValue(LEVEL);
        }

        @Override
        public boolean isSource(FluidState state) {
            return false;
        }
    }

    public static class Source extends ThatchFluid {

        public Source(FluidReferenceHolder referenceHolder) {
            super(referenceHolder);
        }

        @Override
        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
        }

        @Override
        public int getAmount(FluidState p_207192_1_) {
            return 8;
        }

        @Override
        public boolean isSource(FluidState state) {
            return true;
        }
    }


    public record FluidReferenceHolder(Supplier<Block> sourceBlock, Supplier<LiquidBlock> liquidBlock,
                                       Supplier<FlowingFluid> flowing, Supplier<FlowingFluid> source,
                                       ResourceLocation thatchStillTexture,
                                       ResourceLocation thatchFlowingTexture, int color) {


        public Block getSourceBlock() {
            return sourceBlock.get();
        }

        public LiquidBlock getLiquidBlock() {
            return liquidBlock.get();
        }

        public FlowingFluid getFlowingFluid() {
            return flowing.get();
        }

        public FlowingFluid getSourceFluid() {
            return source.get();
        }

    }
}
