package com.lilypuree.decorative_blocks.datagen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lilypuree.decorative_blocks.datagen.types.WoodTypes;
import com.lilypuree.decorative_blocks.setup.Registration;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.data.LootTableProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.*;
import net.minecraft.world.storage.loot.functions.CopyName;
import net.minecraft.world.storage.loot.functions.CopyNbt;
import net.minecraft.world.storage.loot.functions.SetContents;

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
        lootTables.put(Registration.CHANDELIER.get(), createSimpleTable("chandelier", Registration.CHANDELIER.get()));
        lootTables.put(Registration.CHAIN.get(), createSimpleTable("chain", Registration.CHAIN.get()));
        lootTables.put(Registration.BRAZIER.get(), createSimpleTable("brazier", Registration.BRAZIER.get()));
        lootTables.put(Registration.STONE_PILLAR.get(), createSimpleTable("stone_pillar", Registration.STONE_PILLAR.get()));
        lootTables.put(Registration.ROCKY_DIRT.get(), createSimpleTable("rocky_dirt", Registration.ROCKY_DIRT.get()));

        for (WoodTypes wood : WoodTypes.values()){
            lootTables.put(Registration.getBeamBlock(wood), createSimpleTable(wood+"_beam", Registration.getBeamBlock(wood)));
            lootTables.put(Registration.getPalisadeBlock(wood), createSimpleTable(wood+"_palisade", Registration.getPalisadeBlock(wood)));
            lootTables.put(Registration.getSeatBlock(wood), createSimpleTable(wood+"_seat", Registration.getSeatBlock(wood)));
            lootTables.put(Registration.getSupportBlock(wood), createSimpleTable(wood+"_support", Registration.getSupportBlock(wood)));
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
