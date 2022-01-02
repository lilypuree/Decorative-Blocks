package lilypuree.decorative_blocks.core;

import com.google.common.collect.ImmutableMap;
import lilypuree.decorative_blocks.Constants;
import lilypuree.decorative_blocks.core.factory.ItemSuppliers;
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

import static lilypuree.decorative_blocks.core.factory.ItemSuppliers.modItemProperties;

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


    public static void init() {
        CHANDELIER = ItemSuppliers.CHANDELIER.get();
        SOUL_CHANDELIER = ItemSuppliers.SOUL_CHANDELIER.get();
        BRAZIER = ItemSuppliers.BRAZIER.get();
        SOUL_BRAZIER = ItemSuppliers.SOUL_BRAZIER.get();
        BAR_PANEL = ItemSuppliers.BAR_PANEL.get();
        LATTICE = ItemSuppliers.LATTICE.get();
        CHAIN = ItemSuppliers.CHAIN.get();
        STONE_PILLAR = ItemSuppliers.STONE_PILLAR.get();
        ROCKY_DIRT = ItemSuppliers.ROCKY_DIRT.get();
        BLOCKSTATE_COPY_ITEM = ItemSuppliers.BLOCKSTATE_COPY_ITEM.get();

        for (IWoodType wood : VanillaWoodTypes.values()) {
            BEAM_ITEMBLOCKS.put(wood, new BlockItem(DBBlocks.BEAMS.get(wood), modItemProperties));
            SEAT_ITEMBLOCKS.put(wood, new SeatItem(DBBlocks.SEATS.get(wood), modItemProperties));
            SUPPORT_ITEMBLOCKS.put(wood, new SupportItem(DBBlocks.SUPPORTS.get(wood), modItemProperties));
            PALISADE_ITEMBLOCKS.put(wood, new BlockItem(DBBlocks.PALISADES.get(wood), modItemProperties));
        }
    }
}
