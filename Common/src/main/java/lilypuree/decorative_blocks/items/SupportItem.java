package lilypuree.decorative_blocks.items;

import lilypuree.decorative_blocks.Constants;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import java.util.List;

public class SupportItem extends SwitchableBlockItem<BooleanProperty, Boolean> {
    public static final ResourceLocation OVERRIDE_TAG = new ResourceLocation(Constants.MODID, "up");

    public SupportItem(Block blockIn, Properties builder) {
        super(blockIn, builder, BlockStateProperties.UP, OVERRIDE_TAG.getPath());
    }

    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        if (Screen.hasShiftDown()) {
            tooltip.add(new TranslatableComponent("wiki.decorative_blocks.support.condition1"));
            tooltip.add(new TranslatableComponent("wiki.decorative_blocks.support.behavior1"));
            tooltip.add(new TranslatableComponent(""));
            tooltip.add(new TranslatableComponent("wiki.decorative_blocks.support.condition2"));
            tooltip.add(new TranslatableComponent("wiki.decorative_blocks.support.behavior2"));
            tooltip.add(new TranslatableComponent(""));
            tooltip.add(new TranslatableComponent("wiki.decorative_blocks.support.condition3"));
            tooltip.add(new TranslatableComponent("wiki.decorative_blocks.support.behavior3"));
        }
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}
