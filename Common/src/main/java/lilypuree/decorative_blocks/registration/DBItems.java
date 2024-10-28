package lilypuree.decorative_blocks.registration;

import com.google.common.collect.ImmutableMap;
import lilypuree.decorative_blocks.blocks.types.IWoodType;
import lilypuree.decorative_blocks.blocks.types.VanillaWoodTypes;
import lilypuree.decorative_blocks.items.BlockstateCopyItem;
import lilypuree.decorative_blocks.items.SeatItem;
import lilypuree.decorative_blocks.items.SupportItem;
import lilypuree.decorative_blocks.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

import static lilypuree.decorative_blocks.blocks.types.WoodDecorativeBlockTypes.BEAM;
import static lilypuree.decorative_blocks.blocks.types.WoodDecorativeBlockTypes.PALISADE;
import static lilypuree.decorative_blocks.blocks.types.WoodDecorativeBlockTypes.SEAT;
import static lilypuree.decorative_blocks.blocks.types.WoodDecorativeBlockTypes.SUPPORT;

public class DBItems {
    public static final ItemWrapper<BlockItem> CHANDELIER;
    public static final ItemWrapper<BlockItem> SOUL_CHANDELIER;
    public static final ItemWrapper<BlockItem> BRAZIER;
    public static final ItemWrapper<BlockItem> SOUL_BRAZIER;
    public static final ItemWrapper<BlockItem> BAR_PANEL;
    public static final ItemWrapper<BlockItem> LATTICE;
    public static final ItemWrapper<BlockItem> CHAIN;
    public static final ItemWrapper<BlockItem> STONE_PILLAR;
    public static final ItemWrapper<BlockItem> ROCKY_DIRT;
    public static final ItemWrapper<Item> BLOCKSTATE_COPY_ITEM;

    public static final ImmutableMap<IWoodType, ItemWrapper<BlockItem>> BEAM_ITEMBLOCKS;
    public static final ImmutableMap<IWoodType, ItemWrapper<SeatItem>> SEAT_ITEMBLOCKS;
    public static final ImmutableMap<IWoodType, ItemWrapper<SupportItem>> SUPPORT_ITEMBLOCKS;
    public static final ImmutableMap<IWoodType, ItemWrapper<BlockItem>> PALISADE_ITEMBLOCKS;


    static {
        CHANDELIER = registerBlockItem("chandelier", DBBlocks.CHANDELIER);
        SOUL_CHANDELIER = registerBlockItem("soul_chandelier", DBBlocks.SOUL_CHANDELIER);
        BRAZIER = registerBlockItem("brazier", DBBlocks.BRAZIER);
        SOUL_BRAZIER = registerBlockItem("soul_brazier", DBBlocks.SOUL_BRAZIER);
        BAR_PANEL = registerBlockItem("bar_panel", DBBlocks.BAR_PANEL);
        LATTICE = registerBlockItem("lattice", DBBlocks.LATTICE);
        CHAIN = registerBlockItem("chain", DBBlocks.CHAIN);
        STONE_PILLAR = registerBlockItem("stone_pillar", DBBlocks.STONE_PILLAR);
        ROCKY_DIRT = registerBlockItem("rocky_dirt", DBBlocks.ROCKY_DIRT);
        BLOCKSTATE_COPY_ITEM = registerItem("blockstate_copy_item", () -> new BlockstateCopyItem(new Item.Properties().stacksTo(1)));

        ImmutableMap.Builder<IWoodType, ItemWrapper<BlockItem>> beams = new ImmutableMap.Builder<>();
        ImmutableMap.Builder<IWoodType, ItemWrapper<BlockItem>> palisades = new ImmutableMap.Builder<>();
        ImmutableMap.Builder<IWoodType, ItemWrapper<SupportItem>> supports = new ImmutableMap.Builder<>();
        ImmutableMap.Builder<IWoodType, ItemWrapper<SeatItem>> seats = new ImmutableMap.Builder<>();
        for (IWoodType woodType : VanillaWoodTypes.values()) {
            if (woodType != VanillaWoodTypes.BAMBOO)
                beams.put(woodType, registerBlockItem(DBNames.name(woodType, BEAM),DBBlocks.BEAMS.get(woodType)));
            seats.put(woodType, registerItem(DBNames.name(woodType, SEAT), () -> new SeatItem(DBBlocks.SEATS.get(woodType).get(), new Item.Properties())));
            supports.put(woodType, registerItem(DBNames.name(woodType, SUPPORT), () -> new SupportItem(DBBlocks.SUPPORTS.get(woodType).get(), new Item.Properties())));
            palisades.put(woodType, registerBlockItem(DBNames.name(woodType, PALISADE), DBBlocks.PALISADES.get(woodType)));
        }
        BEAM_ITEMBLOCKS = beams.build();
        PALISADE_ITEMBLOCKS = palisades.build();
        SUPPORT_ITEMBLOCKS = supports.build();
        SEAT_ITEMBLOCKS = seats.build();
    }

    public static void init() {
    }


    private static <T extends Item> ItemWrapper<T> registerItem(String name, Supplier<T> itemSupplier) {
        return new ItemWrapper<>(Services.PLATFORM.register(BuiltInRegistries.ITEM, name, itemSupplier));
    }

    private static ItemWrapper<BlockItem> registerBlockItem(String name, BlockWrapper<?> block) {
        return new ItemWrapper<>(Services.PLATFORM.register(BuiltInRegistries.ITEM, name, () -> new BlockItem(block.get(), new Item.Properties())));
    }

}
