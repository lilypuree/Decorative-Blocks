package lilypuree.decorative_blocks;

import lilypuree.decorative_blocks.core.DBItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class Constants {
    public static final String MODID = "decorative_blocks";

    public static final CreativeModeTab ITEM_GROUP = new CreativeModeTab(13, MODID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(DBItems.BRAZIER);
        }
    };
}
