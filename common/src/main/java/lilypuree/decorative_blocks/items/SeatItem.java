package lilypuree.decorative_blocks.items;

import lilypuree.decorative_blocks.Constants;
import lilypuree.decorative_blocks.blocks.state.ModBlockProperties;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import java.util.List;

public class SeatItem extends SwitchableBlockItem {
    public static final ResourceLocation OVERRIDE_TAG = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "post");

    public SeatItem(Block blockIn, Properties builder) {
        super(blockIn, builder, ModBlockProperties.POST, false);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext ctx, List<Component> tooltip, TooltipFlag tooltipFlag) {
        if (Screen.hasShiftDown()) {
            tooltip.add(Component.translatable("wiki.decorative_blocks.seat"));
        }
        super.appendHoverText(stack, ctx, tooltip, tooltipFlag);
    }
}
