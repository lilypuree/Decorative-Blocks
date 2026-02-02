package lilypuree.decorative_blocks.items;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class SwitchableBlockItem extends BlockItem {
    private final BooleanProperty switching;
    private final boolean defaultState;

    public SwitchableBlockItem(Block blockIn, Properties builder, BooleanProperty switching, boolean defaultState) {
        super(blockIn, builder);
        this.switching = switching;
        this.defaultState = defaultState;
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
            state.setValue(switching, getValue(stack));
        }
        return state;
    }

    private boolean getValue(ItemStack stack) {
        BlockItemStateProperties properties = stack.get(DataComponents.BLOCK_STATE);
        if (properties == null) {
            return defaultState;
        }

        Boolean val = properties.get(switching);
        return val != null ? val : defaultState;
    }

    public void cycleValueTag(ItemStack stack) {
        BlockItemStateProperties component = stack.get(DataComponents.BLOCK_STATE);
        if (component == null) {
            component = BlockItemStateProperties.EMPTY;
        }

        Boolean val = component.get(switching);
        stack.set(DataComponents.BLOCK_STATE, component.with(switching, val != null ? !val : !defaultState));
    }

    public static boolean getValueForStack(ItemStack stack)
    {
        Item item = stack.getItem();
        return item instanceof SwitchableBlockItem && ((SwitchableBlockItem) item).getValue(stack);
    }
}
