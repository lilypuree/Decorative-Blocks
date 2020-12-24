package com.lilypuree.decorative_blocks.setup;

import com.google.common.collect.ImmutableMap;
import com.lilypuree.decorative_blocks.blocks.*;
import com.lilypuree.decorative_blocks.blocks.ChainBlock;
import com.lilypuree.decorative_blocks.datagen.types.IWoodType;
import com.lilypuree.decorative_blocks.datagen.types.ModWoodTypes;
import com.lilypuree.decorative_blocks.datagen.types.WoodDecorativeBlockTypes;
import com.lilypuree.decorative_blocks.entity.DummyEntityForSitting;
import com.lilypuree.decorative_blocks.fluid.ThatchFluid;
import com.lilypuree.decorative_blocks.fluid.ThatchFluidBlock;
import com.lilypuree.decorative_blocks.items.BurnableBlockItem;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.lilypuree.decorative_blocks.DecorativeBlocks.MODID;

public class Registration {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, MODID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, MODID);


    public static void register() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(modEventBus);
        BLOCKS.register(modEventBus);
        ENTITIES.register(modEventBus);
        FLUIDS.register(modEventBus);
    }

    public static final RegistryObject<BarPanelBlock> BAR_PANEL = BLOCKS.register("bar_panel", () -> new BarPanelBlock(Block.Properties.create(Material.IRON, MaterialColor.BLACK).hardnessAndResistance(5.0F).sound(SoundType.METAL).notSolid()));
    public static final RegistryObject<LatticeBlock> LATTICE = BLOCKS.register("lattice", () -> new LatticeBlock(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(1.2F).sound(SoundType.WOOD).notSolid()));
    public static final AbstractBlock.Properties chainProperties = Block.Properties.create(Material.IRON, MaterialColor.BLACK).hardnessAndResistance(4.3F).sound(SoundType.METAL).notSolid();
    public static final RegistryObject<ChainBlock> CHAIN = BLOCKS.register("chain", () -> new ChainBlock(chainProperties));
    public static final RegistryObject<ChandelierBlock> CHANDELIER = BLOCKS.register("chandelier", () -> new ChandelierBlock(Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.3F).sound(SoundType.WOOD).notSolid().setLightLevel(state -> 15), false));
    public static final RegistryObject<ChandelierBlock> SOUL_CHANDELIER = BLOCKS.register("soul_chandelier", () -> new ChandelierBlock(Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.3F).sound(SoundType.WOOD).notSolid().setLightLevel(state -> 11), true));
    public static final RegistryObject<BrazierBlock> BRAZIER = BLOCKS.register("brazier", () -> new BrazierBlock(Block.Properties.create(Material.IRON).hardnessAndResistance(3.0F).sound(SoundType.METAL).setLightLevel(state -> state.get(BlockStateProperties.LIT) ? 15 : 0).notSolid(), false));
    public static final RegistryObject<BrazierBlock> SOUL_BRAZIER = BLOCKS.register("soul_brazier", () -> new BrazierBlock(Block.Properties.create(Material.IRON).hardnessAndResistance(3.0F).sound(SoundType.METAL).setLightLevel(state -> state.get(BlockStateProperties.LIT) ? 10 : 0).notSolid(), true));
    public static final RegistryObject<PillarBlock> STONE_PILLAR = BLOCKS.register("stone_pillar", () -> new PillarBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.5F, 6.5F)));
    public static final RegistryObject<Block> ROCKY_DIRT = BLOCKS.register("rocky_dirt", () -> new RockyDirtBlock());
    public static final RegistryObject<BonfireBlock> BONFIRE = BLOCKS.register("bonfire", () -> new BonfireBlock(Block.Properties.create(Material.FIRE, MaterialColor.TNT).doesNotBlockMovement().hardnessAndResistance(0).sound(SoundType.CLOTH).setLightLevel(state -> 15).noDrops()));
    public static final RegistryObject<BonfireBlock> SOUL_BONFIRE = BLOCKS.register("soul_bonfire", () -> new BonfireBlock(Block.Properties.create(Material.FIRE, MaterialColor.CYAN).doesNotBlockMovement().hardnessAndResistance(0).sound(SoundType.CLOTH).setLightLevel(state -> 14).noDrops()));

    public static final Item.Properties modItemProperties = new Item.Properties().group(ModSetup.ITEM_GROUP);
    public static final Item.Properties dummyProperty = new Item.Properties();

    public static final RegistryObject<Item> BAR_PANEL_ITEM = ITEMS.register("bar_panel", () -> new BlockItem(BAR_PANEL.get(), modItemProperties));
    public static final RegistryObject<Item> LATTICE_ITEM = ITEMS.register("lattice", () -> new BlockItem(LATTICE.get(), modItemProperties));
    public static final RegistryObject<Item> CHAIN_ITEM = ITEMS.register("chain", () -> new BlockItem(CHAIN.get(), modItemProperties));
    public static final RegistryObject<Item> CHANDELIER_ITEM = ITEMS.register("chandelier", () -> new BurnableBlockItem(CHANDELIER.get(), modItemProperties, 1600));
    public static final RegistryObject<Item> SOUL_CHANDELIER_ITEM = ITEMS.register("soul_chandelier", () -> new BurnableBlockItem(SOUL_CHANDELIER.get(), modItemProperties, 1600));
    public static final RegistryObject<Item> BRAZIER_ITEM = ITEMS.register("brazier", () -> new BlockItem(BRAZIER.get(), modItemProperties));
    public static final RegistryObject<Item> SOUL_BRAZIER_ITEM = ITEMS.register("soul_brazier", () -> new BlockItem(SOUL_BRAZIER.get(), modItemProperties));
    public static final RegistryObject<Item> STONE_PILLAR_ITEM = ITEMS.register("stone_pillar", () -> new BlockItem(STONE_PILLAR.get(), modItemProperties));
    public static final RegistryObject<Item> ROCKY_DIRT_ITEM = ITEMS.register("rocky_dirt", () -> new BlockItem(ROCKY_DIRT.get(), modItemProperties));

    public static final Material THATCH_MATERIAL = (new Material.Builder(MaterialColor.YELLOW)).doesNotBlockMovement().notSolid().replaceable().liquid().build();
    private static final ResourceLocation thatchStillTexture = new ResourceLocation("decorative_blocks", "block/thatch_still");
    private static final ResourceLocation thatchFlowingTexture = new ResourceLocation("decorative_blocks", "block/thatch_flowing");
    public static final ThatchFluid.FluidReferenceHolder referenceHolder = new ThatchFluid.FluidReferenceHolder(() -> Blocks.HAY_BLOCK, thatchStillTexture, thatchFlowingTexture, 0xAC8D08);
    public static final RegistryObject<FlowingFluid> FLOWING_THATCH = FLUIDS.register("flowing_thatch", () -> new ThatchFluid.Flowing(referenceHolder));
    public static final RegistryObject<FlowingFluid> STILL_THATCH = FLUIDS.register("thatch", () -> new ThatchFluid.Source(referenceHolder));
    public static final RegistryObject<Block> THATCH = BLOCKS.register("thatch", () -> new ThatchFluidBlock(STILL_THATCH, Block.Properties.create(THATCH_MATERIAL).doesNotBlockMovement().tickRandomly().hardnessAndResistance(100.0F).noDrops()));

    public static final RegistryObject<EntityType<DummyEntityForSitting>> DUMMY_ENTITY_TYPE = ENTITIES.register("dummy", () -> EntityType.Builder.<DummyEntityForSitting>create(DummyEntityForSitting::new, EntityClassification.MISC)
            .setTrackingRange(256)
            .setUpdateInterval(20)
            .size(0.0001F, 0.0001F)
            .build(MODID + ":dummy"));


    public static final ImmutableMap<String, RegistryObject<Block>> DECORATIVE_BLOCKS;
    public static final ImmutableMap<String, RegistryObject<Item>> DECORATIVE_ITEMBLOCKS;

    static {
        ImmutableMap.Builder<String, RegistryObject<Block>> decorativeBlockBuilder = ImmutableMap.builder();
        ImmutableMap.Builder<String, RegistryObject<Item>> itemBuilder = ImmutableMap.builder();


        for (WoodDecorativeBlockTypes type : WoodDecorativeBlockTypes.values()) {
            for (IWoodType wood : ModWoodTypes.allWoodTypes()) {
                String name = wood + "_" + type;
                decorativeBlockBuilder.put(name, BLOCKS.register(name, () -> createDecorativeBlock(wood, type)));
            }
        }

        DECORATIVE_BLOCKS = decorativeBlockBuilder.build();

        for (WoodDecorativeBlockTypes type : WoodDecorativeBlockTypes.values()) {
            for (IWoodType wood : ModWoodTypes.allWoodTypes()) {
                String name = wood + "_" + type;

                itemBuilder.put(name, ITEMS.register(name, () ->
                {
                    if (wood.isFlammable()) {
                        return new BurnableBlockItem(DECORATIVE_BLOCKS.get(name).get(), wood.isAvailable() ? modItemProperties : dummyProperty, 300);
                    } else {
                        return new BlockItem(DECORATIVE_BLOCKS.get(name).get(), wood.isAvailable() ? modItemProperties : dummyProperty);
                    }
                }));
            }
        }

        DECORATIVE_ITEMBLOCKS = itemBuilder.build();

        referenceHolder.setFlowingFluid(FLOWING_THATCH::get);
        referenceHolder.setStillFluid(STILL_THATCH::get);
        referenceHolder.setFluidBlock(THATCH::get);
    }

    public static Block getWoodDecorativeBlock(IWoodType wood, WoodDecorativeBlockTypes decorativeBlockType) {
        String name = wood + "_" + decorativeBlockType;
        return DECORATIVE_BLOCKS.get(name).get();
    }

    public static RotatedPillarBlock getBeamBlock(IWoodType wood) {
        return (RotatedPillarBlock) getWoodDecorativeBlock(wood, WoodDecorativeBlockTypes.BEAM);
    }

    public static PalisadeBlock getPalisadeBlock(IWoodType wood) {
        return (PalisadeBlock) getWoodDecorativeBlock(wood, WoodDecorativeBlockTypes.PALISADE);
    }

    public static SeatBlock getSeatBlock(IWoodType wood) {
        return (SeatBlock) getWoodDecorativeBlock(wood, WoodDecorativeBlockTypes.SEAT);
    }

    public static SupportBlock getSupportBlock(IWoodType wood) {
        return (SupportBlock) getWoodDecorativeBlock(wood, WoodDecorativeBlockTypes.SUPPORT);
    }


    private static Block createDecorativeBlock(IWoodType wood, WoodDecorativeBlockTypes woodDecorativeBlockType) {
        Block.Properties woodProperty = AbstractBlock.Properties.create(wood.getMaterial(), wood.getMaterialColor()).hardnessAndResistance(1.2F).sound(wood.getSoundType());
        Block.Properties palisadeProperty = AbstractBlock.Properties.create(wood.getMaterial(), wood.getMaterialColor()).hardnessAndResistance(2.0F, 4.0F).sound(wood.getSoundType());

        switch (woodDecorativeBlockType) {
            default:
            case BEAM:
                return new BeamBlock(woodProperty, wood);
            case SEAT:
                return new SeatBlock(woodProperty, wood);
            case SUPPORT:
                return new SupportBlock(woodProperty, wood);
            case PALISADE:
                return new PalisadeBlock(palisadeProperty, wood);
        }
    }
}

