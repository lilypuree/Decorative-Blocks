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
        return FluidAttributes.builder(referenceHolder.thatchStillTexture, referenceHolder.thatchFlowingTexture).overlay(referenceHolder.thatchStillTexture).sound(SoundEvents.BLOCK_CROP_BREAK, SoundEvents.BLOCK_CROP_BREAK).density(200).viscosity(2000).build(this);
    }

    public Item getFilledBucket() {
        return Items.BUCKET;
    }

    public FluidReferenceHolder getReferenceHolder() {
        return referenceHolder;
    }

    @Override
    public Fluid getFlowingFluid() {
        return referenceHolder.getFlowingFluid();
    }

    @Override
    public Fluid getStillFluid() {
        return referenceHolder.getStillFluid();
    }

    public int getSlopeFindDistance(IWorldReader worldIn) {
        return 2;
    }

    public BlockState getBlockState(FluidState state) {
        return referenceHolder.getFluidBlock().getDefaultState().with(FlowingFluidBlock.LEVEL, Integer.valueOf(getLevelFromState(state)));
    }

    public boolean isEquivalentTo(Fluid fluidIn) {
        return fluidIn == referenceHolder.getFlowingFluid() || fluidIn == referenceHolder.getStillFluid();
    }

    public int getLevelDecreasePerBlock(IWorldReader worldIn) {
        return 4;
    }


    @Override
    protected void beforeReplacingBlock(IWorld worldIn, BlockPos pos, BlockState state) {
        TileEntity tileentity = state.getBlock().hasTileEntity(state) ? worldIn.getTileEntity(pos) : null;
        Block.spawnDrops(state, worldIn.getWorld(), pos, tileentity);
    }

    public boolean canDisplace(FluidState p_215665_1_, IBlockReader p_215665_2_, BlockPos p_215665_3_, Fluid p_215665_4_, Direction p_215665_5_) {
        return false;
        //from lava fluid
//        return p_215665_1_.getActualHeight(p_215665_2_, p_215665_3_) >= 0.44444445F && p_215665_4_.isIn(FluidTags.WATER);
    }

    public int getTickRate(IWorldReader p_205569_1_) {
        return 8;
    }

    protected boolean canSourcesMultiply() {
        return false;
    }

    protected void flowInto(IWorld worldIn, BlockPos pos, BlockState blockStateIn, Direction direction, FluidState fluidStateIn) {
        if (direction == Direction.DOWN) {
            boolean shouldFlowInto = false;


            for (Direction dir : Direction.Plane.HORIZONTAL) {
                BlockPos supportPos = pos.offset(dir);
                BlockState supportBlock = worldIn.getBlockState(supportPos);
                FluidState sourceFluid = worldIn.getFluidState(supportPos.up());
                if (supportBlock.isSolidSide(worldIn, supportPos, dir.getOpposite()) && !sourceFluid.isEmpty()) {
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
            ((ILiquidContainer) blockStateIn.getBlock()).receiveFluid(worldIn, pos, blockStateIn, fluidStateIn);
        } else {
            if (!blockStateIn.isAir()) {
                this.beforeReplacingBlock(worldIn, pos, blockStateIn);
            }

            worldIn.setBlockState(pos, fluidStateIn.getBlockState(), 3);
        }
    }


    protected boolean ticksRandomly() {
        return true;
    }

    protected float getExplosionResistance() {
        return 100.0F;
    }

    public static class Flowing extends ThatchFluid {
        public Flowing(FluidReferenceHolder referenceHolder) {
            super(referenceHolder);
        }

        protected void fillStateContainer(StateContainer.Builder<Fluid, FluidState> builder) {
            super.fillStateContainer(builder);
            builder.add(LEVEL_1_8);
        }

        public int getLevel(FluidState p_207192_1_) {
            return p_207192_1_.get(LEVEL_1_8);
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
        protected void fillStateContainer(StateContainer.Builder<Fluid, FluidState> builder) {
            super.fillStateContainer(builder);
        }

        public int getLevel(FluidState p_207192_1_) {
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
