package lilypuree.decorative_blocks.core.factory;

import lilypuree.decorative_blocks.Constants;
import lilypuree.decorative_blocks.core.DBBlocks;
import lilypuree.decorative_blocks.items.BlockstateCopyItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class ItemSuppliers {
    public static final Item.Properties modItemProperties = new Item.Properties().tab(Constants.ITEM_GROUP);
    public static final Item.Properties dummyProperty = new Item.Properties();
    public static Supplier<Item> CHANDELIER = () -> new BlockItem(DBBlocks.CHANDELIER, modItemProperties);
    public static Supplier<Item> SOUL_CHANDELIER = () -> new BlockItem(DBBlocks.SOUL_CHANDELIER, modItemProperties);
    public static Supplier<Item> BRAZIER = () -> new BlockItem(DBBlocks.BRAZIER, modItemProperties);
    public static Supplier<Item> SOUL_BRAZIER = () -> new BlockItem(DBBlocks.SOUL_BRAZIER, modItemProperties);

    public static Supplier<Item> BAR_PANEL = () -> new BlockItem(DBBlocks.BAR_PANEL, modItemProperties);
    public static Supplier<Item> LATTICE = () -> new BlockItem(DBBlocks.LATTICE, modItemProperties);
    public static Supplier<Item> CHAIN = () -> new BlockItem(DBBlocks.CHAIN, modItemProperties);
    public static Supplier<Item> STONE_PILLAR = () -> new BlockItem(DBBlocks.STONE_PILLAR, modItemProperties);
    public static Supplier<Item> ROCKY_DIRT = () -> new BlockItem(DBBlocks.ROCKY_DIRT, modItemProperties);
    public static Supplier<Item> BLOCKSTATE_COPY_ITEM = () -> new BlockstateCopyItem(new Item.Properties().stacksTo(1));

}
