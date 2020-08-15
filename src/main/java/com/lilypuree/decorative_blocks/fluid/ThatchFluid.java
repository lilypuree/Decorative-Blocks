package com.lilypuree.decorative_blocks.fluid;

import com.lilypuree.decorative_blocks.setup.Registration;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.ILiquidContainer;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
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

public abstract class ThatchFluid extends FlowingFluid {

    ResourceLocation thatchStillTexture = new ResourceLocation("decorative_blocks", "block/thatch_still");
    ResourceLocation thatchFlowingTexture = new ResourceLocation("decorative_blocks", "block/thatch_flowing");

    @Override
    protected FluidAttributes createAttributes() {
        return FluidAttributes.builder(thatchStillTexture, thatchFlowingTexture).overlay(thatchStillTexture).sound(SoundEvents.BLOCK_CROP_BREAK, SoundEvents.BLOCK_CROP_BREAK).density(200).viscosity(2000).build(this);
    }



    public Fluid getFlowingFluid() {
        return Registration.FLOWING_THATCH.get();
    }

    public Fluid getStillFluid() {
        return Registration.STILL_THATCH.get();
    }

    public Item getFilledBucket() {
        return Items.BUCKET;
    }

    public int getSlopeFindDistance(IWorldReader worldIn) {
        return 2;
    }

    public BlockState getBlockState(IFluidState state) {
        return Registration.THATCH.get().getDefaultState().with(FlowingFluidBlock.LEVEL, Integer.valueOf(getLevelFromState(state)));
    }

    public boolean isEquivalentTo(Fluid fluidIn) {
        return fluidIn == Registration.STILL_THATCH.get() || fluidIn == Registration.FLOWING_THATCH.get();
    }

    public int getLevelDecreasePerBlock(IWorldReader worldIn) {
        return 4;
    }

    protected void beforeReplacingBlock(IWorld worldIn, BlockPos pos, BlockState state) {
        TileEntity tileentity = state.getBlock().hasTileEntity() ? worldIn.getTileEntity(pos) : null;
        Block.spawnDrops(state, worldIn.getWorld(), pos, tileentity);
    }

    public boolean canDisplace(IFluidState p_215665_1_, IBlockReader p_215665_2_, BlockPos p_215665_3_, Fluid p_215665_4_, Direction p_215665_5_) {
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

    protected void flowInto(IWorld worldIn, BlockPos pos, BlockState blockStateIn, Direction direction, IFluidState fluidStateIn) {
        if (direction == Direction.DOWN) {
            boolean shouldFlowInto = false;


            for (Direction dir : Direction.Plane.HORIZONTAL){
                BlockPos supportPos = pos.offset(dir);
                BlockState supportBlock = worldIn.getBlockState(supportPos);
                IFluidState sourceFluid = worldIn.getFluidState(pos.offset(dir).up());
                if(supportBlock.isSolidSide(worldIn, supportPos, dir.getOpposite()) && !sourceFluid.isEmpty()){
                    shouldFlowInto = true;
                }
            }
//            IFluidState fluidState = worldIn.getFluidState(pos.up());
//            if (fluidState.get(FALLING)) {
//                return;
//            }
            if(!shouldFlowInto){
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
        protected void fillStateContainer(StateContainer.Builder<Fluid, IFluidState> builder) {
            super.fillStateContainer(builder);
            builder.add(LEVEL_1_8);
        }

        public int getLevel(IFluidState p_207192_1_) {
            return p_207192_1_.get(LEVEL_1_8);
        }

        public boolean isSource(IFluidState state) {
            return false;
        }
    }

    public static class Source extends ThatchFluid {
        @Override
        protected void fillStateContainer(StateContainer.Builder<Fluid, IFluidState> builder) {
            super.fillStateContainer(builder);
        }

        public int getLevel(IFluidState p_207192_1_) {
            return 8;
        }

        public boolean isSource(IFluidState state) {
            return true;
        }
    }
}
