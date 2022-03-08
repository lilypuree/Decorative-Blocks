package lilypuree.decorative_blocks.core;

import lilypuree.decorative_blocks.Constants;
import lilypuree.decorative_blocks.blocks.types.IWoodType;
import lilypuree.decorative_blocks.blocks.types.VanillaWoodTypes;
import lilypuree.decorative_blocks.items.BlockstateCopyItem;
import lilypuree.decorative_blocks.items.SeatItem;
import lilypuree.decorative_blocks.items.SupportItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.Map;

public class DBItems {
    public static Item CHANDELIER;
    public static Item SOUL_CHANDELIER;
    public static Item BRAZIER;
    public static Item SOUL_BRAZIER;
    public static Item BAR_PANEL;
    public static Item LATTICE;
    public static Item CHAIN;
    public static Item STONE_PILLAR;
    public static Item ROCKY_DIRT;
    public static Item BLOCKSTATE_COPY_ITEM;

    public static Map<IWoodType, Item> BEAM_ITEMBLOCKS = new HashMap<>();
    public static Map<IWoodType, Item> SEAT_ITEMBLOCKS = new HashMap<>();
    public static Map<IWoodType, Item> SUPPORT_ITEMBLOCKS = new HashMap<>();
    public static Map<IWoodType, Item> PALISADE_ITEMBLOCKS = new HashMap<>();

    public static final Item.Properties modItemProperties = new Item.Properties().tab(Constants.ITEM_GROUP);

    public static void init() {
        CHANDELIER = new BlockItem(DBBlocks.CHANDELIER, modItemProperties);
        SOUL_CHANDELIER = new BlockItem(DBBlocks.SOUL_CHANDELIER, modItemProperties);
        BRAZIER = new BlockItem(DBBlocks.BRAZIER, modItemProperties);
        SOUL_BRAZIER = new BlockItem(DBBlocks.SOUL_BRAZIER, modItemProperties);
        BAR_PANEL = new BlockItem(DBBlocks.BAR_PANEL, modItemProperties);
        LATTICE = new BlockItem(DBBlocks.LATTICE, modItemProperties);
        CHAIN = new BlockItem(DBBlocks.CHAIN, modItemProperties);
        STONE_PILLAR = new BlockItem(DBBlocks.STONE_PILLAR, modItemProperties);
        ROCKY_DIRT = new BlockItem(DBBlocks.ROCKY_DIRT, modItemProperties);
        BLOCKSTATE_COPY_ITEM = new BlockstateCopyItem(new Item.Properties().stacksTo(1));

        for (IWoodType wood : VanillaWoodTypes.values()) {
            BEAM_ITEMBLOCKS.put(wood, new BlockItem(DBBlocks.BEAMS.get(wood), modItemProperties));
            SEAT_ITEMBLOCKS.put(wood, new SeatItem(DBBlocks.SEATS.get(wood), modItemProperties));
            SUPPORT_ITEMBLOCKS.put(wood, new SupportItem(DBBlocks.SUPPORTS.get(wood), modItemProperties));
            PALISADE_ITEMBLOCKS.put(wood, new BlockItem(DBBlocks.PALISADES.get(wood), modItemProperties));
        }
    }
}
