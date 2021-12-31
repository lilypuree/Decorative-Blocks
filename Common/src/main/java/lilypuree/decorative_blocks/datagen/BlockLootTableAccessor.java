//package lilypuree.decorative_blocks.datagen;
//
//import net.minecraft.advancements.critereon.EnchantmentPredicate;
//import net.minecraft.advancements.critereon.ItemPredicate;
//import net.minecraft.advancements.critereon.MinMaxBounds;
//import net.minecraft.data.loot.BlockLoot;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.item.enchantment.Enchantments;
//import net.minecraft.world.level.ItemLike;
//import net.minecraft.world.level.block.Block;
//import net.minecraft.world.level.storage.loot.LootTable;
//import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
//import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay;
//import net.minecraft.world.level.storage.loot.functions.FunctionUserBuilder;
//import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
//import net.minecraft.world.level.storage.loot.predicates.MatchTool;
//
//public class BlockLootTableAccessor extends BlockLoot {
//    private static final LootItemCondition.Builder SILK_TOUCH = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))));
//
//    public static <T> T withExplosionDecayWithoutImmuneCheck(ItemLike item, FunctionUserBuilder<T> function) {
//        return function.apply(ApplyExplosionDecay.explosionDecay());
//    }
//
//    public static LootTable.Builder droppingItemWithFortune(Block block, Item item) {
//        return BlockLoot.createOreDrop(block, item);
//    }
//
//    public static LootTable.Builder droppingSlab(Block slab) {
//        return BlockLoot.createSlabItemTable(slab);
//    }
//
//    public static LootTable.Builder dropping(ItemLike item) {
//        return BlockLoot.createSingleItemTable(item);
//    }
//
//    public static LootTable.Builder droppingWithSilkTouch(Block block, LootPoolEntryContainer.Builder<?> builder) {
//        return BlockLoot.createSelfDropDispatchTable(block, SILK_TOUCH, builder);
//    }
//
//    public static LootTable.Builder droppingWithSilkTouch(Block block, ItemLike noSilkTouch) {
//        return BlockLoot.createSingleItemTableWithSilkTouch(block, noSilkTouch);
//    }
//}