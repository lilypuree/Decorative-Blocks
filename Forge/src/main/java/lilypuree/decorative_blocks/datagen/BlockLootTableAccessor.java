package lilypuree.decorative_blocks.datagen;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.DynamicLoot;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.*;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.number.BinomialDistributionGenerator;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

public abstract class BlockLootTableAccessor implements Consumer<BiConsumer<ResourceLocation, LootTable.Builder>> {
    public static final LootItemCondition.Builder HAS_SILK_TOUCH = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))));
    public static final LootItemCondition.Builder HAS_NO_SILK_TOUCH = HAS_SILK_TOUCH.invert();
    public static final LootItemCondition.Builder HAS_SHEARS = MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS));
    public static final LootItemCondition.Builder HAS_SHEARS_OR_SILK_TOUCH = HAS_SHEARS.or(HAS_SILK_TOUCH);
    public static final LootItemCondition.Builder HAS_NO_SHEARS_OR_SILK_TOUCH = HAS_SHEARS_OR_SILK_TOUCH.invert();
    public static final Set<Item> EXPLOSION_RESISTANT = new HashSet<>();
    public final Map<ResourceLocation, LootTable.Builder> map = Maps.newHashMap();

//    protected abstract void addTables();
//
//    protected abstract Iterable<Block> getKnownBlocks();
//
//    public void accept(BiConsumer<ResourceLocation, LootTable.Builder> biConsumer) {
//        this.addTables();
//        Set<ResourceLocation> set = Sets.newHashSet();
//
//        for (Block block : getKnownBlocks()) {
//            ResourceLocation lootTable = block.getLootTable();
//            if (lootTable != BuiltInLootTables.EMPTY && set.add(lootTable)) {
//                LootTable.Builder builder = this.map.remove(lootTable);
//                if (builder == null) {
//                    throw new IllegalStateException(String.format("Missing loottable '%s' for '%s'", lootTable, Registry.BLOCK.getKey(block)));
//                }
//
//                biConsumer.accept(lootTable, builder);
//            }
//        }
//
//        if (!this.map.isEmpty()) {
//            throw new IllegalStateException("Created block loot tables for non-blocks: " + this.map.keySet());
//        }
//    }


    public static <T> T applyExplosionDecay(ItemLike itemLike, FunctionUserBuilder<T> builder) {
        return (T) (!EXPLOSION_RESISTANT.contains(itemLike.asItem()) ? builder.apply(ApplyExplosionDecay.explosionDecay()) : builder.unwrap());
    }

    public static <T> T applyExplosionCondition(ItemLike itemLike, ConditionUserBuilder<T> builder) {
        return (T) (!EXPLOSION_RESISTANT.contains(itemLike.asItem()) ? builder.when(ExplosionCondition.survivesExplosion()) : builder.unwrap());
    }

    public static LootTable.Builder createSingleItemTable(ItemLike itemLike) {
        return LootTable.lootTable().withPool(applyExplosionCondition(itemLike, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(itemLike))));
    }

    public static LootTable.Builder createSelfDropDispatchTable(Block block, LootItemCondition.Builder condition, LootPoolEntryContainer.Builder<?> otherwise) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(block).when(condition).otherwise(otherwise)));
    }

    public static LootTable.Builder createSilkTouchDispatchTable(Block block, LootPoolEntryContainer.Builder<?> onSilkTouch) {
        return createSelfDropDispatchTable(block, HAS_SILK_TOUCH, onSilkTouch);
    }

    public static LootTable.Builder createShearsDispatchTable(Block block, LootPoolEntryContainer.Builder<?> onShear) {
        return createSelfDropDispatchTable(block, HAS_SHEARS, onShear);
    }

    public static LootTable.Builder createSilkTouchOrShearsDispatchTable(Block block, LootPoolEntryContainer.Builder<?> drop) {
        return createSelfDropDispatchTable(block, HAS_SHEARS_OR_SILK_TOUCH, drop);
    }

    public static LootTable.Builder createSingleItemTableWithSilkTouch(Block block, ItemLike $$1) {
        return createSilkTouchDispatchTable(block, applyExplosionCondition(block, LootItem.lootTableItem($$1)));
    }

    public static LootTable.Builder createSingleItemTable(ItemLike $$0, NumberProvider $$1) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(applyExplosionDecay($$0, LootItem.lootTableItem($$0).apply(SetItemCountFunction.setCount($$1)))));
    }

    public static LootTable.Builder createSingleItemTableWithSilkTouch(Block $$0, ItemLike $$1, NumberProvider $$2) {
        return createSilkTouchDispatchTable($$0, applyExplosionDecay($$0, LootItem.lootTableItem($$1).apply(SetItemCountFunction.setCount($$2))));
    }

    public static LootTable.Builder createSilkTouchOnlyTable(ItemLike drop) {
        return LootTable.lootTable().withPool(LootPool.lootPool().when(HAS_SILK_TOUCH).setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(drop)));
    }

    public static LootTable.Builder createShearsOnlyDrop(ItemLike onSheared) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(HAS_SHEARS).add(LootItem.lootTableItem(onSheared)));
    }


    public static LootTable.Builder createSlabItemTable(Block slab) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(applyExplosionDecay(slab, LootItem.lootTableItem(slab).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(slab).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SlabBlock.TYPE, SlabType.DOUBLE)))))));
    }

    public static <T extends Comparable<T> & StringRepresentable> LootTable.Builder createSinglePropConditionTable(Block block, Property<T> $$1, T $$2) {
        return LootTable.lootTable().withPool(applyExplosionCondition(block, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(block).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty($$1, $$2))))));
    }

    public static LootTable.Builder createNameableBlockEntityTable(Block $$0) {
        return LootTable.lootTable().withPool(applyExplosionCondition($$0, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem($$0).apply(CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY)))));
    }


    public static LootTable.Builder createOreDrop(Block block, Item oreItem) {
        return createSilkTouchDispatchTable(block, applyExplosionDecay(block, LootItem.lootTableItem(oreItem).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }

    public static LootTable.Builder createMushroomBlockDrop(Block $$0, ItemLike $$1) {
        return createSilkTouchDispatchTable($$0, applyExplosionDecay($$0, LootItem.lootTableItem($$1).apply(SetItemCountFunction.setCount(UniformGenerator.between(-6.0F, 2.0F))).apply(LimitCount.limitCount(IntRange.lowerBound(0)))));
    }

    public static LootTable.Builder createGrassDrops(Block $$0) {
        return createShearsDispatchTable($$0, applyExplosionDecay($$0, LootItem.lootTableItem(Items.WHEAT_SEEDS).when(LootItemRandomChanceCondition.randomChance(0.125F)).apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE, 2))));
    }

    public static LootTable.Builder createStemDrops(Block $$0, Item $$1) {
        return LootTable.lootTable().withPool(applyExplosionDecay($$0, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem($$1).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(3, 0.06666667F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties($$0).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 0)))).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(3, 0.13333334F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties($$0).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 1)))).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(3, 0.2F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties($$0).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 2)))).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(3, 0.26666668F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties($$0).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 3)))).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(3, 0.33333334F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties($$0).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 4)))).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(3, 0.4F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties($$0).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 5)))).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(3, 0.46666667F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties($$0).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 6)))).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(3, 0.53333336F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties($$0).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 7)))))));
    }

    public static LootTable.Builder createAttachedStemDrops(Block $$0, Item $$1) {
        return LootTable.lootTable().withPool(applyExplosionDecay($$0, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem($$1).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(3, 0.53333336F))))));
    }


    public static LootTable.Builder createGlowLichenDrops(Block $$0) {
        return LootTable.lootTable().withPool(LootPool.lootPool().add(applyExplosionDecay($$0, LootItem.lootTableItem($$0).when(HAS_SHEARS).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F), true).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties($$0).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(PipeBlock.EAST, true)))).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F), true).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties($$0).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(PipeBlock.WEST, true)))).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F), true).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties($$0).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(PipeBlock.NORTH, true)))).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F), true).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties($$0).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(PipeBlock.SOUTH, true)))).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F), true).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties($$0).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(PipeBlock.UP, true)))).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F), true).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties($$0).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(PipeBlock.DOWN, true)))).apply(SetItemCountFunction.setCount(ConstantValue.exactly(-1.0F), true)))));
    }

    public static LootTable.Builder createLeavesDrops(Block $$0, Block $$1, float... $$2) {
        return createSilkTouchOrShearsDispatchTable($$0, applyExplosionCondition($$0, LootItem.lootTableItem($$1)).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, $$2))).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(HAS_NO_SHEARS_OR_SILK_TOUCH).add(applyExplosionDecay($$0, LootItem.lootTableItem(Items.STICK).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F))));
    }


    public static LootTable.Builder createDoublePlantShearsDrop(Block $$0) {
        return LootTable.lootTable().withPool(LootPool.lootPool().when(HAS_SHEARS).add(LootItem.lootTableItem($$0).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)))));
    }

    public static LootTable.Builder createDoublePlantWithSeedDrops(Block $$0, Block $$1) {
        LootPoolEntryContainer.Builder<?> $$2 = LootItem.lootTableItem($$1).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))).when(HAS_SHEARS).otherwise(applyExplosionCondition($$0, LootItem.lootTableItem(Items.WHEAT_SEEDS)).when(LootItemRandomChanceCondition.randomChance(0.125F)));
        return LootTable.lootTable().withPool(LootPool.lootPool().add($$2).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties($$0).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER))).when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of($$0).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER).build()).build()), new BlockPos(0, 1, 0)))).withPool(LootPool.lootPool().add($$2).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties($$0).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER))).when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of($$0).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER).build()).build()), new BlockPos(0, -1, 0))));
    }

    public static LootTable.Builder createDoorTable(Block door) {
        return createSinglePropConditionTable(door, DoorBlock.HALF, DoubleBlockHalf.LOWER);
    }


    public void otherWhenSilkTouch(Block block, Block other) {
        this.add(block, createSilkTouchOnlyTable(other));
    }

    public void dropOther(Block block, ItemLike other) {
        this.add(block, createSingleItemTable(other));
    }

    public void dropWhenSilkTouch(Block block) {
        this.otherWhenSilkTouch(block, block);
    }

    public void dropSelf(Block block) {
        this.dropOther(block, block);
    }

    public static LootTable.Builder noDrop() {
        return LootTable.lootTable();
    }


    public void add(Block block, Function<Block, LootTable.Builder> function) {
        this.add(block, function.apply(block));
    }

    public void add(Block block, LootTable.Builder builder) {
        this.map.put(block.getLootTable(), builder);
    }
}