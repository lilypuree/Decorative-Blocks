package com.lilypuree.decorative_blocks.entity;

import com.lilypuree.decorative_blocks.core.setup.Registration;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class DummyEntityForSitting extends Entity {

    public DummyEntityForSitting(World world){
        super(Registration.DUMMY_ENTITY_TYPE.get(), world);
    }

    public DummyEntityForSitting(EntityType<? extends DummyEntityForSitting> type, World world)
    {
        super(type, world);
    }

    public DummyEntityForSitting(World world, BlockPos pos)
    {
        super(Registration.DUMMY_ENTITY_TYPE.get(), world);
        setPosition(pos.getX() + 0.5D, pos.getY() + 0.25D, pos.getZ() + 0.5D);
        noClip = true;
    }


    @Override
    protected void registerData() {}

    @Override
    protected void readAdditional(CompoundNBT tag) {}

    @Override
    protected void writeAdditional(CompoundNBT tag) {}

    @Override
    public IPacket<?> createSpawnPacket()
    {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
