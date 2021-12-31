package lilypuree.decorative_blocks.items;

import com.google.common.collect.ImmutableSet;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BlockstateCopyItem extends Item {
    public static Map<Block, Set<Property<?>>> allowedProperties = new HashMap<>();

    public BlockstateCopyItem(Properties properties) {
        super(properties);

    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        if (!world.isClientSide) {
            Player entity = context.getPlayer();
            ItemStack tool = context.getItemInHand();
            BlockPos pos = context.getClickedPos();
            BlockState existing = world.getBlockState(pos);
            if (entity.isShiftKeyDown()) {
                if (copyBlockState(tool, existing)) {
                    return InteractionResult.SUCCESS;
                }
            } else {
                BlockState pasteResult = pasteTo(existing, tool);
                if (pasteResult != null) {
                    world.setBlock(pos, pasteResult, 2 | 16);
                    return InteractionResult.SUCCESS;
                }
            }
            return InteractionResult.FAIL;
        } else {
            return InteractionResult.PASS;
        }
    }

    private BlockState pasteTo(BlockState state, ItemStack tool) {
        CompoundTag tag = tool.getTagElement("blockstate");
        if (tag != null) {
            BlockState clipboard = NbtUtils.readBlockState(tag);
            if (state.getBlock() == clipboard.getBlock()) {
                for (Property property : allowedProperties.get(state.getBlock())) {
                    state = state.setValue(property, clipboard.getValue(property));
                }
                return state;
            }
        }
        return null;
    }

    private boolean copyBlockState(ItemStack tool, BlockState state) {
        if (allowedProperties.containsKey(state.getBlock())) {
            CompoundTag nbt = tool.getOrCreateTag();
            nbt.put("blockstate", NbtUtils.writeBlockState(state));
            tool.setTag(nbt);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        if (Screen.hasShiftDown()) {
            CompoundTag tag = stack.getTagElement("blockstate");
            String blockstatename = "none";
            if (tag != null) {
                blockstatename = NbtUtils.readBlockState(tag).getBlock().getDescriptionId();
            }
            tooltip.add(new TranslatableComponent("wiki.decorative_blocks.copy1"));
            tooltip.add(new TranslatableComponent("wiki.decorative_blocks.copy2", blockstatename));
        }

        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }

    static {
        Registry.BLOCK.forEach(block -> {
            if (block instanceof FenceBlock || block instanceof IronBarsBlock) {
                addProperties(block, CrossCollisionBlock.NORTH, CrossCollisionBlock.EAST, CrossCollisionBlock.SOUTH, CrossCollisionBlock.WEST);
            } else if (block instanceof WallBlock) {
                allowedProperties.put(block, ImmutableSet.of(WallBlock.UP, WallBlock.EAST_WALL, WallBlock.NORTH_WALL, WallBlock.SOUTH_WALL, WallBlock.WEST_WALL));
            } else if (block instanceof StairBlock) {
                allowedProperties.put(block, ImmutableSet.of(StairBlock.SHAPE, StairBlock.FACING, StairBlock.HALF));
            } else if (block instanceof SlabBlock) {
//                allowedProperties.put(block, ImmutableSet.of(SlabBlock.TYPE));
            } else if (block instanceof ComparatorBlock) {
                allowedProperties.put(block, ImmutableSet.of(ComparatorBlock.MODE));
            } else if (block.getStateDefinition().getProperties().contains(BlockStateProperties.RAIL_SHAPE_STRAIGHT)) {
                allowedProperties.put(block, ImmutableSet.of(BlockStateProperties.RAIL_SHAPE_STRAIGHT));
            } else if (block instanceof DispenserBlock) {
                allowedProperties.put(block, ImmutableSet.of(DispenserBlock.FACING));
            } else if (block instanceof GlazedTerracottaBlock || block instanceof RepeaterBlock) {
                addProperties(block, HorizontalDirectionalBlock.FACING);
            } else if (block instanceof HugeMushroomBlock) {
                allowedProperties.put(block, ImmutableSet.of(HugeMushroomBlock.UP, HugeMushroomBlock.EAST, HugeMushroomBlock.WEST, HugeMushroomBlock.DOWN, HugeMushroomBlock.NORTH, HugeMushroomBlock.SOUTH));
            } else if (block instanceof ObserverBlock) {
                allowedProperties.put(block, ImmutableSet.of(DirectionalBlock.FACING));
            } else if (block.getStateDefinition().getProperties().contains(BlockStateProperties.RAIL_SHAPE)) {
                addProperties(block, BlockStateProperties.RAIL_SHAPE);
            } else if (block instanceof TrapDoorBlock) {
                addProperties(block, BlockStateProperties.HORIZONTAL_FACING, TrapDoorBlock.HALF, TrapDoorBlock.OPEN);
            } else if (block instanceof RotatedPillarBlock) {
                addProperties(block, RotatedPillarBlock.AXIS);
            } else if (block instanceof RedStoneWireBlock) {
                addProperties(block, RedStoneWireBlock.EAST, RedStoneWireBlock.NORTH, RedStoneWireBlock.SOUTH, RedStoneWireBlock.WEST);
            }
        });
    }

    public static void addProperties(Block block, Property<?>... properties) {
        allowedProperties.put(block, ImmutableSet.copyOf(properties));
    }
}
