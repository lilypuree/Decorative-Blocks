package com.lilypuree.decorative_blocks.datagen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lilypuree.decorative_blocks.datagen.types.IWoodType;
import com.lilypuree.decorative_blocks.datagen.types.ModWoodTypes;
import com.lilypuree.decorative_blocks.datagen.types.WoodDecorativeBlockTypes;
import com.lilypuree.decorative_blocks.setup.Registration;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.data.LootTableProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class LootTables extends LootTableProvider {
    private final DataGenerator generator;
    protected final Map<Block, LootTable.Builder> lootTables = new HashMap<>();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    public LootTables(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
        this.generator = dataGeneratorIn;
    }

    @Override
    public void act(DirectoryCache cache) {

        lootTables.put(Registration.BAR_PANEL.get(), createSimpleTable("bar_panel", Registration.BAR_PANEL.get()));
        lootTables.put(Registration.LATTICE.get(), createSimpleTable("lattice", Registration.LATTICE.get()));
        lootTables.put(Registration.CHANDELIER.get(), createSimpleTable("chandelier", Registration.CHANDELIER.get()));
        lootTables.put(Registration.CHAIN.get(), createSimpleTable("chain", Registration.CHAIN.get()));
        lootTables.put(Registration.BRAZIER.get(), createSimpleTable("brazier", Registration.BRAZIER.get()));
        lootTables.put(Registration.STONE_PILLAR.get(), createSimpleTable("stone_pillar", Registration.STONE_PILLAR.get()));
        lootTables.put(Registration.ROCKY_DIRT.get(), createSimpleTable("rocky_dirt", Registration.ROCKY_DIRT.get()));

        for (IWoodType wood : ModWoodTypes.allWoodTypes()){
            for (WoodDecorativeBlockTypes type : WoodDecorativeBlockTypes.values()){
                lootTables.put(Registration.getWoodDecorativeBlock(wood, type), createSimpleTable(wood+"_"+type, Registration.getWoodDecorativeBlock(wood,type)));
            }
        }

        Map<ResourceLocation, LootTable> tables = new HashMap<>();
        for (Map.Entry<Block, LootTable.Builder> entry : lootTables.entrySet()) {
            tables.put(entry.getKey().getLootTable(), entry.getValue().setParameterSet(LootParameterSets.BLOCK).build());
        }
        writeTables(cache, tables);
    }

    private void writeTables(DirectoryCache cache, Map<ResourceLocation, LootTable> tables) {
        Path outputFolder = this.generator.getOutputFolder();
        tables.forEach((key, lootTable) -> {
            Path path = outputFolder.resolve("data/" + key.getNamespace() + "/loot_tables/" + key.getPath() + ".json");
            try {
                IDataProvider.save(GSON, cache, LootTableManager.toJson(lootTable), path);
            } catch (IOException e) {
                e.printStackTrace();
//                LOGGER.error("Couldn't write loot table {}", path, e);
            }
        });
    }

    protected LootTable.Builder createSimpleTable(String name, Block block) {
        LootPool.Builder builder = LootPool.builder()
                .name(name)
                .rolls(ConstantRange.of(1))
                .addEntry(ItemLootEntry.builder(block));
        return LootTable.builder().addLootPool(builder);
    }
}
