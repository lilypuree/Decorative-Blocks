package lilypuree.decorative_blocks.items;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

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
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        if (playerIn.isShiftKeyDown()) {
            if (!worldIn.isClientSide()) {
                cycleValueTag(playerIn.getItemInHand(handIn));
            }
            return InteractionResultHolder.success(playerIn.getItemInHand(handIn));
        }
        return super.use(worldIn, playerIn, handIn);
    }


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
        CompoundTag tag = stack.getOrCreateTag();
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
