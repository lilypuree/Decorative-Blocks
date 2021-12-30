package com.lilypuree.decorative_blocks.items;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.state.Property;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.*;

import net.minecraft.item.Item.Properties;

public class BlockstateCopyItem extends Item {
    public static Map<Block, Set<Property<?>>> allowedProperties = new HashMap<>();

    public BlockstateCopyItem(Properties properties) {
        super(properties);

    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        World world = context.getLevel();
        if (!world.isClientSide) {
            PlayerEntity entity = context.getPlayer();
            ItemStack tool = context.getItemInHand();
            BlockPos pos = context.getClickedPos();
            BlockState existing = world.getBlockState(pos);
            if (entity.isShiftKeyDown()) {
                if (copyBlockState(tool, existing)) {
                    return ActionResultType.SUCCESS;
                }
            } else {
                BlockState pasteResult = pasteTo(existing, tool);
                if (pasteResult != null) {
                    world.setBlock(pos, pasteResult, 2 | 16);
                    return ActionResultType.SUCCESS;
                }
            }
            return ActionResultType.FAIL;
        } else {
            return ActionResultType.PASS;
        }
    }

    private BlockState pasteTo(BlockState state, ItemStack tool) {
        CompoundNBT tag = tool.getTagElement("blockstate");
        if (tag != null) {
            BlockState clipboard = NBTUtil.readBlockState(tag);
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
            CompoundNBT nbt = tool.getOrCreateTag();
            nbt.put("blockstate", NBTUtil.writeBlockState(state));
            tool.setTag(nbt);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (Screen.hasShiftDown()) {
            CompoundNBT tag = stack.getTagElement("blockstate");
            String blockstatename = "none";
            if (tag != null) {
                blockstatename = NBTUtil.readBlockState(tag).getBlock().getDescriptionId();
            }
            tooltip.add(new TranslationTextComponent("wiki.decorative_blocks.copy1"));
            tooltip.add(new TranslationTextComponent("wiki.decorative_blocks.copy2", blockstatename));
        }

        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }

    static {
        Registry.BLOCK.forEach(block -> {
            if (block instanceof FenceBlock || block instanceof PaneBlock) {
                addProperties(block, FourWayBlock.NORTH, FourWayBlock.EAST, FourWayBlock.SOUTH, FourWayBlock.WEST);
            } else if (block instanceof WallBlock) {
                allowedProperties.put(block, ImmutableSet.of(WallBlock.UP, WallBlock.EAST_WALL, WallBlock.NORTH_WALL, WallBlock.SOUTH_WALL, WallBlock.WEST_WALL));
            } else if (block instanceof StairsBlock) {
                allowedProperties.put(block, ImmutableSet.of(StairsBlock.SHAPE, StairsBlock.FACING, StairsBlock.HALF));
            } else if (block instanceof SlabBlock) {
//                allowedProperties.put(block, ImmutableSet.of(SlabBlock.TYPE));
            } else if (block instanceof ComparatorBlock) {
                allowedProperties.put(block, ImmutableSet.of(ComparatorBlock.MODE));
            } else if (block.getStateDefinition().getProperties().contains(BlockStateProperties.RAIL_SHAPE_STRAIGHT)) {
                allowedProperties.put(block, ImmutableSet.of(BlockStateProperties.RAIL_SHAPE_STRAIGHT));
            } else if (block instanceof DispenserBlock) {
                allowedProperties.put(block, ImmutableSet.of(DispenserBlock.FACING));
            } else if (block instanceof GlazedTerracottaBlock || block instanceof RepeaterBlock) {
                addProperties(block, HorizontalBlock.FACING);
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
            } else if (block instanceof RedstoneWireBlock) {
                addProperties(block, RedstoneWireBlock.EAST, RedstoneWireBlock.NORTH, RedstoneWireBlock.SOUTH, RedstoneWireBlock.WEST);
            }
        });
    }

    public static void addProperties(Block block, Property<?>... properties) {
        allowedProperties.put(block, ImmutableSet.copyOf(properties));
    }
}
