package com.lilypuree.decorative_blocks.entity;

import com.lilypuree.decorative_blocks.core.setup.Registration;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;

import java.util.HashMap;
import java.util.Map;

public class ItemEntityBonfireActivator extends ItemEntity {

    public static Map<Block, Block> bonfireMap;

    static {
        bonfireMap = new HashMap<>();
        bonfireMap.put(Blocks.FIRE, Registration.BONFIRE.get());
        bonfireMap.put(Blocks.SOUL_FIRE, Registration.SOUL_BONFIRE.get());
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
                    level.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.FIRECHARGE_USE, SoundCategory.BLOCKS, 1.0f, 0.7f);
                }
                this.remove();
            }
        }
        return super.hurt(source, amount);
    }


}
