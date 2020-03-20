package com.lilypuree.decorative_blocks.capability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

public class ChunkSavedBlockPosStorage implements Capability.IStorage<ChunkSavedBlockPos> {

    @Nullable
    @Override
    public INBT writeNBT(Capability<ChunkSavedBlockPos> capability, ChunkSavedBlockPos instance, Direction side) {
        CompoundNBT tag = new CompoundNBT();
        long[] blockPoses = instance.getSavedBlockPoses().stream().mapToLong(pos -> pos.toLong()).toArray();
        tag.putLongArray("saved_poses", blockPoses);
        return tag;
    }

    @Override
    public void readNBT(Capability<ChunkSavedBlockPos> capability, ChunkSavedBlockPos instance, Direction side, INBT nbt) {
        CompoundNBT tag = (CompoundNBT) nbt;
        Set<BlockPos> poses = new HashSet<>();
        for(long pos:tag.getLongArray("saved_poses")){
            poses.add(BlockPos.fromLong(pos));
        }
        instance.setSavedBlockPoses(poses);
    }
}
