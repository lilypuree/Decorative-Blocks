package lilypuree.decorative_blocks.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;

public class RockyDirtBlock extends Block {
    public RockyDirtBlock() {
        super(Properties.of().mapColor(MapColor.DIRT).strength(1.0F, 6.0F).sound(SoundType.GRAVEL));
    }

//    @Override
//    public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, IPlantable plantable) {
//        return plantable.getPlantType(world, pos) != PlantType.CROP;
//    }
}
