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
        super(parent.world, parent.getPosX(), parent.getPosY(), parent.getPosZ(), parent.getItem());
        this.setMotion(parent.getMotion());
        this.setPickupDelay(40);
        this.setThrowerId(parent.getThrowerId());
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (source == DamageSource.ON_FIRE) {
            Block block = world.getBlockState(this.getPosition()).getBlock();
            if (bonfireMap.containsKey(block)) {
                if (!world.isRemote()) {
                    world.setBlockState(this.getPosition(), bonfireMap.get(block).getDefaultState());
                    world.playSound(null, this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.BLOCKS, 1.0f, 0.7f);
                }
                this.remove();
            }
        }
        return super.attackEntityFrom(source, amount);
    }


}
