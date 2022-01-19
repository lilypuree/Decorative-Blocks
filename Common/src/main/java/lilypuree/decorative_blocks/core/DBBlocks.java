package lilypuree.decorative_blocks.core;

import lilypuree.decorative_blocks.blocks.*;
import lilypuree.decorative_blocks.core.factory.BlockSuppliers;
import lilypuree.decorative_blocks.blocks.types.IWoodType;
import lilypuree.decorative_blocks.blocks.types.VanillaWoodTypes;
import lilypuree.decorative_blocks.blocks.types.WoodDecorativeBlockTypes;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;
import java.util.Map;

public class DBBlocks {
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


    public static void init() {
        BONFIRE = BlockSuppliers.BONFIRE.get();
        CHANDELIER = BlockSuppliers.CHANDELIER.get();
        BRAZIER = BlockSuppliers.BRAZIER.get();
        SOUL_BONFIRE = BlockSuppliers.SOUL_BONFIRE.get();
        SOUL_CHANDELIER = BlockSuppliers.SOUL_CHANDELIER.get();
        SOUL_BRAZIER = BlockSuppliers.SOUL_BRAZIER.get();
        BAR_PANEL = BlockSuppliers.BAR_PANEL.get();
        LATTICE = BlockSuppliers.LATTICE.get();
        CHAIN = BlockSuppliers.CHAIN.get();
        STONE_PILLAR = BlockSuppliers.STONE_PILLAR.get();
        ROCKY_DIRT = BlockSuppliers.ROCKY_DIRT.get();

        for (IWoodType woodType : VanillaWoodTypes.values()) {
            BEAMS.put(woodType, (BeamBlock) BlockSuppliers.createDecorativeBlock(woodType, WoodDecorativeBlockTypes.BEAM));
            PALISADES.put(woodType, (PalisadeBlock) BlockSuppliers.createDecorativeBlock(woodType, WoodDecorativeBlockTypes.PALISADE));
            SUPPORTS.put(woodType, (SupportBlock) BlockSuppliers.createDecorativeBlock(woodType, WoodDecorativeBlockTypes.SUPPORT));
            SEATS.put(woodType, (SeatBlock) BlockSuppliers.createDecorativeBlock(woodType, WoodDecorativeBlockTypes.SEAT));
        }
    }
}