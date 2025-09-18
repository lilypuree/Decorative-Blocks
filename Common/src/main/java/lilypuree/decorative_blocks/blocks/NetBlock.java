package lilypuree.decorative_blocks.blocks;

import lilypuree.decorative_blocks.blocks.state.ModBlockProperties;
import lilypuree.decorative_blocks.blocks.state.NetShape;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class NetBlock extends HorizontalDirectionalBlock {
    public static final EnumProperty<NetShape> SHAPE = ModBlockProperties.NET_SHAPE;
    public static final BooleanProperty ATTACHED = BlockStateProperties.ATTACHED;
    protected static final VoxelShape[] TOP_SHAPES;
    protected static final VoxelShape[] FULL_SHAPES;
    protected static final VoxelShape[] TIP_SHAPES;

    protected NetBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(ATTACHED, true).setValue(SHAPE, NetShape.POINTED));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, SHAPE, ATTACHED);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        int facing = state.getValue(FACING).get2DDataValue();
        NetShape shape = state.getValue(SHAPE);
        if (shape == NetShape.EMPTY) {
            if (state.getValue(ATTACHED)) {
                return TOP_SHAPES[facing];
            } else return TIP_SHAPES[facing];
        } else return FULL_SHAPES[facing];
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        int facing = state.getValue(FACING).get2DDataValue();
        if (state.getValue(ATTACHED)) {
            return TOP_SHAPES[facing];
        } else return Shapes.empty();
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context);
    }

    static {
        TOP_SHAPES = createShapes(2);
        FULL_SHAPES = createShapes(16);
        TIP_SHAPES = createShapes(8);
    }
    protected static VoxelShape[] createShapes(int length) {
        VoxelShape[] shapes = new VoxelShape[4];
        double l = 16D - length;
        shapes[0] = Block.box(0, l, 14D, 16D, 16D, 16D);
        shapes[1] = Block.box(0, l, 0D, 2D, 16D, 16D);
        shapes[2] = Block.box(0, l, 0D, 16D, 16D, 2D);
        shapes[3] = Block.box(14D, l, 0D, 16D, 16D, 16D);
        return shapes;
    }
}
