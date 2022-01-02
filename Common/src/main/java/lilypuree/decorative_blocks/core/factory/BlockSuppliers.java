package lilypuree.decorative_blocks.core.factory;

import lilypuree.decorative_blocks.blocks.*;
import lilypuree.decorative_blocks.core.Registration;
import lilypuree.decorative_blocks.datagen.types.IWoodType;
import lilypuree.decorative_blocks.datagen.types.WoodDecorativeBlockTypes;
import lilypuree.decorative_blocks.fluid.ThatchFluidBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import java.util.function.Supplier;

public class BlockSuppliers {
    public static final Material thatchMaterial = (new Material.Builder(MaterialColor.COLOR_YELLOW)).noCollider().nonSolid().replaceable().liquid().build();
    public static final BlockBehaviour.Properties chainProperties = Block.Properties.of(Material.METAL, MaterialColor.COLOR_BLACK).strength(4.3F).sound(SoundType.METAL).noOcclusion();

    public static Supplier<Block> THATCH = () -> new ThatchFluidBlock(Registration.STILL_THATCH, Block.Properties.of(thatchMaterial).noCollission().randomTicks().strength(100.0F).noDrops());

    public static Supplier<Block> BONFIRE = () -> new BonfireBlock(Block.Properties.of(Material.FIRE, MaterialColor.FIRE).noCollission().strength(0).sound(SoundType.WOOL).lightLevel(state -> 15).noDrops());
    public static Supplier<Block> SOUL_BONFIRE = () -> new BonfireBlock(Block.Properties.of(Material.FIRE, MaterialColor.COLOR_CYAN).noCollission().strength(0).sound(SoundType.WOOL).lightLevel(state -> 14).noDrops());
    public static Supplier<Block> CHANDELIER = () -> new ChandelierBlock(Block.Properties.of(Material.DECORATION).strength(0.3F).sound(SoundType.WOOD).noOcclusion().lightLevel(state -> 15), false);
    public static Supplier<Block> SOUL_CHANDELIER = () -> new ChandelierBlock(Block.Properties.of(Material.DECORATION).strength(0.3F).sound(SoundType.WOOD).noOcclusion().lightLevel(state -> 11), true);
    public static Supplier<Block> BRAZIER = () -> new BrazierBlock(Block.Properties.of(Material.METAL).strength(3.0F).sound(SoundType.METAL).lightLevel(state -> state.getValue(BlockStateProperties.LIT) ? 15 : 0).noOcclusion(), false);
    public static Supplier<Block> SOUL_BRAZIER = () -> new BrazierBlock(Block.Properties.of(Material.METAL).strength(3.0F).sound(SoundType.METAL).lightLevel(state -> state.getValue(BlockStateProperties.LIT) ? 10 : 0).noOcclusion(), true);
    public static Supplier<Block> BAR_PANEL = () -> new BarPanelBlock(Block.Properties.of(Material.METAL, MaterialColor.COLOR_BLACK).strength(5.0F).sound(SoundType.METAL).noOcclusion());
    public static Supplier<Block> LATTICE = () -> new LatticeBlock(Block.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(1.2F).sound(SoundType.WOOD).noOcclusion());
    public static Supplier<Block> CHAIN = () -> new ChainBlock(chainProperties);
    public static Supplier<Block> STONE_PILLAR = () -> new PillarBlock(Block.Properties.of(Material.STONE).strength(1.5F, 6.5F));
    public static Supplier<Block> ROCKY_DIRT = () -> new RockyDirtBlock();

    public static Block createDecorativeBlock(IWoodType wood, WoodDecorativeBlockTypes woodDecorativeBlockType) {
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

}
