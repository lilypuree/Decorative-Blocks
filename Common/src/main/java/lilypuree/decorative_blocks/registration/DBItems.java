package lilypuree.decorative_blocks.registration;

import com.google.common.collect.ImmutableMap;
import lilypuree.decorative_blocks.blocks.types.VanillaWoodTypes;
import lilypuree.decorative_blocks.items.BlockstateCopyItem;
import lilypuree.decorative_blocks.items.SeatItem;
import lilypuree.decorative_blocks.items.StepLadderItem;
import lilypuree.decorative_blocks.items.SupportItem;
import lilypuree.decorative_blocks.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.properties.WoodType;

import java.util.function.Supplier;

import static lilypuree.decorative_blocks.blocks.types.WoodDecorativeBlockTypes.*;

public class DBItems {
    public static final ItemWrapper<BlockItem> CHANDELIER;
    public static final ItemWrapper<BlockItem> SOUL_CHANDELIER;
    public static final ItemWrapper<BlockItem> BRAZIER;
    public static final ItemWrapper<BlockItem> SOUL_BRAZIER;
    public static final ItemWrapper<BlockItem> BAR_PANEL;
    public static final ItemWrapper<BlockItem> LATTICE;
    public static final ItemWrapper<BlockItem> CHAIN;
    public static final ItemWrapper<BlockItem> ROCKY_DIRT;
    public static final ItemWrapper<BlockItem> TABLE_POT;
    public static final ItemWrapper<BlockItem> ROPE_COIL;
    public static final ItemWrapper<BlockItem> STEP_LADDER;
    public static final ItemWrapper<Item> BLOCKSTATE_COPY_ITEM;

    public static final ItemWrapper<BlockItem> STONE_PILLAR;
    public static final ItemWrapper<BlockItem> SMOOTH_STONE_PILLAR;
    public static final ItemWrapper<BlockItem> SANDSTONE_PILLAR;
    public static final ItemWrapper<BlockItem> RED_SANDSTONE_PILLAR;
    public static final ItemWrapper<BlockItem> BLACKSTONE_PILLAR;
    public static final ItemWrapper<BlockItem> BASALT_PILLAR;
    public static final ItemWrapper<BlockItem> TUFF_PILLAR;
    public static final ItemWrapper<BlockItem> MUD_PILLAR;


    public static final ImmutableMap<WoodType, ItemWrapper<BlockItem>> BEAM_ITEMBLOCKS;
    public static final ImmutableMap<WoodType, ItemWrapper<SeatItem>> SEAT_ITEMBLOCKS;
    public static final ImmutableMap<WoodType, ItemWrapper<SupportItem>> SUPPORT_ITEMBLOCKS;
    public static final ImmutableMap<WoodType, ItemWrapper<BlockItem>> PALISADE_ITEMBLOCKS;


    static {
        CHANDELIER = registerBlockItem("chandelier", DBBlocks.CHANDELIER);
        SOUL_CHANDELIER = registerBlockItem("soul_chandelier", DBBlocks.SOUL_CHANDELIER);
        BRAZIER = registerBlockItem("brazier", DBBlocks.BRAZIER);
        SOUL_BRAZIER = registerBlockItem("soul_brazier", DBBlocks.SOUL_BRAZIER);
        BAR_PANEL = registerBlockItem("bar_panel", DBBlocks.BAR_PANEL);
        LATTICE = registerBlockItem("lattice", DBBlocks.LATTICE);
        CHAIN = registerBlockItem("chain", DBBlocks.CHAIN);
        ROCKY_DIRT = registerBlockItem("rocky_dirt", DBBlocks.ROCKY_DIRT);
        TABLE_POT = registerBlockItem("table_pot", DBBlocks.TABLE_POT);
        ROPE_COIL = registerBlockItem("rope_coil", DBBlocks.ROPE_COIL);
        STEP_LADDER = registerItem("step_ladder", () -> new StepLadderItem(DBBlocks.STEP_LADDER.get(), new Item.Properties()));
        BLOCKSTATE_COPY_ITEM = registerItem("blockstate_copy_item", () -> new BlockstateCopyItem(new Item.Properties().stacksTo(1)));

        STONE_PILLAR = registerBlockItem("stone_pillar", DBBlocks.STONE_PILLAR);
        SMOOTH_STONE_PILLAR = registerBlockItem("smooth_stone_pillar", DBBlocks.SMOOTH_STONE_PILLAR);
        SANDSTONE_PILLAR = registerBlockItem("sandstone_pillar", DBBlocks.SANDSTONE_PILLAR);
        RED_SANDSTONE_PILLAR = registerBlockItem("red_sandstone_pillar", DBBlocks.RED_SANDSTONE_PILLAR);
        BLACKSTONE_PILLAR = registerBlockItem("blackstone_pillar", DBBlocks.BLACKSTONE_PILLAR);
        BASALT_PILLAR = registerBlockItem("basalt_pillar", DBBlocks.BASALT_PILLAR);
        TUFF_PILLAR = registerBlockItem("tuff_pillar", DBBlocks.TUFF_PILLAR);
        MUD_PILLAR = registerBlockItem("mud_pillar", DBBlocks.MUD_PILLAR);


        ImmutableMap.Builder<WoodType, ItemWrapper<BlockItem>> beams = new ImmutableMap.Builder<>();
        ImmutableMap.Builder<WoodType, ItemWrapper<BlockItem>> palisades = new ImmutableMap.Builder<>();
        ImmutableMap.Builder<WoodType, ItemWrapper<SupportItem>> supports = new ImmutableMap.Builder<>();
        ImmutableMap.Builder<WoodType, ItemWrapper<SeatItem>> seats = new ImmutableMap.Builder<>();
        for (WoodType woodType : VanillaWoodTypes.VANILLA) {
            if (woodType != WoodType.BAMBOO)
                beams.put(woodType, registerBlockItem(DBNames.name(woodType, BEAM), DBBlocks.BEAMS.get(woodType)));
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
        return registerItem(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }
}
