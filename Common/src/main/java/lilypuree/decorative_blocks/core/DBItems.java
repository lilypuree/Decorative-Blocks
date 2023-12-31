package lilypuree.decorative_blocks.core;

import com.google.common.collect.ImmutableMap;
import lilypuree.decorative_blocks.Constants;
import lilypuree.decorative_blocks.blocks.types.IWoodType;
import lilypuree.decorative_blocks.blocks.types.VanillaWoodTypes;
import lilypuree.decorative_blocks.items.BlockstateCopyItem;
import lilypuree.decorative_blocks.items.SeatItem;
import lilypuree.decorative_blocks.items.SupportItem;
import lilypuree.decorative_blocks.platform.Services;
import lilypuree.decorative_blocks.registration.BlockRegistryObject;
import lilypuree.decorative_blocks.registration.RegistrationProvider;
import lilypuree.decorative_blocks.registration.RegistryObject;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

import static lilypuree.decorative_blocks.blocks.types.WoodDecorativeBlockTypes.SEAT;
import static lilypuree.decorative_blocks.blocks.types.WoodDecorativeBlockTypes.SUPPORT;

public class DBItems {
    private static final RegistrationProvider<Item> ITEM_REGISTRY = RegistrationProvider.get(Registries.ITEM, Constants.MOD_ID);
    public static final RegistryObject<Item> CHANDELIER;
    public static final RegistryObject<Item> SOUL_CHANDELIER;
    public static final RegistryObject<Item> BRAZIER;
    public static final RegistryObject<Item> SOUL_BRAZIER;
    public static final RegistryObject<Item> BAR_PANEL;
    public static final RegistryObject<Item> LATTICE;
    public static final RegistryObject<Item> CHAIN;
    public static final RegistryObject<Item> STONE_PILLAR;
    public static final RegistryObject<Item> ROCKY_DIRT;
    public static final RegistryObject<Item> BLOCKSTATE_COPY_ITEM;

    public static final ImmutableMap<IWoodType, RegistryObject<Item>> BEAM_ITEMBLOCKS;
    public static final ImmutableMap<IWoodType, RegistryObject<Item>> SEAT_ITEMBLOCKS;
    public static final ImmutableMap<IWoodType, RegistryObject<Item>> SUPPORT_ITEMBLOCKS;
    public static final ImmutableMap<IWoodType, RegistryObject<Item>> PALISADE_ITEMBLOCKS;


    static {
        CHANDELIER = registerBlockItem(DBBlocks.CHANDELIER);
        SOUL_CHANDELIER = registerBlockItem(DBBlocks.SOUL_CHANDELIER);
        BRAZIER = registerBlockItem(DBBlocks.BRAZIER);
        SOUL_BRAZIER = registerBlockItem(DBBlocks.SOUL_BRAZIER);
        BAR_PANEL = registerBlockItem(DBBlocks.BAR_PANEL);
        LATTICE = registerBlockItem(DBBlocks.LATTICE);
        CHAIN = registerBlockItem(DBBlocks.CHAIN);
        STONE_PILLAR = registerBlockItem(DBBlocks.STONE_PILLAR);
        ROCKY_DIRT = registerBlockItem(DBBlocks.ROCKY_DIRT);
        BLOCKSTATE_COPY_ITEM = registerItem("blockstate_copy_item", () -> new BlockstateCopyItem(new Item.Properties().stacksTo(1)));

        ImmutableMap.Builder<IWoodType, RegistryObject<Item>> beams = new ImmutableMap.Builder<>();
        ImmutableMap.Builder<IWoodType, RegistryObject<Item>> palisades = new ImmutableMap.Builder<>();
        ImmutableMap.Builder<IWoodType, RegistryObject<Item>> supports = new ImmutableMap.Builder<>();
        ImmutableMap.Builder<IWoodType, RegistryObject<Item>> seats = new ImmutableMap.Builder<>();
        for (IWoodType woodType : VanillaWoodTypes.values()) {
            beams.put(woodType, registerBlockItem(DBBlocks.BEAMS.get(woodType)));
            seats.put(woodType, registerItem(DBNames.name(woodType, SEAT), () -> new SeatItem(DBBlocks.SEATS.get(woodType).get(), new Item.Properties())));
            supports.put(woodType, registerItem(DBNames.name(woodType, SUPPORT), () -> new SupportItem(DBBlocks.SUPPORTS.get(woodType).get(), new Item.Properties())));
            palisades.put(woodType, registerBlockItem(DBBlocks.PALISADES.get(woodType)));
        }
        BEAM_ITEMBLOCKS = beams.build();
        PALISADE_ITEMBLOCKS = palisades.build();
        SUPPORT_ITEMBLOCKS = supports.build();
        SEAT_ITEMBLOCKS = seats.build();
    }

    public static void init() {
    }


    private static RegistryObject<Item> registerItem(String name, Supplier<Item> itemSupplier) {
        return ITEM_REGISTRY.register(name, itemSupplier);
    }

    private static RegistryObject<Item> registerBlockItem(BlockRegistryObject<?> block) {
        return ITEM_REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
    }

}
