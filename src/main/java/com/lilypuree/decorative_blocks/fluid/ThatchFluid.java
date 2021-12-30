package com.lilypuree.decorative_blocks.fluid;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.ILiquidContainer;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.fluids.FluidAttributes;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public abstract class ThatchFluid extends FlowingFluid {
    public static Map<Block, FluidReferenceHolder> shearMap = new HashMap<>();
    private FluidReferenceHolder referenceHolder;

    public ThatchFluid(FluidReferenceHolder referenceHolder) {
        this.referenceHolder = referenceHolder;
    }

    @Override
    protected FluidAttributes createAttributes() {
        return FluidAttributes.builder(referenceHolder.thatchStillTexture, referenceHolder.thatchFlowingTexture).overlay(referenceHolder.thatchStillTexture).sound(SoundEvents.CROP_BREAK, SoundEvents.CROP_BREAK).density(200).viscosity(2000).build(this);
    }

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

    public int getSlopeFindDistance(IWorldReader worldIn) {
        return 2;
    }

    public BlockState createLegacyBlock(FluidState state) {
        return referenceHolder.getFluidBlock().defaultBlockState().setValue(FlowingFluidBlock.LEVEL, Integer.valueOf(getLegacyLevel(state)));
    }

    public boolean isSame(Fluid fluidIn) {
        return fluidIn == referenceHolder.getFlowingFluid() || fluidIn == referenceHolder.getStillFluid();
    }

    public int getDropOff(IWorldReader worldIn) {
        return 4;
    }


    @Override
    protected void beforeDestroyingBlock(IWorld worldIn, BlockPos pos, BlockState state) {
        TileEntity tileentity = state.getBlock().hasTileEntity(state) ? worldIn.getBlockEntity(pos) : null;
        Block.dropResources(state, worldIn, pos, tileentity);
    }

    public boolean canBeReplacedWith(FluidState p_215665_1_, IBlockReader p_215665_2_, BlockPos p_215665_3_, Fluid p_215665_4_, Direction p_215665_5_) {
        return false;
        //from lava fluid
//        return p_215665_1_.getActualHeight(p_215665_2_, p_215665_3_) >= 0.44444445F && p_215665_4_.isIn(FluidTags.WATER);
    }

    public int getTickDelay(IWorldReader p_205569_1_) {
        return 8;
    }

    protected boolean canConvertToSource() {
        return false;
    }

    protected void spreadTo(IWorld worldIn, BlockPos pos, BlockState blockStateIn, Direction direction, FluidState fluidStateIn) {
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
        if (blockStateIn.getBlock() instanceof ILiquidContainer) {
            ((ILiquidContainer) blockStateIn.getBlock()).placeLiquid(worldIn, pos, blockStateIn, fluidStateIn);
        } else {
            if (!blockStateIn.isAir()) {
                this.beforeDestroyingBlock(worldIn, pos, blockStateIn);
            }

            worldIn.setBlock(pos, fluidStateIn.createLegacyBlock(), 3);
        }
    }


    protected boolean isRandomlyTicking() {
        return true;
    }

    protected float getExplosionResistance() {
        return 100.0F;
    }

    public static class Flowing extends ThatchFluid {
        public Flowing(FluidReferenceHolder referenceHolder) {
            super(referenceHolder);
        }

        protected void createFluidStateDefinition(StateContainer.Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }

        public int getAmount(FluidState p_207192_1_) {
            return p_207192_1_.getValue(LEVEL);
        }

        public boolean isSource(FluidState state) {
            return false;
        }
    }

    public static class Source extends ThatchFluid {
        public Source(FluidReferenceHolder referenceHolder) {
            super(referenceHolder);
        }

        @Override
        protected void createFluidStateDefinition(StateContainer.Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
        }

        public int getAmount(FluidState p_207192_1_) {
            return 8;
        }

        public boolean isSource(FluidState state) {
            return true;
        }
    }

    public static class FluidReferenceHolder {

        private Supplier<Block> sourceBlock;
        private ResourceLocation thatchStillTexture;
        private ResourceLocation thatchFlowingTexture;
        private Supplier<Fluid> flowingFluid;
        private Supplier<Fluid> stillFluid;
        private Supplier<Block> fluidBlock;
        private int color;

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
