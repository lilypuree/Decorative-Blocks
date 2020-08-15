package com.lilypuree.decorative_blocks.fluid;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.HoeItem;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.ToolType;

import java.util.function.Supplier;

public class ThatchFluidBlock extends FlowingFluidBlock {
    public ThatchFluidBlock(Supplier<? extends FlowingFluid> supplier, Properties p_i48368_1_) {
        super(supplier, p_i48368_1_);
    }

    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entityIn;
            boolean isPlayerMoving = player.getMotion().length() > 0.1;
            if (worldIn.rand.nextFloat() < 0.1F && isPlayerMoving) {
                worldIn.playSound(player, pos, SoundEvents.BLOCK_GRASS_HIT, SoundCategory.BLOCKS, 0.8f, 1.5f);
            }
        }
    }

    @Override
    public Fluid pickupFluid(IWorld worldIn, BlockPos pos, BlockState state) {
        return Fluids.EMPTY;
    }


    @Override
    public boolean isFlammable(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return true;
    }

    @Override
    public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return 80;
    }
}
