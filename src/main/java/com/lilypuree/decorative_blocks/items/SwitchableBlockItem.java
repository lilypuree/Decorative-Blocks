package com.lilypuree.decorative_blocks.items;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;

import net.minecraft.item.Item.Properties;

public class SwitchableBlockItem<T extends Property<U>, U extends Comparable<U>> extends BlockItem {
    private T switching;
    private int max;
    private String tagName;

    public SwitchableBlockItem(Block blockIn, Properties builder, T switching, String tag) {
        super(blockIn, builder);
        this.switching = switching;
        this.max = switching.getPossibleValues().size() - 1;
        this.tagName = tag;
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (playerIn.isShiftKeyDown()) {
            if (!worldIn.isClientSide()) {
                cycleValueTag(playerIn.getItemInHand(handIn));
            }
            return ActionResult.success(playerIn.getItemInHand(handIn));
        }
        return super.use(worldIn, playerIn, handIn);
    }

//    @Nullable
//    @Override
//    protected BlockState getStateForPlacement(BlockItemUseContext context) {
//        BlockState state = super.getStateForPlacement(context);
//        ItemStack stack = context.getItem();
//        if (state != null && state.hasProperty(switching)) {
//            state = state.with(switching, state.getBlock().getDefaultState().get(switching));
//            for (int i = 0; i < getValueTag(stack); i++) {
//                state.cycle(switching);
//            }
//        }
//        return state;
//    }

    public BlockState getSwitchedState(BlockState state, ItemStack stack) {
        if (state != null && state.hasProperty(switching)) {
            state = state.setValue(switching, state.getBlock().defaultBlockState().getValue(switching));
            for (int i = 0; i < getValueTag(stack); i++) {
                state = state.cycle(switching);
            }
        }
        return state;
    }

    private int getValueTag(ItemStack stack) {
        if (stack.hasTag()) {
            return stack.getTag().getInt(tagName);
        }
        return 0;
    }

    private ItemStack cycleValueTag(ItemStack stack) {
        CompoundNBT tag = stack.getOrCreateTag();
        int currentValue = tag.contains(tagName, 3) ? tag.getInt(tagName) : 0;
        if (currentValue == max) {
            tag.remove(tagName);
            if (tag.isEmpty()) {
                stack.setTag(null);
            } else {
                stack.setTag(tag);
            }
            return stack;
        }
        tag.putInt(tagName, currentValue + 1);
        stack.setTag(tag);
        return stack;
    }

    public String getTagName() {
        return tagName;
    }
}
