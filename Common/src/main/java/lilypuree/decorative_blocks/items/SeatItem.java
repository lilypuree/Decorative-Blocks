package lilypuree.decorative_blocks.items;

import lilypuree.decorative_blocks.Constants;
import lilypuree.decorative_blocks.state.ModBlockProperties;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import javax.annotation.Nullable;
import java.util.List;

public class SeatItem extends SwitchableBlockItem<BooleanProperty, Boolean> {
    public static final ResourceLocation OVERRIDE_TAG = new ResourceLocation(Constants.MODID, "post");

    public SeatItem(Block blockIn, Properties builder) {
        super(blockIn, builder, ModBlockProperties.POST, OVERRIDE_TAG.getPath());
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        if (Screen.hasShiftDown()) {
            tooltip.add(new TranslatableComponent("wiki.decorative_blocks.seat"));
        }
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}
