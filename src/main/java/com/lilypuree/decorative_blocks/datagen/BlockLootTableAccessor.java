package com.lilypuree.decorative_blocks.datagen;

import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.block.Block;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.loot.ILootFunctionConsumer;
import net.minecraft.loot.LootEntry;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.MatchTool;
import net.minecraft.loot.functions.ExplosionDecay;
import net.minecraft.util.IItemProvider;

public class BlockLootTableAccessor extends BlockLootTables {
    private static final ILootCondition.IBuilder SILK_TOUCH = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1))));

    public static <T> T withExplosionDecayWithoutImmuneCheck(IItemProvider item, ILootFunctionConsumer<T> function) {
        return function.apply(ExplosionDecay.explosionDecay());
    }

    public static LootTable.Builder droppingItemWithFortune(Block block, Item item) {
        return BlockLootTables.createOreDrop(block, item);
    }

    public static LootTable.Builder droppingSlab(Block slab) {
        return BlockLootTables.createSlabItemTable(slab);
    }

    public static LootTable.Builder dropping(IItemProvider item) {
        return BlockLootTables.createSingleItemTable(item);
    }

    public static LootTable.Builder droppingWithSilkTouch(Block block, LootEntry.Builder<?> builder) {
        return BlockLootTables.createSelfDropDispatchTable(block, SILK_TOUCH, builder);
    }

    public static LootTable.Builder droppingWithSilkTouch(Block block, IItemProvider noSilkTouch) {
        return BlockLootTables.createSingleItemTableWithSilkTouch(block, noSilkTouch);
    }
}