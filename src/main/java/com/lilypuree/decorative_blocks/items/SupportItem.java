package com.lilypuree.decorative_blocks.items;

import com.lilypuree.decorative_blocks.DecorativeBlocks;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class SupportItem extends SwitchableBlockItem<BooleanProperty, Boolean> {
    public static final ResourceLocation OVERRIDE_TAG = new ResourceLocation(DecorativeBlocks.MODID, "up");

    public SupportItem(Block blockIn, Properties builder) {
        super(blockIn, builder, BlockStateProperties.UP, OVERRIDE_TAG.getPath());
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (Screen.hasShiftDown()) {
            tooltip.add(new TranslationTextComponent("wiki.decorative_blocks.support.condition1"));
            tooltip.add(new TranslationTextComponent("wiki.decorative_blocks.support.behavior1"));
            tooltip.add(new TranslationTextComponent(""));
            tooltip.add(new TranslationTextComponent("wiki.decorative_blocks.support.condition2"));
            tooltip.add(new TranslationTextComponent("wiki.decorative_blocks.support.behavior2"));
            tooltip.add(new TranslationTextComponent(""));
            tooltip.add(new TranslationTextComponent("wiki.decorative_blocks.support.condition3"));
            tooltip.add(new TranslationTextComponent("wiki.decorative_blocks.support.behavior3"));
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }
}
