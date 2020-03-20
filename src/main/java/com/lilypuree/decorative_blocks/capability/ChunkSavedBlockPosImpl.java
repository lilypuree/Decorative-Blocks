package com.lilypuree.decorative_blocks.capability;

import net.minecraft.util.math.BlockPos;

import java.util.HashSet;
import java.util.Set;

public class ChunkSavedBlockPosImpl implements ChunkSavedBlockPos {

    Set<BlockPos> poses = new HashSet<>();

    @Override
    public void addBlockPos(BlockPos pos) {
        poses.add(pos);
    }

    @Override
    public void removeBlockPos(BlockPos pos) {
        poses.remove(pos);
    }

    @Override
    public Set<BlockPos> getSavedBlockPoses() {
        return poses;
    }

    @Override
    public void setSavedBlockPoses(Set<BlockPos> poses) {
        this.poses = poses;
    }
}
