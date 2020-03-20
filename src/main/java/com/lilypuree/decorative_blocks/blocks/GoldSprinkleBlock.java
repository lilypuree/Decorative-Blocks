package com.lilypuree.decorative_blocks.blocks;

import com.lilypuree.decorative_blocks.capability.ChunkSavedBlockPos;
import com.lilypuree.decorative_blocks.capability.ChunkSavedBlockPosProvider;
import com.lilypuree.decorative_blocks.setup.ClientProxy;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;

import java.util.Random;

public class GoldSprinkleBlock extends Block {

    public GoldSprinkleBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.empty();
    }

    @Override
    public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        LazyOptional<ChunkSavedBlockPos> savedBlockPos = worldIn.getChunkAt(pos).getCapability(ChunkSavedBlockPosProvider.CHUNK_SAVED_BLOCK_POS_CAPABILITY);
        savedBlockPos.ifPresent(save -> save.addBlockPos(pos));
        super.onBlockAdded(state, worldIn, pos, oldState, isMoving);
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        LazyOptional<ChunkSavedBlockPos> savedBlockPos = worldIn.getChunkAt(pos).getCapability(ChunkSavedBlockPosProvider.CHUNK_SAVED_BLOCK_POS_CAPABILITY);
        savedBlockPos.ifPresent(save->save.addBlockPos(pos));
        super.onReplaced(state, worldIn, pos, newState, isMoving);
    }

    @Override
    public int tickRate(IWorldReader worldIn) {
        return 4;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        ClientProxy.spawnGoldParticles(worldIn, pos, rand);
    }
}
