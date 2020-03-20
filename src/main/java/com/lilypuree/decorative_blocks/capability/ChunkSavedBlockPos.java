package com.lilypuree.decorative_blocks.capability;

import net.minecraft.util.math.BlockPos;

import java.util.Set;

public interface ChunkSavedBlockPos {

    void addBlockPos(BlockPos pos);

    void removeBlockPos(BlockPos pos);

    Set<BlockPos> getSavedBlockPoses();

    void setSavedBlockPoses(Set<BlockPos> poses);
}
