package lilypuree.decorative_blocks.core;

import com.google.common.collect.ImmutableMap;
import lilypuree.decorative_blocks.Constants;
import lilypuree.decorative_blocks.blocks.*;
import lilypuree.decorative_blocks.blocks.types.IWoodType;
import lilypuree.decorative_blocks.blocks.types.VanillaWoodTypes;
import lilypuree.decorative_blocks.blocks.types.WoodDecorativeBlockTypes;
import lilypuree.decorative_blocks.platform.Services;
import lilypuree.decorative_blocks.registration.BlockRegistryObject;
import lilypuree.decorative_blocks.registration.RegistrationProvider;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
<<<<<<< Updated upstream:Common/src/main/java/lilypuree/decorative_blocks/core/DBBlocks.java
import net.minecraft.world.level.block.LiquidBlock;
=======
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
>>>>>>> Stashed changes:Common/src/main/java/lilypuree/decorative_blocks/registration/DBBlocks.java
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.Supplier;

import static lilypuree.decorative_blocks.blocks.types.WoodDecorativeBlockTypes.*;

public class DBBlocks {
<<<<<<< Updated upstream:Common/src/main/java/lilypuree/decorative_blocks/core/DBBlocks.java
    private static final RegistrationProvider<Block> BLOCK_REGISTRY = RegistrationProvider.get(Registries.BLOCK, Constants.MOD_ID);
=======
    public static final BlockWrapper<BonfireBlock> BONFIRE;
    public static final BlockWrapper<ChandelierBlock> CHANDELIER;
    public static final BlockWrapper<BrazierBlock> BRAZIER;
    public static final BlockWrapper<ChandelierBlock> SOUL_CHANDELIER;
    public static final BlockWrapper<BrazierBlock> SOUL_BRAZIER;
    public static final BlockWrapper<BonfireBlock> SOUL_BONFIRE;
    public static final BlockWrapper<BarPanelBlock> BAR_PANEL;
    public static final BlockWrapper<LatticeBlock> LATTICE;
    public static final BlockWrapper<ChainBlock> CHAIN;
    public static final BlockWrapper<RotatedPillarBlock> ROPE_COIL;
    public static final BlockWrapper<PillarBlock> STONE_PILLAR;
    public static final BlockWrapper<PillarBlock> SMOOTH_STONE_PILLAR;
    public static final BlockWrapper<PillarBlock> SANDSTONE_PILLAR;
    public static final BlockWrapper<PillarBlock> RED_SANDSTONE_PILLAR;
    public static final BlockWrapper<PillarBlock> BLACKSTONE_PILLAR;
    public static final BlockWrapper<PillarBlock> BASALT_PILLAR;
    public static final BlockWrapper<PillarBlock> TUFF_PILLAR;
    public static final BlockWrapper<PillarBlock> MUD_PILLAR;
    public static final BlockWrapper<RockyDirtBlock> ROCKY_DIRT;
    public static final BlockWrapper<TablePotBlock> TABLE_POT;
    public static final BlockWrapper<StepLadderBlock> STEP_LADDER;
>>>>>>> Stashed changes:Common/src/main/java/lilypuree/decorative_blocks/registration/DBBlocks.java

    public static final BlockRegistryObject<Block> BONFIRE;
    public static final BlockRegistryObject<Block> CHANDELIER;
    public static final BlockRegistryObject<Block> BRAZIER;
    public static final BlockRegistryObject<Block> SOUL_CHANDELIER;
    public static final BlockRegistryObject<Block> SOUL_BRAZIER;
    public static final BlockRegistryObject<Block> SOUL_BONFIRE;
    public static final BlockRegistryObject<Block> BAR_PANEL;
    public static final BlockRegistryObject<Block> LATTICE;
    public static final BlockRegistryObject<Block> CHAIN;
    public static final BlockRegistryObject<Block> STONE_PILLAR;
    public static final BlockRegistryObject<Block> ROCKY_DIRT;
    public static final BlockRegistryObject<LiquidBlock> THATCH;

    public static final ImmutableMap<IWoodType, BlockRegistryObject<BeamBlock>> BEAMS;
    public static final ImmutableMap<IWoodType, BlockRegistryObject<PalisadeBlock>> PALISADES;
    public static final ImmutableMap<IWoodType, BlockRegistryObject<SupportBlock>> SUPPORTS;
    public static final ImmutableMap<IWoodType, BlockRegistryObject<SeatBlock>> SEATS;

