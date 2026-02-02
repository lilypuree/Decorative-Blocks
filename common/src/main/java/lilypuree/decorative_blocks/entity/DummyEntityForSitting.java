package lilypuree.decorative_blocks.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class DummyEntityForSitting extends Entity {
    public DummyEntityForSitting(EntityType<? extends DummyEntityForSitting> type, Level world) {
        super(type, world);
        noPhysics = true;
    }

    public void setSeatPos(BlockPos pos) {
        setPos(pos.getX() + 0.5D, pos.getY() + 0.25D, pos.getZ() + 0.5D);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
    }

    @Override
    protected void removePassenger(Entity entity) {
        super.removePassenger(entity);
        if (!this.isRemoved() && !this.level().isClientSide()) {
            entity.absMoveTo(entity.getX(), entity.getY(), entity.getZ(), entity.yRotO, entity.xRotO);
            this.discard();
        }
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket(ServerEntity entity) {
        return new ClientboundAddEntityPacket(this, entity);
    }
}
