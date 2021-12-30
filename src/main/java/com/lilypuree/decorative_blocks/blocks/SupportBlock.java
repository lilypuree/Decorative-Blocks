package com.lilypuree.decorative_blocks.blocks;

import com.google.common.collect.ImmutableMap;
import com.lilypuree.decorative_blocks.datagen.types.IWoodType;
import com.lilypuree.decorative_blocks.items.SwitchableBlockItem;
import com.lilypuree.decorative_blocks.state.ModBlockProperties;
import com.lilypuree.decorative_blocks.state.SupportFaceShape;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SupportBlock extends HorizontalBlock implements IWaterLoggable, IWoodenBlock {
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
    private IWoodType woodType;

    private final ImmutableMap<BlockState, VoxelShape> stateToShapeMap;

    public SupportBlock(Block.Properties properties, IWoodType woodType) {
        super(properties);
        this.stateToShapeMap = getStateToShapeMap(this.getStateDefinition());
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, Boolean.FALSE).setValue(UP, Boolean.TRUE).setValue(HORIZONTAL_SHAPE, SupportFaceShape.BIG).setValue(VERTICAL_SHAPE, SupportFaceShape.SMALL));
        this.woodType = woodType;
    }

    @Override
    public IWoodType getWoodType() {
        return woodType;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return stateToShapeMap.get(state);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        FluidState ifluidstate = context.getLevel().getFluidState(context.getClickedPos());
        boolean flag = ifluidstate.is(FluidTags.WATER) && ifluidstate.getAmount() == 8;
        BlockState blockstate = this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(WATERLOGGED, flag);
        ItemStack stack = context.getItemInHand();
        if (stack.getItem() instanceof SwitchableBlockItem) {
            blockstate = ((SwitchableBlockItem<?, ?>) stack.getItem()).getSwitchedState(blockstate, stack);
        }
        if (!blockstate.getValue(UP)) {
            blockstate = blockstate.setValue(HORIZONTAL_SHAPE, SupportFaceShape.SMALL);
        }
        return blockstate;
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (player.getItemInHand(handIn).getToolTypes().contains(ToolType.AXE)) {
            if (!worldIn.isClientSide()) {
                onSupportActivation(state, worldIn, pos, player, handIn, hit);
            }
            return ActionResultType.sidedSuccess(worldIn.isClientSide);
        }
        return super.use(state, worldIn, pos, player, handIn, hit);
    }

    public static void onSupportActivation(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        double hitHeight = hit.getLocation().y() - pos.getY();
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
                else{
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
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, UP, HORIZONTAL_SHAPE, VERTICAL_SHAPE);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return !state.getValue(WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public boolean isPathfindable(BlockState p_196266_1_, IBlockReader p_196266_2_, BlockPos p_196266_3_, PathType p_196266_4_) {
        return false;
    }

    private static ImmutableMap<BlockState, VoxelShape> getStateToShapeMap(StateContainer<Block, BlockState> stateManager) {
        Map<BlockState, VoxelShape> map = stateManager.getPossibleStates().stream()
                .collect(Collectors.toMap(Function.identity(), SupportBlock::getShapeForState));
        return ImmutableMap.copyOf(map);
    }

    private static VoxelShape getShapeForState(BlockState blockState) {
        boolean up = blockState.getValue(UP);
        Direction dir = blockState.getValue(FACING);
        boolean ns = dir.getAxis() == Direction.Axis.Z;
        VoxelShape horizontal = VoxelShapes.empty();
        VoxelShape vertical = VoxelShapes.empty();
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
                if (horizontal == VoxelShapes.empty()) {
                    return VoxelShapes.block();
                }
        }
        return VoxelShapes.or(horizontal, vertical);
    }


    static {
        verticalSmall = new EnumMap<Direction, VoxelShape>(Direction.class);
        verticalLarge = new EnumMap<Direction, VoxelShape>(Direction.class);
        Direction.Plane.HORIZONTAL.stream().forEach(dir -> {
            int x = dir.getStepX();
            int z = dir.getStepZ();
            verticalLarge.put(dir, Block.box((1 - x * x) * 0 + x * x * (d1 / 2 - d1 / 2 * x), 0, (1 - z * z) * 0 + z * z * (d1 / 2 - d1 / 2 * z),
                    (1 - x * x) * 16 + x * x * ((d0 + 16) / 2 + (d0 - 16) / 2 * x), 16, (1 - z * z) * 16 + z * z * ((d0 + 16) / 2 + (d0 - 16) / 2 * z)));
            verticalSmall.put(dir, Block.box((1 - x * x) * d2 + x * x * (d1 / 2 - d1 / 2 * x), 0, (1 - z * z) * d2 + z * z * (d1 / 2 - d1 / 2 * z),
                    (1 - x * x) * d3 + x * x * ((d0 + 16) / 2 + (d0 - 16) / 2 * x), 16, (1 - z * z) * d3 + z * z * ((d0 + 16) / 2 + (d0 - 16) / 2 * z)));
        });
    }

}
