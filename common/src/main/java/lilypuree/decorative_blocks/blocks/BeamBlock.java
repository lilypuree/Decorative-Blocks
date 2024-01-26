package lilypuree.decorative_blocks.blocks;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.properties.WoodType;

public class BeamBlock extends RotatedPillarBlock implements IWoodenBlock {
    public static final MapCodec<BeamBlock> CODEC = RecordCodecBuilder.mapCodec(
            inst -> inst.group(WoodType.CODEC.fieldOf("wood_type").forGetter(block -> block.woodType), propertiesCodec()).apply(inst, BeamBlock::new)
    );

    @Override
    public MapCodec<? extends RotatedPillarBlock> codec() {
        return CODEC;
    }

    private WoodType woodType;

    public BeamBlock(WoodType woodType, Properties properties) {
        super(properties);
        this.woodType = woodType;
    }

    @Override
    public WoodType getWoodType() {
        return woodType;
    }
}
