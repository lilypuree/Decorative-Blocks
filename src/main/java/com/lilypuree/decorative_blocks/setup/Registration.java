package com.lilypuree.decorative_blocks.setup;

import com.google.common.collect.ImmutableMap;
import com.lilypuree.decorative_blocks.blocks.*;
import com.lilypuree.decorative_blocks.datagen.types.*;
import com.lilypuree.decorative_blocks.entity.DummyEntityForSitting;
import com.lilypuree.decorative_blocks.fluid.ThatchFluid;
import com.lilypuree.decorative_blocks.fluid.ThatchFluidBlock;
import com.lilypuree.decorative_blocks.items.BurnableBlockItem;
import net.minecraft.block.Block;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
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
    public static final DeferredRegister<Fluid> FLUIDS = new DeferredRegister<>(ForgeRegistries.FLUIDS, MODID);

    public static void register() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(modEventBus);
        BLOCKS.register(modEventBus);
        ENTITIES.register(modEventBus);
        FLUIDS.register(modEventBus);
    }

    public static final RegistryObject<BarPanelBlock> BAR_PANEL = BLOCKS.register("bar_panel", ()-> new BarPanelBlock(Block.Properties.create(Material.IRON, MaterialColor.BLACK).hardnessAndResistance(5.0F).sound(SoundType.METAL)));
    public static final RegistryObject<LatticeBlock> LATTICE = BLOCKS.register("lattice", ()-> new LatticeBlock(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(1.2F).sound(SoundType.WOOD)));
    public static final RegistryObject<ChainBlock> CHAIN = BLOCKS.register("chain", ()-> new ChainBlock(Block.Properties.create(Material.IRON, MaterialColor.BLACK).hardnessAndResistance(4.3F).sound(SoundType.METAL)));
    public static final RegistryObject<ChandelierBlock> CHANDELIER = BLOCKS.register("chandelier", ()->new ChandelierBlock(Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.3F).sound(SoundType.WOOD)));
    public static final RegistryObject<BrazierBlock> BRAZIER = BLOCKS.register("brazier", ()->new BrazierBlock(Block.Properties.create(Material.IRON).hardnessAndResistance(3.0F).sound(SoundType.METAL)));
    public static final RegistryObject<PillarBlock> STONE_PILLAR = BLOCKS.register("stone_pillar", ()->new PillarBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.5F, 6.5F)));
    public static final RegistryObject<Block> ROCKY_DIRT = BLOCKS.register("rocky_dirt", ()->new RockyDirtBlock());
    public static final RegistryObject<BonfireBlock> BONFIRE = BLOCKS.register("bonfire", ()->new BonfireBlock(Block.Properties.create(Material.FIRE, MaterialColor.TNT).doesNotBlockMovement().hardnessAndResistance(0).sound(SoundType.CLOTH).lightValue(15).noDrops()));

    public static final Item.Properties modItemProperties =  new Item.Properties().group(ModSetup.ITEM_GROUP);
    public static final RegistryObject<Item> BAR_PANEL_ITEM = ITEMS.register("bar_panel", () -> new BlockItem(BAR_PANEL.get(),modItemProperties));
    public static final RegistryObject<Item> LATTICE_ITEM = ITEMS.register("lattice", () -> new BlockItem(LATTICE.get(),modItemProperties));
    public static final RegistryObject<Item> CHAIN_ITEM = ITEMS.register("chain", () -> new BlockItem(CHAIN.get(), modItemProperties));
    public static final RegistryObject<Item> CHANDELIER_ITEM = ITEMS.register("chandelier", () -> new BurnableBlockItem(CHANDELIER.get(), modItemProperties, 1600));
    public static final RegistryObject<Item> BRAZIER_ITEM = ITEMS.register("brazier", () -> new BlockItem(BRAZIER.get(), modItemProperties));
    public static final RegistryObject<Item> STONE_PILLAR_ITEM = ITEMS.register("stone_pillar", () -> new BlockItem(STONE_PILLAR.get(), modItemProperties));
    public static final RegistryObject<Item> ROCKY_DIRT_ITEM = ITEMS.register("rocky_dirt", () -> new BlockItem(ROCKY_DIRT.get(), modItemProperties));


    public static final Material THTACH_MATERIAL = (new Material.Builder(MaterialColor.YELLOW)).doesNotBlockMovement().notSolid().replaceable().liquid().build();
    public static final RegistryObject<FlowingFluid> FLOWING_THATCH = FLUIDS.register("flowing_thatch", ThatchFluid.Flowing::new);
    public static final RegistryObject<FlowingFluid> STILL_THATCH = FLUIDS.register("thatch", ThatchFluid.Source::new);
    public static final RegistryObject<Block> THATCH = BLOCKS.register("thatch", ()-> new ThatchFluidBlock(STILL_THATCH, Block.Properties.create(THTACH_MATERIAL).doesNotBlockMovement().tickRandomly().hardnessAndResistance(100.0F).noDrops()));
