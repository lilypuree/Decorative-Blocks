package com.lilypuree.decorative_blocks.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IEnviromentBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class ChandelierBlock extends Block {
    private final VoxelShape CHANDELIER_SHAPE = Block.makeCuboidShape(2D, 0.0D, 2D, 14D, 12D, 14D);

    public ChandelierBlock(Block.Properties properties){
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return CHANDELIER_SHAPE;
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        double d0 = (double)pos.getX() + 0.5D;
        double d1 = (double)pos.getY() + 0.7D;
        double d2 = (double)pos.getZ() + 0.5D;

        double off1 = 0.125;
        double off2 = 0.385;
        worldIn.addParticle(ParticleTypes.SMOKE, d0-off1, d1, d2-off2, 0.0D, 0.0D, 0.0D);
        worldIn.addParticle(ParticleTypes.SMOKE, d0-off2, d1, d2+off1, 0.0D, 0.0D, 0.0D);
        worldIn.addParticle(ParticleTypes.SMOKE, d0+off1, d1, d2+off2, 0.0D, 0.0D, 0.0D);
        worldIn.addParticle(ParticleTypes.SMOKE, d0+off2, d1, d2-off1, 0.0D, 0.0D, 0.0D);
        worldIn.addParticle(ParticleTypes.FLAME, d0-off1, d1, d2-off2, 0.0D, 0.0D, 0.0D);
        worldIn.addParticle(ParticleTypes.FLAME, d0-off2, d1, d2+off1, 0.0D, 0.0D, 0.0D);
        worldIn.addParticle(ParticleTypes.FLAME, d0+off1, d1, d2+off2, 0.0D, 0.0D, 0.0D);
        worldIn.addParticle(ParticleTypes.FLAME, d0+off2, d1, d2-off1, 0.0D, 0.0D, 0.0D);
      }

    @Override
    public int getLightValue(BlockState state) {
        return 15;
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

}
