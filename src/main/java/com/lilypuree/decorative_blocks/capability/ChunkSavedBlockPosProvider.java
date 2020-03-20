package com.lilypuree.decorative_blocks.capability;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class ChunkSavedBlockPosProvider implements ICapabilitySerializable<INBT> {

    @CapabilityInject(ChunkSavedBlockPos.class)
    public static final Capability<ChunkSavedBlockPos> CHUNK_SAVED_BLOCK_POS_CAPABILITY = null;

    private LazyOptional<ChunkSavedBlockPos> instance = LazyOptional.of(CHUNK_SAVED_BLOCK_POS_CAPABILITY::getDefaultInstance);

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return cap == CHUNK_SAVED_BLOCK_POS_CAPABILITY ? instance.cast() : LazyOptional.empty();
    }

    @Override
    public INBT serializeNBT() {
        return CHUNK_SAVED_BLOCK_POS_CAPABILITY.getStorage().writeNBT(CHUNK_SAVED_BLOCK_POS_CAPABILITY, this.instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        CHUNK_SAVED_BLOCK_POS_CAPABILITY.getStorage().readNBT(CHUNK_SAVED_BLOCK_POS_CAPABILITY, this.instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), null, nbt);
    }
}
