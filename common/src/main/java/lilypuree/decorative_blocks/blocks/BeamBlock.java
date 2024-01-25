package lilypuree.decorative_blocks.blocks;

import lilypuree.decorative_blocks.blocks.types.IWoodType;
import net.minecraft.world.level.block.RotatedPillarBlock;

public class BeamBlock extends RotatedPillarBlock implements IWoodenBlock {

    private IWoodType woodType;

    public BeamBlock(Properties properties, IWoodType woodType) {
        super(properties);
        this.woodType = woodType;
    }

    @Override
    public IWoodType getWoodType() {
        return woodType;
    }
}