//    public static final RegistryObject<Item> HAY_BUCKET = ITEMS.register("hay_bucket",() -> new BucketItem(STILL_HAY, (new Item.Properties()).containerItem(Items.BUCKET).maxStackSize(1).group(ModSetup.ITEM_GROUP)));

    public static final RegistryObject<EntityType<DummyEntityForSitting>> DUMMY_ENTITY_TYPE = ENTITIES.register("dummy", ()->EntityType.Builder.<DummyEntityForSitting>create(DummyEntityForSitting::new, EntityClassification.MISC)
    .setTrackingRange(256)
    .setUpdateInterval(20)
    .size(0.0001F, 0.0001F)
    .build(MODID + ":dummy"));



    public static final ImmutableMap<String, RegistryObject<Block>> DECORATIVE_BLOCKS;
    public static final ImmutableMap<String, RegistryObject<Item>> DECORATIVE_ITEMBLOCKS;

    static {
        ImmutableMap.Builder<String, RegistryObject<Block>> decorativeBlockBuilder = ImmutableMap.builder();
        ImmutableMap.Builder<String, RegistryObject<Item>> itemBuilder = ImmutableMap.builder();


        for (WoodDecorativeBlockTypes type : WoodDecorativeBlockTypes.values()){
            for (IWoodType wood : ModWoodTypes.allWoodTypes()){
                String name = wood + "_" + type;
                Block decorativeBlock = createDecorativeBlock(wood, type);
                decorativeBlockBuilder.put(name, BLOCKS.register(name, ()->decorativeBlock));
                itemBuilder.put(name, ITEMS.register(name, ()->new BurnableBlockItem(decorativeBlock, modItemProperties, 300)));
            }
        }

        DECORATIVE_BLOCKS = decorativeBlockBuilder.build();
        DECORATIVE_ITEMBLOCKS = itemBuilder.build();
    }

    public static Block getWoodDecorativeBlock(IWoodType wood, WoodDecorativeBlockTypes decorativeBlockType){
        String name = wood + "_" + decorativeBlockType;
        return DECORATIVE_BLOCKS.get(name).get();
    }

    public static RotatedPillarBlock getBeamBlock(IWoodType wood){
       return (RotatedPillarBlock)getWoodDecorativeBlock(wood, WoodDecorativeBlockTypes.BEAM);
    }

    public static PalisadeBlock getPalisadeBlock(IWoodType wood){
       return (PalisadeBlock)getWoodDecorativeBlock(wood, WoodDecorativeBlockTypes.PALISADE);
    }

    public static SeatBlock getSeatBlock(IWoodType wood){
        return (SeatBlock)getWoodDecorativeBlock(wood,WoodDecorativeBlockTypes.SEAT);
    }

    public static SupportBlock getSupportBlock(IWoodType wood){
       return (SupportBlock)getWoodDecorativeBlock(wood,WoodDecorativeBlockTypes.SUPPORT);
    }


    private static Block createDecorativeBlock(IWoodType wood, WoodDecorativeBlockTypes woodDecorativeBlockType){
        Block.Properties woodProperty = Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(1.2F).sound(SoundType.WOOD);
        Block.Properties palisadeProperty = Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F, 4.0F).sound(SoundType.WOOD);

        switch (woodDecorativeBlockType){
            default:
            case BEAM:
                return new BeamBlock(woodProperty);
            case SEAT:
                return new SeatBlock(woodProperty);
            case SUPPORT:
                return new SupportBlock(woodProperty);
            case PALISADE:
                return new PalisadeBlock(palisadeProperty);
        }
    }
}

