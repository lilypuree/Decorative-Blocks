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
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.Supplier;

import static lilypuree.decorative_blocks.blocks.types.WoodDecorativeBlockTypes.*;

public class DBBlocks {
    private static final RegistrationProvider<Block> BLOCK_REGISTRY = RegistrationProvider.get(Registries.BLOCK, Constants.MOD_ID);

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
        BlockBehaviour.Properties thatchProperties = Block.Properties.of().liquid().replaceable().noCollission().randomTicks().noLootTable()
                .mapColor(MapColor.COLOR_YELLOW).pushReaction(PushReaction.DESTROY).strength(100.0F);

        BlockBehaviour.Properties bonfire = BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0).mapColor(MapColor.FIRE).pushReaction(PushReaction.DESTROY).replaceable().noCollission().lightLevel(state -> 15).noLootTable();
        BlockBehaviour.Properties soul_bonfire = BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0).mapColor(MapColor.COLOR_CYAN).pushReaction(PushReaction.DESTROY).replaceable().noCollission().lightLevel(state -> 14).noLootTable();
        BlockBehaviour.Properties chandelier = BlockBehaviour.Properties.of().sound(SoundType.WOOD).strength(0.3f).pushReaction(PushReaction.DESTROY).replaceable().noCollission().noOcclusion().lightLevel(state -> 15);
        BlockBehaviour.Properties soul_chandelier = BlockBehaviour.Properties.of().sound(SoundType.WOOD).strength(0.3f).pushReaction(PushReaction.DESTROY).replaceable().noCollission().noOcclusion().lightLevel(state -> 11);
        BlockBehaviour.Properties brazier = BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(3.0f).mapColor(MapColor.METAL).noOcclusion().lightLevel(state -> state.getValue(BlockStateProperties.LIT) ? 15 : 0);
        BlockBehaviour.Properties soul_brazier = BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(3.0f).mapColor(MapColor.METAL).noOcclusion().lightLevel(state -> state.getValue(BlockStateProperties.LIT) ? 10 : 0);
        BlockBehaviour.Properties bar_banel = BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(5.0f).mapColor(MapColor.METAL).noOcclusion();
        BlockBehaviour.Properties lattice = BlockBehaviour.Properties.of().sound(SoundType.WOOD).strength(1.2f).mapColor(MapColor.WOOD).noOcclusion();
        BlockBehaviour.Properties pillar = BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(1.5F, 6.5F).mapColor(MapColor.STONE);

        BONFIRE = registerBlock("bonfire", () -> new BonfireBlock(bonfire));
        CHANDELIER = registerBlock("chandelier", () -> new ChandelierBlock(chandelier, false));
        BRAZIER = registerBlock("brazier", () -> new BrazierBlock(brazier, false));
        SOUL_BONFIRE = registerBlock("soul_bonfire", () -> new BonfireBlock(soul_bonfire));
        SOUL_CHANDELIER = registerBlock("soul_chandelier", () -> new ChandelierBlock(soul_chandelier, true));
        SOUL_BRAZIER = registerBlock("soul_brazier", () -> new BrazierBlock(soul_brazier, true));
        BAR_PANEL = registerBlock("bar_panel", () -> new BarPanelBlock(bar_banel));
        LATTICE = registerBlock("lattice", () -> new LatticeBlock(lattice));
        CHAIN = registerBlock("chain", () -> new ChainBlock(chainProperties));
        STONE_PILLAR = registerBlock("stone_pillar", () -> new PillarBlock(pillar));
        ROCKY_DIRT = registerBlock("rocky_dirt", RockyDirtBlock::new);
        THATCH = registerBlock("thatch", () -> Services.PLATFORM.createThatchFluidBlock(Registration.STILL_THATCH, thatchProperties));

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

    private static <T extends Block> BlockRegistryObject<T> registerBlock(String name, Supplier<T> blockSupplier) {
        return BlockRegistryObject.wrap(BLOCK_REGISTRY.register(name, blockSupplier));
    }

}
