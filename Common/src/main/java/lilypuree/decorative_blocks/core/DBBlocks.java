package lilypuree.decorative_blocks.core;

import lilypuree.decorative_blocks.blocks.*;
import lilypuree.decorative_blocks.blocks.types.IWoodType;
import lilypuree.decorative_blocks.blocks.types.VanillaWoodTypes;
import lilypuree.decorative_blocks.blocks.types.WoodDecorativeBlockTypes;
import lilypuree.decorative_blocks.fluid.ThatchFluidBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import java.util.HashMap;
import java.util.Map;

public class DBBlocks {
    public static Block THATCH;

    public static Block BONFIRE;
    public static Block CHANDELIER;
    public static Block BRAZIER;
    public static Block SOUL_CHANDELIER;
    public static Block SOUL_BRAZIER;
    public static Block SOUL_BONFIRE;
    public static Block BAR_PANEL;
    public static Block LATTICE;
    public static Block CHAIN;
    public static Block STONE_PILLAR;
    public static Block ROCKY_DIRT;

    public static Map<IWoodType, BeamBlock> BEAMS = new HashMap<>();
    public static Map<IWoodType, PalisadeBlock> PALISADES = new HashMap<>();
    public static Map<IWoodType, SupportBlock> SUPPORTS = new HashMap<>();
    public static Map<IWoodType, SeatBlock> SEATS = new HashMap<>();

    public static final BlockBehaviour.Properties chainProperties = Block.Properties.of(Material.METAL, MaterialColor.COLOR_BLACK).strength(4.3F).sound(SoundType.METAL).noOcclusion();
    public static final Material thatchMaterial = (new Material.Builder(MaterialColor.COLOR_YELLOW)).noCollider().nonSolid().replaceable().liquid().build();

    public static void init() {
        THATCH =  new ThatchFluidBlock(Registration.STILL_THATCH, Block.Properties.of(thatchMaterial).noCollission().randomTicks().strength(100.0F).noDrops());

        BONFIRE = new BonfireBlock(Block.Properties.of(Material.FIRE, MaterialColor.FIRE).noCollission().strength(0).sound(SoundType.WOOL).lightLevel(state -> 15).noDrops());
        CHANDELIER = new ChandelierBlock(Block.Properties.of(Material.DECORATION).strength(0.3F).sound(SoundType.WOOD).noOcclusion().lightLevel(state -> 15), false);
        BRAZIER = new BrazierBlock(Block.Properties.of(Material.METAL).strength(3.0F).sound(SoundType.METAL).lightLevel(state -> state.getValue(BlockStateProperties.LIT) ? 15 : 0).noOcclusion(), false);
        SOUL_BONFIRE = new BonfireBlock(Block.Properties.of(Material.FIRE, MaterialColor.COLOR_CYAN).noCollission().strength(0).sound(SoundType.WOOL).lightLevel(state -> 14).noDrops());
        SOUL_CHANDELIER = new ChandelierBlock(Block.Properties.of(Material.DECORATION).strength(0.3F).sound(SoundType.WOOD).noOcclusion().lightLevel(state -> 11), true);
        SOUL_BRAZIER = new BrazierBlock(Block.Properties.of(Material.METAL).strength(3.0F).sound(SoundType.METAL).lightLevel(state -> state.getValue(BlockStateProperties.LIT) ? 10 : 0).noOcclusion(), true);
        BAR_PANEL = new BarPanelBlock(Block.Properties.of(Material.METAL, MaterialColor.COLOR_BLACK).strength(5.0F).sound(SoundType.METAL).noOcclusion());
        LATTICE = new LatticeBlock(Block.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(1.2F).sound(SoundType.WOOD).noOcclusion());
        CHAIN = new ChainBlock(chainProperties);
        STONE_PILLAR = new PillarBlock(Block.Properties.of(Material.STONE).strength(1.5F, 6.5F));
        ROCKY_DIRT = new RockyDirtBlock();

        for (IWoodType woodType : VanillaWoodTypes.values()) {
            BEAMS.put(woodType, (BeamBlock) createDecorativeBlock(woodType, WoodDecorativeBlockTypes.BEAM));
            PALISADES.put(woodType, (PalisadeBlock) createDecorativeBlock(woodType, WoodDecorativeBlockTypes.PALISADE));
            SUPPORTS.put(woodType, (SupportBlock) createDecorativeBlock(woodType, WoodDecorativeBlockTypes.SUPPORT));
            SEATS.put(woodType, (SeatBlock) createDecorativeBlock(woodType, WoodDecorativeBlockTypes.SEAT));
        }
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
}