package lilypuree.decorative_blocks.fluid;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
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

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public abstract class ThatchFluid extends FlowingFluid {
    public static Map<Block, FluidReferenceHolder> shearMap = new HashMap<>();
    protected FluidReferenceHolder referenceHolder;

    public ThatchFluid(FluidReferenceHolder referenceHolder) {
        this.referenceHolder = referenceHolder;
    }

    public static void addThatchlikeFluid(FluidReferenceHolder referenceHolder){
        shearMap.put(referenceHolder.getSourceBlock(), referenceHolder);
    }

//    @Override
//    protected FluidAttributes createAttributes() {
//    }

    public Item getBucket() {
        return Items.BUCKET;
    }

    public FluidReferenceHolder getReferenceHolder() {
        return referenceHolder;
    }

    @Override
    public Fluid getFlowing() {
        return referenceHolder.getFlowingFluid();
    }

    @Override
    public Fluid getSource() {
        return referenceHolder.getStillFluid();
    }

    @Override
    public int getSlopeFindDistance(LevelReader worldIn) {
        return 2;
    }

    @Override
    public BlockState createLegacyBlock(FluidState state) {
        return referenceHolder.getFluidBlock().defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(state));
    }

    @Override
    public boolean isSame(Fluid fluidIn) {
        return fluidIn == referenceHolder.getFlowingFluid() || fluidIn == referenceHolder.getStillFluid();
    }

    @Override
    public int getDropOff(LevelReader worldIn) {
        return 4;
    }


    @Override
    protected void beforeDestroyingBlock(LevelAccessor worldIn, BlockPos pos, BlockState state) {
        BlockEntity tileentity = state.hasBlockEntity() ? worldIn.getBlockEntity(pos) : null;
        Block.dropResources(state, worldIn, pos, tileentity);
    }

    @Override
    public boolean canBeReplacedWith(FluidState p_215665_1_, BlockGetter p_215665_2_, BlockPos p_215665_3_, Fluid p_215665_4_, Direction p_215665_5_) {
        return false;
        //from lava fluid
//        return p_215665_1_.getActualHeight(p_215665_2_, p_215665_3_) >= 0.44444445F && p_215665_4_.isIn(FluidTags.WATER);
    }

    @Override
    public int getTickDelay(LevelReader level) {
        return 8;
    }

    @Override
    protected boolean canConvertToSource() {
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

    public abstract static class Flowing extends ThatchFluid {
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

    public abstract static class Source extends ThatchFluid {
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



    public static class FluidReferenceHolder {
        public Supplier<Block> sourceBlock;
        public ResourceLocation thatchStillTexture;
        public ResourceLocation thatchFlowingTexture;
        public Supplier<Fluid> flowingFluid;
        public Supplier<Fluid> stillFluid;
        public Supplier<Block> fluidBlock;
        public int color;

        public FluidReferenceHolder(Supplier<Block> sourceBlock, ResourceLocation thatchStillTexture, ResourceLocation thatchFlowingTexture, int color) {
            this.sourceBlock = sourceBlock;
            this.thatchStillTexture = thatchStillTexture;
            this.thatchFlowingTexture = thatchFlowingTexture;
            this.color = color;
        }

        public void setFlowingFluid(Supplier<Fluid> flowingFluid) {
            this.flowingFluid = flowingFluid;
        }

        public Fluid getFlowingFluid() {
            return flowingFluid.get();
        }

        public void setStillFluid(Supplier<Fluid> stillFluid) {
            this.stillFluid = stillFluid;
        }

        public Fluid getStillFluid() {
            return stillFluid.get();
        }


        public void setFluidBlock(Supplier<Block> fluidBlock) {
            this.fluidBlock = fluidBlock;
        }

        public Block getFluidBlock() {
            return fluidBlock.get();
        }

        public Block getSourceBlock() {
            return sourceBlock.get();
        }

        public int getColor() {
            return color;
        }

    }
}