    static {
        BlockBehaviour.Properties chainProperties = Block.Properties.of().mapColor(MapColor.METAL).strength(4.3F).sound(SoundType.METAL).noOcclusion();
<<<<<<< Updated upstream:Common/src/main/java/lilypuree/decorative_blocks/core/DBBlocks.java
        BlockBehaviour.Properties thatchProperties = Block.Properties.of().liquid().replaceable().noCollission().randomTicks().noLootTable()
                .mapColor(MapColor.COLOR_YELLOW).pushReaction(PushReaction.DESTROY).strength(100.0F);
=======
>>>>>>> Stashed changes:Common/src/main/java/lilypuree/decorative_blocks/registration/DBBlocks.java

        BlockBehaviour.Properties bonfire = BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0).mapColor(MapColor.FIRE).pushReaction(PushReaction.DESTROY).replaceable().noCollission().lightLevel(state -> 15).noLootTable();
        BlockBehaviour.Properties soul_bonfire = BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0).mapColor(MapColor.COLOR_CYAN).pushReaction(PushReaction.DESTROY).replaceable().noCollission().lightLevel(state -> 14).noLootTable();
        BlockBehaviour.Properties chandelier = BlockBehaviour.Properties.of().sound(SoundType.WOOD).strength(0.3f).pushReaction(PushReaction.DESTROY).replaceable().noCollission().noOcclusion().lightLevel(state -> 15);
        BlockBehaviour.Properties soul_chandelier = BlockBehaviour.Properties.of().sound(SoundType.WOOD).strength(0.3f).pushReaction(PushReaction.DESTROY).replaceable().noCollission().noOcclusion().lightLevel(state -> 11);
        BlockBehaviour.Properties brazier = BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(3.0f).mapColor(MapColor.METAL).noOcclusion().lightLevel(state -> state.getValue(BlockStateProperties.LIT) ? 15 : 0);
        BlockBehaviour.Properties soul_brazier = BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(3.0f).mapColor(MapColor.METAL).noOcclusion().lightLevel(state -> state.getValue(BlockStateProperties.LIT) ? 10 : 0);
        BlockBehaviour.Properties bar_banel = BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(5.0f).mapColor(MapColor.METAL).noOcclusion();
        BlockBehaviour.Properties lattice = BlockBehaviour.Properties.of().sound(SoundType.WOOD).strength(1.2f).mapColor(MapColor.WOOD).noOcclusion();
        BlockBehaviour.Properties table_pot = BlockBehaviour.Properties.of().sound(SoundType.WOOD).strength(1.5F).mapColor(MapColor.WOOD).noOcclusion();

        BONFIRE = registerBlock("bonfire", () -> new BonfireBlock(bonfire));
        CHANDELIER = registerBlock("chandelier", () -> new ChandelierBlock(chandelier, false));
        BRAZIER = registerBlock("brazier", () -> new BrazierBlock(brazier, false));
        SOUL_BONFIRE = registerBlock("soul_bonfire", () -> new BonfireBlock(soul_bonfire));
        SOUL_CHANDELIER = registerBlock("soul_chandelier", () -> new ChandelierBlock(soul_chandelier, true));
        SOUL_BRAZIER = registerBlock("soul_brazier", () -> new BrazierBlock(soul_brazier, true));
        BAR_PANEL = registerBlock("bar_panel", () -> new BarPanelBlock(bar_banel));
        LATTICE = registerBlock("lattice", () -> new LatticeBlock(lattice));
        CHAIN = registerBlock("chain", () -> new ChainBlock(chainProperties));
        ROPE_COIL = registerBlock("rope_coil", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.BROWN_WOOL)));
        STEP_LADDER = registerBlock("step_ladder", () -> new StepLadderBlock(lattice));
        TABLE_POT = registerBlock("table_pot", () -> new TablePotBlock(table_pot));
        ROCKY_DIRT = registerBlock("rocky_dirt", RockyDirtBlock::new);
        THATCH = registerBlock("thatch", () -> Services.PLATFORM.createThatchFluidBlock(Registration.STILL_THATCH, thatchProperties));

