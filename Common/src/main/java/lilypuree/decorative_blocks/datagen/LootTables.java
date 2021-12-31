//package lilypuree.decorative_blocks.datagen;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.lilypuree.decorative_blocks.core.setup.Registration;
//import com.lilypuree.decorative_blocks.datagen.types.IWoodType;
//import com.lilypuree.decorative_blocks.datagen.types.ModWoodTypes;
//import com.lilypuree.decorative_blocks.datagen.types.WoodDecorativeBlockTypes;
//import net.minecraft.data.DataGenerator;
//import net.minecraft.data.DataProvider;
//import net.minecraft.data.HashCache;
//import net.minecraft.data.loot.LootTableProvider;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.level.block.Block;
//import net.minecraft.world.level.storage.loot.LootTable;
//import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
//
//import java.io.IOException;
//import java.nio.file.Path;
//import java.util.HashMap;
//import java.util.Map;
//
//public class LootTables extends LootTableProvider {
//    private final DataGenerator generator;
//    protected final Map<Block, LootTable.Builder> lootTables = new HashMap<>();
//    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
//
//    public LootTables(DataGenerator dataGeneratorIn) {
//        super(dataGeneratorIn);
//        this.generator = dataGeneratorIn;
//    }
//
//    @Override
//    public void run(HashCache cache) {
//
//        addBlockLoot(Registration.BAR_PANEL.get());
//        addBlockLoot(Registration.CHANDELIER.get());
//        addBlockLoot(Registration.CHAIN.get());
//        addBlockLoot(Registration.LATTICE.get());
//        addBlockLoot(Registration.BRAZIER.get());
//        addBlockLoot(Registration.STONE_PILLAR.get());
//        addBlockLoot(Registration.ROCKY_DIRT.get());
//        addBlockLoot(Registration.SOUL_BRAZIER.get());
//        addBlockLoot(Registration.SOUL_CHANDELIER.get());
//
//        for (IWoodType wood : ModWoodTypes.allWoodTypes()){
//            for (WoodDecorativeBlockTypes type : WoodDecorativeBlockTypes.values()){
//                addBlockLoot(Registration.getWoodDecorativeBlock(wood,type));
//            }
//        }
//
//        Map<ResourceLocation, LootTable> tables = new HashMap<>();
//        for (Map.Entry<Block, LootTable.Builder> entry : lootTables.entrySet()) {
//            tables.put(entry.getKey().getLootTable(), entry.getValue().setParamSet(LootContextParamSets.BLOCK).build());
//        }
//        writeTables(cache, tables);
//    }
//
//    public void writeTables(HashCache cache, Map<ResourceLocation, LootTable> tables) {
//        Path outputFolder = this.generator.getOutputFolder();
//        tables.forEach((key, lootTable) -> {
//            Path path = outputFolder.resolve("data/" + key.getNamespace() + "/loot_tables/" + key.getPath() + ".json");
//            try {
//                DataProvider.save(GSON, cache, LootTables.serialize(lootTable), path);
//            } catch (IOException e) {
//                e.printStackTrace();
////                LOGGER.error("Couldn't write loot table {}", path, e);
//            }
//        });
//    }
//
////    protected LootTable.Builder createSimpleTable(String name, Block block) {
////        LootPool.Builder builder = LootPool.builder()
////                .name(name)
////                .rolls(ConstantRange.of(1))
////                .addEntry(ItemLootEntry.builder(block));
////        return LootTable.builder().addLootPool(builder);
////    }
//
//    public void addBlockLoot(Block block) {
//        lootTables.put(block, BlockLootTableAccessor.dropping(block));
//    }
//
//}
