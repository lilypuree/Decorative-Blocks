package lilypuree.decorative_blocks.core;

import com.google.common.collect.ImmutableMap;
import lilypuree.decorative_blocks.Constants;
import lilypuree.decorative_blocks.core.setup.ModSetup;
import lilypuree.decorative_blocks.datagen.types.IWoodType;
import lilypuree.decorative_blocks.datagen.types.VanillaWoodTypes;
import lilypuree.decorative_blocks.datagen.types.WoodDecorativeBlockTypes;
import lilypuree.decorative_blocks.items.BlockstateCopyItem;
import lilypuree.decorative_blocks.items.SeatItem;
import lilypuree.decorative_blocks.items.SupportItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;
import java.util.Map;

public class DBItems {
    public static final Item.Properties modItemProperties = new Item.Properties().tab(Constants.ITEM_GROUP);
    public static final Item.Properties dummyProperty = new Item.Properties();
    public static Item CHANDELIER = new BlockItem(DBBlocks.CHANDELIER, modItemProperties);
    public static Item SOUL_CHANDELIER = new BlockItem(DBBlocks.SOUL_CHANDELIER, modItemProperties);
    public static Item BRAZIER = new BlockItem(DBBlocks.BRAZIER, modItemProperties);
    public static Item SOUL_BRAZIER = new BlockItem(DBBlocks.SOUL_BRAZIER, modItemProperties);

    public static Item BAR_PANEL = new BlockItem(DBBlocks.BAR_PANEL, modItemProperties);
    public static Item LATTICE = new BlockItem(DBBlocks.LATTICE, modItemProperties);
    public static Item CHAIN= new BlockItem(DBBlocks.CHAIN, modItemProperties);
    public static Item STONE_PILLAR = new BlockItem(DBBlocks.STONE_PILLAR, modItemProperties);
    public static Item ROCKY_DIRT = new BlockItem(DBBlocks.ROCKY_DIRT, modItemProperties);
    public static Item BLOCKSTATE_COPY_ITEM = new BlockstateCopyItem(new Item.Properties().stacksTo(1));

//    public static ImmutableMap<String, Item> DECORATIVE_ITEMBLOCKS;

    public static Map<IWoodType, Item> BEAM_ITEMBLOCKS = new HashMap<>();
    public static Map<IWoodType, Item> SEAT_ITEMBLOCKS = new HashMap<>();
    public static Map<IWoodType, Item> SUPPORT_ITEMBLOCKS = new HashMap<>();
    public static Map<IWoodType, Item> PALISADE_ITEMBLOCKS = new HashMap<>();


    static {
        for (IWoodType wood : VanillaWoodTypes.values()) {
            BEAM_ITEMBLOCKS.put(wood, new BlockItem(DBBlocks.BEAMS.get(wood), modItemProperties));
            SEAT_ITEMBLOCKS.put(wood, new SeatItem(DBBlocks.SEATS.get(wood), modItemProperties));
            SUPPORT_ITEMBLOCKS.put(wood, new SupportItem(DBBlocks.SUPPORTS.get(wood), modItemProperties));
            PALISADE_ITEMBLOCKS.put(wood, new BlockItem(DBBlocks.PALISADES.get(wood), modItemProperties));
        }
    }
}
