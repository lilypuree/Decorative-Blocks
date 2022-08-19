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
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import java.util.function.Supplier;

import static lilypuree.decorative_blocks.blocks.types.WoodDecorativeBlockTypes.*;

public class DBBlocks {
    private static final RegistrationProvider<Block> BLOCK_REGISTRY = RegistrationProvider.get(Registry.BLOCK, Constants.MODID);

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
        BlockBehaviour.Properties chainProperties = Block.Properties.of(Material.METAL, MaterialColor.COLOR_BLACK).strength(4.3F).sound(SoundType.METAL).noOcclusion();
        Material thatchMaterial = (new Material.Builder(MaterialColor.COLOR_YELLOW)).noCollider().nonSolid().replaceable().liquid().build();
        BlockBehaviour.Properties thatchProperties = Block.Properties.of(thatchMaterial).noCollission().randomTicks().strength(100.0F).noLootTable();

        BONFIRE = registerBlock("bonfire", () -> new BonfireBlock(Block.Properties.of(Material.FIRE, MaterialColor.FIRE).noCollission().strength(0).sound(SoundType.WOOL).lightLevel(state -> 15).noLootTable()));
        CHANDELIER = registerBlock("chandelier", () -> new ChandelierBlock(Block.Properties.of(Material.DECORATION).strength(0.3F).sound(SoundType.WOOD).noOcclusion().lightLevel(state -> 15), false));
        BRAZIER = registerBlock("brazier", () -> new BrazierBlock(Block.Properties.of(Material.METAL).strength(3.0F).sound(SoundType.METAL).lightLevel(state -> state.getValue(BlockStateProperties.LIT) ? 15 : 0).noOcclusion(), false));
        SOUL_BONFIRE = registerBlock("soul_bonfire", () -> new BonfireBlock(Block.Properties.of(Material.FIRE, MaterialColor.COLOR_CYAN).noCollission().strength(0).sound(SoundType.WOOL).lightLevel(state -> 14).noLootTable()));
        SOUL_CHANDELIER = registerBlock("soul_chandelier", () -> new ChandelierBlock(Block.Properties.of(Material.DECORATION).strength(0.3F).sound(SoundType.WOOD).noOcclusion().lightLevel(state -> 11), true));
        SOUL_BRAZIER = registerBlock("soul_brazier", () -> new BrazierBlock(Block.Properties.of(Material.METAL).strength(3.0F).sound(SoundType.METAL).lightLevel(state -> state.getValue(BlockStateProperties.LIT) ? 10 : 0).noOcclusion(), true));
        BAR_PANEL = registerBlock("bar_panel", () -> new BarPanelBlock(Block.Properties.of(Material.METAL, MaterialColor.COLOR_BLACK).strength(5.0F).sound(SoundType.METAL).noOcclusion()));
        LATTICE = registerBlock("lattice", () -> new LatticeBlock(Block.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(1.2F).sound(SoundType.WOOD).noOcclusion()));
        CHAIN = registerBlock("chain", () -> new ChainBlock(chainProperties));
        STONE_PILLAR = registerBlock("stone_pillar", () -> new PillarBlock(Block.Properties.of(Material.STONE).strength(1.5F, 6.5F)));
        ROCKY_DIRT = registerBlock("rocky_dirt", RockyDirtBlock::new);
        THATCH = registerBlock("thatch", () -> Services.PLATFORM.createThatchFluidBlock(Registration.STILL_THATCH, thatchProperties));

        ImmutableMap.Builder<IWoodType, BlockRegistryObject<BeamBlock>> beams = new ImmutableMap.Builder<>();
        ImmutableMap.Builder<IWoodType, BlockRegistryObject<PalisadeBlock>> palisades = new ImmutableMap.Builder<>();
        ImmutableMap.Builder<IWoodType, BlockRegistryObject<SupportBlock>> supports = new ImmutableMap.Builder<>();
        ImmutableMap.Builder<IWoodType, BlockRegistryObject<SeatBlock>> seats = new ImmutableMap.Builder<>();
        for (IWoodType woodType : VanillaWoodTypes.values()) {
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
        Block.Properties woodProperty = BlockBehaviour.Properties.of(wood.getMaterial(), wood.getMaterialColor()).strength(1.2F).sound(wood.getSoundType());
        Block.Properties palisadeProperty = BlockBehaviour.Properties.of(wood.getMaterial(), wood.getMaterialColor()).strength(2.0F, 4.0F).sound(wood.getSoundType());

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