<<<<<<< Updated upstream:Common/src/main/java/lilypuree/decorative_blocks/core/DBBlocks.java
        ImmutableMap.Builder<IWoodType, BlockRegistryObject<BeamBlock>> beams = new ImmutableMap.Builder<>();
        ImmutableMap.Builder<IWoodType, BlockRegistryObject<PalisadeBlock>> palisades = new ImmutableMap.Builder<>();
        ImmutableMap.Builder<IWoodType, BlockRegistryObject<SupportBlock>> supports = new ImmutableMap.Builder<>();
        ImmutableMap.Builder<IWoodType, BlockRegistryObject<SeatBlock>> seats = new ImmutableMap.Builder<>();
        for (IWoodType woodType : VanillaWoodTypes.values()) {
            if (woodType != VanillaWoodTypes.BAMBOO)
                beams.put(woodType, registerBlock(DBNames.name(woodType, BEAM), () -> (BeamBlock) createDecorativeBlock(woodType, BEAM)));
            palisades.put(woodType, registerBlock(DBNames.name(woodType, PALISADE), () -> (PalisadeBlock) createDecorativeBlock(woodType, PALISADE)));
            supports.put(woodType, registerBlock(DBNames.name(woodType, SUPPORT), () -> (SupportBlock) createDecorativeBlock(woodType, SUPPORT)));
            seats.put(woodType, registerBlock(DBNames.name(woodType, SEAT), () -> (SeatBlock) createDecorativeBlock(woodType, SEAT)));
=======
        STONE_PILLAR = registerBlock("stone_pillar", () -> createPillarBlock(Blocks.STONE));
        SMOOTH_STONE_PILLAR = registerBlock("smooth_stone_pillar", () -> createPillarBlock(Blocks.SMOOTH_STONE));
        SANDSTONE_PILLAR = registerBlock("sandstone_pillar", () -> createPillarBlock(Blocks.SANDSTONE));
        RED_SANDSTONE_PILLAR = registerBlock("red_sandstone_pillar", () -> createPillarBlock(Blocks.RED_SANDSTONE));
        BLACKSTONE_PILLAR = registerBlock("blackstone_pillar", () -> createPillarBlock(Blocks.BLACKSTONE));
        BASALT_PILLAR = registerBlock("basalt_pillar", () -> createPillarBlock(Blocks.BASALT));
        TUFF_PILLAR = registerBlock("tuff_pillar", () -> createPillarBlock(Blocks.TUFF));
        MUD_PILLAR = registerBlock("mud_pillar", () -> createPillarBlock(Blocks.PACKED_MUD));


        ImmutableMap.Builder<WoodType, BlockWrapper<BeamBlock>> beams = new ImmutableMap.Builder<>();
        ImmutableMap.Builder<WoodType, BlockWrapper<PalisadeBlock>> palisades = new ImmutableMap.Builder<>();
        ImmutableMap.Builder<WoodType, BlockWrapper<SupportBlock>> supports = new ImmutableMap.Builder<>();
        ImmutableMap.Builder<WoodType, BlockWrapper<SeatBlock>> seats = new ImmutableMap.Builder<>();

        for (WoodType woodType : VanillaWoodTypes.VANILLA) {
            MapColor mapColor = VanillaWoodTypes.getPlanks(woodType).defaultMapColor();
            if (woodType != WoodType.BAMBOO)
                beams.put(woodType, registerBlock(DBNames.name(woodType, BEAM), () -> (BeamBlock) createDecorativeBlock(woodType, mapColor, BEAM)));
            palisades.put(woodType, registerBlock(DBNames.name(woodType, PALISADE), () -> (PalisadeBlock) createDecorativeBlock(woodType, mapColor, PALISADE)));
            supports.put(woodType, registerBlock(DBNames.name(woodType, SUPPORT), () -> (SupportBlock) createDecorativeBlock(woodType, mapColor, SUPPORT)));
            seats.put(woodType, registerBlock(DBNames.name(woodType, SEAT), () -> (SeatBlock) createDecorativeBlock(woodType, mapColor, SEAT)));
>>>>>>> Stashed changes:Common/src/main/java/lilypuree/decorative_blocks/registration/DBBlocks.java
        }

        BEAMS = beams.build();
        PALISADES = palisades.build();
        SUPPORTS = supports.build();
        SEATS = seats.build();

    }

    public static void init() {
    }

    public static Block createDecorativeBlock(IWoodType wood, WoodDecorativeBlockTypes woodDecorativeBlockType) {
        BlockBehaviour.Properties woodProperty = wood.getProperties().strength(1.2F);
        BlockBehaviour.Properties palisadeProperty = wood.getProperties().strength(2.0F, 4.0F);

        return switch (woodDecorativeBlockType) {
            case BEAM -> new BeamBlock(woodProperty, wood);
            case SEAT -> new SeatBlock(woodProperty, wood);
            case SUPPORT -> new SupportBlock(woodProperty, wood);
            case PALISADE -> new PalisadeBlock(palisadeProperty, wood);
        };
    }

<<<<<<< Updated upstream:Common/src/main/java/lilypuree/decorative_blocks/core/DBBlocks.java
    private static <T extends Block> BlockRegistryObject<T> registerBlock(String name, Supplier<T> blockSupplier) {
        return BlockRegistryObject.wrap(BLOCK_REGISTRY.register(name, blockSupplier));
=======
    public static PillarBlock createPillarBlock(BlockBehaviour parent) {
        return new PillarBlock(BlockBehaviour.Properties.copy(parent));
    }

    private static <T extends Block> BlockWrapper<T> registerBlock(String name, Supplier<T> blockSupplier) {
        return new BlockWrapper<>(Services.PLATFORM.register(BuiltInRegistries.BLOCK, name, blockSupplier));
>>>>>>> Stashed changes:Common/src/main/java/lilypuree/decorative_blocks/registration/DBBlocks.java
    }

}
