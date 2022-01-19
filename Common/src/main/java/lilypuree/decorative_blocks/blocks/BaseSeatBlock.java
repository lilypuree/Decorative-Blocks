package lilypuree.decorative_blocks.blocks;

import lilypuree.decorative_blocks.blocks.types.IWoodType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class BaseSeatBlock extends Block implements IWoodenBlock {
    protected static final VoxelShape POST_SHAPE = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 4.0D, 10.0D);
    protected static final VoxelShape TOP_POST = Block.box(6.0D, 7.0D, 6.0D, 10.0D, 16.0D, 10.0D);
    protected static final VoxelShape JOIST_NS = Block.box(0, 4.0D, 4D, 16D, 7D, 12D);
    protected static final VoxelShape JOIST_EW = Block.box(4.0D, 4.0D, 0D, 12D, 7D, 16D);
    protected static final VoxelShape SEAT_NS = Shapes.or(POST_SHAPE, JOIST_NS);
    protected static final VoxelShape SEAT_EW = Shapes.or(POST_SHAPE, JOIST_EW);
    protected static final VoxelShape JOIST_POST_NS = Shapes.or(TOP_POST, JOIST_NS);
    protected static final VoxelShape JOIST_POST_EW = Shapes.or(TOP_POST, JOIST_NS);
    protected static final VoxelShape SEAT_POST_NS = Shapes.or(SEAT_NS, TOP_POST);
    protected static final VoxelShape SEAT_POST_EW = Shapes.or(SEAT_EW, TOP_POST);

    protected IWoodType woodType;

    public BaseSeatBlock(BlockBehaviour.Properties properties, IWoodType woodType) {
        super(properties);
        this.woodType = woodType;
    }

    @Override
    public IWoodType getWoodType() {
        return woodType;
    }

    protected boolean isInAttachablePos(LevelReader worldIn, BlockPos pos) {
        if (worldIn.getBlockState(pos.below()).getBlock() == Blocks.LANTERN) {
            return true;
        }
        return Block.canSupportCenter(worldIn, pos.below(), Direction.UP);
    }

}
