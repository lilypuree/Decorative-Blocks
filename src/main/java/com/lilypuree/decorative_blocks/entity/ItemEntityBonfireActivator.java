package com.lilypuree.decorative_blocks.entity;

import com.lilypuree.decorative_blocks.setup.Registration;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;

public class ItemEntityBonfireActivator extends ItemEntity {
    public ItemEntityBonfireActivator(ItemEntity parent) {
        super(parent.world, parent.getPosX(), parent.getPosY(), parent.getPosZ(), parent.getItem());
        this.setMotion(parent.getMotion());
        this.setPickupDelay(40);
        this.setThrowerId(parent.getThrowerId());
    }

    @Override
    protected void dealFireDamage(int amount) {

        Block block = world.getBlockState(this.getPosition()).getBlock();
        if (block == Blocks.FIRE) {
            if (!world.isRemote()) {
                world.setBlockState(this.getPosition(), Registration.BONFIRE.get().getDefaultState());
                world.playSound(null, this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.BLOCKS, 1.0f, 0.7f);
            }
            this.remove();
        }

        super.dealFireDamage(amount);
    }
}
