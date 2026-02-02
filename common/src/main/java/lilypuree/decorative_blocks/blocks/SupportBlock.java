package lilypuree.decorative_blocks.blocks;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lilypuree.decorative_blocks.blocks.state.ModBlockProperties;
import lilypuree.decorative_blocks.blocks.state.SupportFaceShape;
import lilypuree.decorative_blocks.items.SwitchableBlockItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SupportBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock, IWoodenBlock {
    public static final MapCodec<SupportBlock> CODEC = RecordCodecBuilder.mapCodec(
            inst -> inst.group(WoodType.CODEC.fieldOf("wood_type").forGetter(block -> block.woodType), propertiesCodec()).apply(inst, SupportBlock::new)
    );

    private static final double d0 = 3D;
    private static final double d1 = 13D;
    private static final double d2 = 4D;
    private static final double d3 = 12D;

    private static final VoxelShape TOP_LARGE = Block.box(0, d1, 0, 16, 16, 16);
    private static final VoxelShape TOP_SMALL_NS = Block.box(d2, d1, 0, d3, 16, 16);
    private static final VoxelShape TOP_SMALL_EW = Block.box(0, d1, d2, 16, 16, d3);
    private static final VoxelShape BOTTOM_LARGE = Block.box(0, 0, 0, 16, d0, 16);
    private static final VoxelShape BOTTOM_SMALL_NS = Block.box(d2, 0, 0, d3, d0, 16);
    private static final VoxelShape BOTTOM_SMALL_EW = Block.box(0, 0, d2, 16, d0, d3);
    private static final Map<Direction, VoxelShape> verticalLarge;
    private static final Map<Direction, VoxelShape> verticalSmall;

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final EnumProperty<SupportFaceShape> HORIZONTAL_SHAPE = ModBlockProperties.HORIZONTAL_SHAPE;
    public static final EnumProperty<SupportFaceShape> VERTICAL_SHAPE = ModBlockProperties.VERTICAL_SHAPE;
    private WoodType woodType;

    private final ImmutableMap<BlockState, VoxelShape> stateToShapeMap;

    public SupportBlock(WoodType woodType, Properties properties) {
        super(properties);
        this.woodType = woodType;
        this.stateToShapeMap = getStateToShapeMap(this.getStateDefinition());
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, Boolean.FALSE).setValue(UP, Boolean.TRUE).setValue(HORIZONTAL_SHAPE, SupportFaceShape.BIG).setValue(VERTICAL_SHAPE, SupportFaceShape.SMALL));
    }

    @Override
    public WoodType getWoodType() {
        return woodType;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return stateToShapeMap.get(state);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState ifluidstate = context.getLevel().getFluidState(context.getClickedPos());
        boolean flag = ifluidstate.is(FluidTags.WATER) && ifluidstate.getAmount() == 8;
        BlockState blockstate = this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(WATERLOGGED, flag);
        ItemStack stack = context.getItemInHand();
        if (stack.getItem() instanceof SwitchableBlockItem) {
            blockstate = ((SwitchableBlockItem) stack.getItem()).getSwitchedState(blockstate, stack);
        }
        if (!blockstate.getValue(UP)) {
            blockstate = blockstate.setValue(HORIZONTAL_SHAPE, SupportFaceShape.SMALL);
        }
        return blockstate;
    }

    public static void onSupportActivation(BlockState state, Level worldIn, BlockPos pos, Player player, Vec3 hitPos) {
        double hitHeight = hitPos.y() - pos.getY();
        boolean hitVertical;
        if (state.getValue(HORIZONTAL_SHAPE).isHidden()) {
            hitVertical = true;
        } else if (state.getValue(VERTICAL_SHAPE).isHidden()) {
            hitVertical = false;
        } else {
            if (state.getValue(UP)) {
                hitVertical = hitHeight < d1 / 16;
            } else {
                hitVertical = hitHeight > d0 / 16;
            }
        }
        if (player.isShiftKeyDown()) {
            if (hitVertical) {
                if (!state.getValue(HORIZONTAL_SHAPE).isHidden())
                    worldIn.setBlockAndUpdate(pos, state.setValue(VERTICAL_SHAPE, SupportFaceShape.HIDDEN));
                else {
                    worldIn.setBlockAndUpdate(pos, state.setValue(HORIZONTAL_SHAPE, SupportFaceShape.BIG));
                }
            } else {
                if (!state.getValue(VERTICAL_SHAPE).isHidden())
                    worldIn.setBlockAndUpdate(pos, state.setValue(HORIZONTAL_SHAPE, SupportFaceShape.HIDDEN));
                else
                    worldIn.setBlockAndUpdate(pos, state.setValue(VERTICAL_SHAPE, SupportFaceShape.SMALL));
            }
        } else {
            if (hitVertical) {
                worldIn.setBlockAndUpdate(pos, state.setValue(VERTICAL_SHAPE, state.getValue(VERTICAL_SHAPE).getSwitched()));
            } else {
                worldIn.setBlockAndUpdate(pos, state.setValue(HORIZONTAL_SHAPE, state.getValue(HORIZONTAL_SHAPE).getSwitched()));
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, UP, HORIZONTAL_SHAPE, VERTICAL_SHAPE);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return !state.getValue(WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
        return false;
    }

    private static ImmutableMap<BlockState, VoxelShape> getStateToShapeMap(StateDefinition<Block, BlockState> stateManager) {
        Map<BlockState, VoxelShape> map = stateManager.getPossibleStates().stream()
                .collect(Collectors.toMap(Function.identity(), SupportBlock::getShapeForState));
        return ImmutableMap.copyOf(map);
    }

    private static VoxelShape getShapeForState(BlockState blockState) {
        boolean up = blockState.getValue(UP);
        Direction dir = blockState.getValue(FACING);
        boolean ns = dir.getAxis() == Direction.Axis.Z;
        VoxelShape horizontal = Shapes.empty();
        VoxelShape vertical = Shapes.empty();
        switch (blockState.getValue(HORIZONTAL_SHAPE)) {
            case BIG:
                horizontal = up ? TOP_LARGE : BOTTOM_LARGE;
                break;
            case SMALL:
                horizontal = up ? (ns ? TOP_SMALL_NS : TOP_SMALL_EW) : (ns ? BOTTOM_SMALL_NS : BOTTOM_SMALL_EW);
                break;
        }
        switch (blockState.getValue(VERTICAL_SHAPE)) {
            case BIG:
                vertical = verticalLarge.get(dir);
                break;
            case SMALL:
                vertical = verticalSmall.get(dir);
                break;
            case HIDDEN:
                if (horizontal == Shapes.empty()) {
                    return Shapes.block();
                }
        }
        return Shapes.or(horizontal, vertical);
    }


    static {
        verticalSmall = new EnumMap<>(Direction.class);
        verticalLarge = new EnumMap<>(Direction.class);
        Direction.Plane.HORIZONTAL.stream().forEach(dir -> {
            int x = dir.getStepX();
            int z = dir.getStepZ();
            verticalLarge.put(dir, Block.box((1 - x * x) * 0 + x * x * (d1 / 2 - d1 / 2 * x), 0, (1 - z * z) * 0 + z * z * (d1 / 2 - d1 / 2 * z),
                    (1 - x * x) * 16 + x * x * ((d0 + 16) / 2 + (d0 - 16) / 2 * x), 16, (1 - z * z) * 16 + z * z * ((d0 + 16) / 2 + (d0 - 16) / 2 * z)));
            verticalSmall.put(dir, Block.box((1 - x * x) * d2 + x * x * (d1 / 2 - d1 / 2 * x), 0, (1 - z * z) * d2 + z * z * (d1 / 2 - d1 / 2 * z),
                    (1 - x * x) * d3 + x * x * ((d0 + 16) / 2 + (d0 - 16) / 2 * x), 16, (1 - z * z) * d3 + z * z * ((d0 + 16) / 2 + (d0 - 16) / 2 * z)));
        });
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }
}
