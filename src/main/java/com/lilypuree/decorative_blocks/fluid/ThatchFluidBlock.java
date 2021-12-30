package com.lilypuree.decorative_blocks.fluid;

import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.function.Supplier;

import net.minecraft.block.AbstractBlock.Properties;

public class ThatchFluidBlock extends FlowingFluidBlock {
    public ThatchFluidBlock(Supplier<? extends FlowingFluid> supplier, Properties properties) {
        super(supplier, properties);
    }

    @Override
    public void entityInside(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entityIn;
            boolean isPlayerMoving = player.getDeltaMovement().length() > 0.1;
            if (worldIn.random.nextFloat() < 0.1F && isPlayerMoving) {
                worldIn.playSound(player, pos, SoundEvents.GRASS_HIT, SoundCategory.BLOCKS, 0.8f, 1.5f);
            }
        }
    }

    @Override
    public Fluid takeLiquid(IWorld worldIn, BlockPos pos, BlockState state) {
        return Fluids.EMPTY;
    }
}
