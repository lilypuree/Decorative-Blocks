package lilypuree.decorative_blocks.items;

import lilypuree.decorative_blocks.Constants;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import java.util.List;

public class SupportItem extends SwitchableBlockItem {
    public static final ResourceLocation OVERRIDE_TAG = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "up");

    public SupportItem(Block blockIn, Properties builder) {
        super(blockIn, builder, BlockStateProperties.UP, true);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext ctx, List<Component> tooltip, TooltipFlag tooltipFlag) {
        if (Screen.hasShiftDown()) {
            tooltip.add(Component.translatable("wiki.decorative_blocks.support.condition1"));
            tooltip.add(Component.translatable("wiki.decorative_blocks.support.behavior1"));
            tooltip.add(Component.literal(""));
            tooltip.add(Component.translatable("wiki.decorative_blocks.support.condition2"));
            tooltip.add(Component.translatable("wiki.decorative_blocks.support.behavior2"));
            tooltip.add(Component.literal(""));
            tooltip.add(Component.translatable("wiki.decorative_blocks.support.condition3"));
            tooltip.add(Component.translatable("wiki.decorative_blocks.support.behavior3"));
        }
        super.appendHoverText(stack, ctx, tooltip, tooltipFlag);
    }
}
