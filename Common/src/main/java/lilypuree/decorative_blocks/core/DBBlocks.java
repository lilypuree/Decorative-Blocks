package lilypuree.decorative_blocks.core;

import com.google.common.collect.ImmutableMap;
import lilypuree.decorative_blocks.blocks.*;
import lilypuree.decorative_blocks.datagen.types.IWoodType;
import lilypuree.decorative_blocks.datagen.types.VanillaWoodTypes;
import lilypuree.decorative_blocks.datagen.types.WoodDecorativeBlockTypes;
import lilypuree.decorative_blocks.fluid.ThatchFluidBlock;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static lilypuree.decorative_blocks.core.Registration.STILL_THATCH;

public class DBBlocks {
    protected static final BlockBehaviour.Properties chainProperties = Block.Properties.of(Material.METAL, MaterialColor.COLOR_BLACK).strength(4.3F).sound(SoundType.METAL).noOcclusion();

    public static Block BONFIRE = new BonfireBlock(Block.Properties.of(Material.FIRE, MaterialColor.FIRE).noCollission().strength(0).sound(SoundType.WOOL).lightLevel(state -> 15).noDrops());
    public static Block SOUL_BONFIRE = new BonfireBlock(Block.Properties.of(Material.FIRE, MaterialColor.COLOR_CYAN).noCollission().strength(0).sound(SoundType.WOOL).lightLevel(state -> 14).noDrops());
    public static Block CHANDELIER = new ChandelierBlock(Block.Properties.of(Material.DECORATION).strength(0.3F).sound(SoundType.WOOD).noOcclusion().lightLevel(state -> 15), false);
    public static Block SOUL_CHANDELIER = new ChandelierBlock(Block.Properties.of(Material.DECORATION).strength(0.3F).sound(SoundType.WOOD).noOcclusion().lightLevel(state -> 11), true);
    public static Block BRAZIER = new BrazierBlock(Block.Properties.of(Material.METAL).strength(3.0F).sound(SoundType.METAL).lightLevel(state -> state.getValue(BlockStateProperties.LIT) ? 15 : 0).noOcclusion(), false);
    public static Block SOUL_BRAZIER = new BrazierBlock(Block.Properties.of(Material.METAL).strength(3.0F).sound(SoundType.METAL).lightLevel(state -> state.getValue(BlockStateProperties.LIT) ? 10 : 0).noOcclusion(), true);

    public static Block BAR_PANEL = new BarPanelBlock(Block.Properties.of(Material.METAL, MaterialColor.COLOR_BLACK).strength(5.0F).sound(SoundType.METAL).noOcclusion());
    public static Block LATTICE = new LatticeBlock(Block.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(1.2F).sound(SoundType.WOOD).noOcclusion());
    public static Block CHAIN = new ChainBlock(chainProperties);
    public static Block STONE_PILLAR = new PillarBlock(Block.Properties.of(Material.STONE).strength(1.5F, 6.5F));
    public static Block ROCKY_DIRT = new RockyDirtBlock();

    public static Map<IWoodType, BeamBlock> BEAMS = new HashMap<>();
    public static Map<IWoodType, PalisadeBlock> PALISADES = new HashMap<>();
    public static Map<IWoodType, SupportBlock> SUPPORTS = new HashMap<>();
    public static Map<IWoodType, SeatBlock> SEATS = new HashMap<>();

    private static Block createDecorativeBlock(IWoodType wood, WoodDecorativeBlockTypes woodDecorativeBlockType) {
        Block.Properties woodProperty = BlockBehaviour.Properties.of(wood.getMaterial(), wood.getMaterialColor()).strength(1.2F).sound(wood.getSoundType());
        Block.Properties palisadeProperty = BlockBehaviour.Properties.of(wood.getMaterial(), wood.getMaterialColor()).strength(2.0F, 4.0F).sound(wood.getSoundType());

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

    static {
        for (IWoodType wood : VanillaWoodTypes.values()) {
            BEAMS.put(wood, (BeamBlock) createDecorativeBlock(wood, WoodDecorativeBlockTypes.BEAM));
            SEATS.put(wood, (SeatBlock) createDecorativeBlock(wood, WoodDecorativeBlockTypes.SEAT));
            SUPPORTS.put(wood, (SupportBlock) createDecorativeBlock(wood, WoodDecorativeBlockTypes.SUPPORT));
            PALISADES.put(wood, (PalisadeBlock) createDecorativeBlock(wood, WoodDecorativeBlockTypes.PALISADE));
        }
    }

}