package com.lilypuree.decorative_blocks.setup;

import com.google.common.collect.ImmutableMap;
import com.lilypuree.decorative_blocks.blocks.*;
import com.lilypuree.decorative_blocks.datagen.types.WoodTypes;
import com.lilypuree.decorative_blocks.entity.DummyEntityForSitting;
import com.lilypuree.decorative_blocks.items.BurnableBlockItem;
import net.minecraft.block.Block;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.lilypuree.decorative_blocks.DecorativeBlocks.MODID;

public class Registration {
    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = new DeferredRegister<>(ForgeRegistries.ENTITIES, MODID);


    public static void register() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(modEventBus);
        BLOCKS.register(modEventBus);
        ENTITIES.register(modEventBus);
    }

    public static final RegistryObject<BarPanelBlock> BAR_PANEL = BLOCKS.register("bar_panel", ()-> new BarPanelBlock(Block.Properties.create(Material.IRON, MaterialColor.BLACK).notSolid().hardnessAndResistance(5.0F).sound(SoundType.METAL)));
    public static final RegistryObject<ChainBlock> CHAIN = BLOCKS.register("chain", ()-> new ChainBlock(Block.Properties.create(Material.IRON, MaterialColor.BLACK).hardnessAndResistance(4.3F).sound(SoundType.METAL)));
    public static final RegistryObject<ChandelierBlock> CHANDELIER = BLOCKS.register("chandelier", ()->new ChandelierBlock(Block.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.3F).sound(SoundType.WOOD)));
    public static final RegistryObject<BrazierBlock> BRAZIER = BLOCKS.register("brazier", ()->new BrazierBlock(Block.Properties.create(Material.IRON).hardnessAndResistance(3.0F).sound(SoundType.METAL)));
    public static final RegistryObject<PillarBlock> STONE_PILLAR = BLOCKS.register("stone_pillar", ()->new PillarBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.5F, 6.5F)));
    public static final RegistryObject<Block> ROCKY_DIRT = BLOCKS.register("rocky_dirt", ()->new RockyDirtBlock());

    public static final RegistryObject<Item> BAR_PANEL_ITEM = ITEMS.register("bar_panel", () -> new BlockItem(BAR_PANEL.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));
    public static final RegistryObject<Item> CHAIN_ITEM = ITEMS.register("chain", () -> new BlockItem(CHAIN.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));
    public static final RegistryObject<Item> CHANDELIER_ITEM = ITEMS.register("chandelier", () -> new BurnableBlockItem(CHANDELIER.get(), new Item.Properties().group(ItemGroup.DECORATIONS), 1600));
    public static final RegistryObject<Item> BRAZIER_ITEM = ITEMS.register("brazier", () -> new BlockItem(BRAZIER.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));
    public static final RegistryObject<Item> STONE_PILLAR_ITEM = ITEMS.register("stone_pillar", () -> new BlockItem(STONE_PILLAR.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));
    public static final RegistryObject<Item> ROCKY_DIRT_ITEM = ITEMS.register("rocky_dirt", () -> new BlockItem(ROCKY_DIRT.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));

    public static final RegistryObject<EntityType<DummyEntityForSitting>> DUMMY_ENTITY_TYPE = ENTITIES.register("dummy", ()->EntityType.Builder.<DummyEntityForSitting>create(DummyEntityForSitting::new, EntityClassification.MISC)
    .setTrackingRange(256)
    .setUpdateInterval(20)
    .size(0.0001F, 0.0001F)
    .build(MODID + ":dummy"));

    public static final ImmutableMap<String, RegistryObject<BeamBlock>> BEAM_BLOCKS;
    public static final ImmutableMap<String, RegistryObject<PalisadeBlock>> PALISADE_BLOCKS;
    public static final ImmutableMap<String, RegistryObject<SeatBlock>> SEAT_BLOCKS;
    public static final ImmutableMap<String, RegistryObject<SupportBlock>> SUPPORT_BLOCKS;
    public static final ImmutableMap<String, RegistryObject<Item>> ITEMBLOCKS;

    static {
        ImmutableMap.Builder<String, RegistryObject<BeamBlock>> beamBlockBuilder = ImmutableMap.builder();
        ImmutableMap.Builder<String, RegistryObject<PalisadeBlock>> palisadeBlockBuilder = ImmutableMap.builder();
        ImmutableMap.Builder<String, RegistryObject<SeatBlock>> seatBlockBuilder = ImmutableMap.builder();
        ImmutableMap.Builder<String, RegistryObject<SupportBlock>> supportBlockBuilder = ImmutableMap.builder();
        ImmutableMap.Builder<String, RegistryObject<Item>> itemBuilder = ImmutableMap.builder();

        Block.Properties woodProperty = Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(1.2F).sound(SoundType.WOOD);
        Block.Properties palisadeProperty = Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F, 4.0F).sound(SoundType.WOOD);
        Item.Properties buildingBlockItemProperty = new Item.Properties().group(ItemGroup.DECORATIONS);

        for(WoodTypes wood : WoodTypes.values()){
            String beamName = wood + "_beam";
            String palisadeName = wood + "_palisade";
            String seatName = wood + "_seat";
            String supportName = wood + "_support";

            BeamBlock beamBlock = new BeamBlock(woodProperty);
            PalisadeBlock palisadeBlock = new PalisadeBlock(palisadeProperty);
            SeatBlock seatBlock = new SeatBlock(woodProperty);
            SupportBlock supportBlock = new SupportBlock(woodProperty);

            beamBlockBuilder.put(beamName, BLOCKS.register(beamName, ()-> beamBlock));
            palisadeBlockBuilder.put(palisadeName, BLOCKS.register(palisadeName, ()-> palisadeBlock));
            seatBlockBuilder.put(seatName, BLOCKS.register(seatName, ()-> seatBlock));
            supportBlockBuilder.put(supportName, BLOCKS.register(supportName, ()-> supportBlock));

            itemBuilder.put(beamName, ITEMS.register(beamName, ()->new BurnableBlockItem(beamBlock, buildingBlockItemProperty, 300)));
            itemBuilder.put(palisadeName, ITEMS.register(palisadeName, ()->new BurnableBlockItem(palisadeBlock, buildingBlockItemProperty, 300)));
            itemBuilder.put(seatName, ITEMS.register(seatName, ()->new BurnableBlockItem(seatBlock, buildingBlockItemProperty, 300)));
            itemBuilder.put(supportName, ITEMS.register(supportName, ()->new BurnableBlockItem(supportBlock, buildingBlockItemProperty, 300)));
        }

        BEAM_BLOCKS = beamBlockBuilder.build();
        PALISADE_BLOCKS = palisadeBlockBuilder.build();
        SEAT_BLOCKS = seatBlockBuilder.build();
        SUPPORT_BLOCKS = supportBlockBuilder.build();
        ITEMBLOCKS = itemBuilder.build();
    }

    public static RotatedPillarBlock getBeamBlock(WoodTypes wood){
        String name = wood + "_beam";
        return BEAM_BLOCKS.get(name).get();
    }

    public static Block getPalisadeBlock(WoodTypes wood){
        String name = wood + "_palisade";
        return PALISADE_BLOCKS.get(name).get();
    }

    public static SeatBlock getSeatBlock(WoodTypes wood){
        String name = wood + "_seat";
        return SEAT_BLOCKS.get(name).get();
    }

    public static SupportBlock getSupportBlock(WoodTypes wood){
        String name = wood + "_support";
        return SUPPORT_BLOCKS.get(name).get();
    }
}

