package lilypuree.decorative_blocks.entity;

import lilypuree.decorative_blocks.core.DBBlocks;
import lilypuree.decorative_blocks.core.Registration;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.HashMap;
import java.util.Map;

public class ItemEntityBonfireActivator extends ItemEntity {

    public static Map<Block, Block> bonfireMap;

    static {
        bonfireMap = new HashMap<>();
        bonfireMap.put(Blocks.FIRE, DBBlocks.BONFIRE);
        bonfireMap.put(Blocks.SOUL_FIRE, DBBlocks.SOUL_BONFIRE);
    }

    public ItemEntityBonfireActivator(ItemEntity parent) {
        super(parent.level, parent.getX(), parent.getY(), parent.getZ(), parent.getItem());
        this.setDeltaMovement(parent.getDeltaMovement());
        this.setPickUpDelay(40);
        this.setThrower(parent.getThrower());
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source == DamageSource.ON_FIRE) {
            Block block = level.getBlockState(this.blockPosition()).getBlock();
            if (bonfireMap.containsKey(block)) {
                if (!level.isClientSide()) {
                    level.setBlockAndUpdate(this.blockPosition(), bonfireMap.get(block).defaultBlockState());
                    level.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 1.0f, 0.7f);
                }
                this.remove(RemovalReason.DISCARDED);
            }
        }
        return super.hurt(source, amount);
    }


}
