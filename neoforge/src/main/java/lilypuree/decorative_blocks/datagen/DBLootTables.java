package lilypuree.decorative_blocks.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;

public class DBLootTables extends LootTableProvider {

    public DBLootTables(PackOutput pOutput) {
        super(pOutput, Set.of(), List.of(new LootTableProvider.SubProviderEntry(DBBlockLoots::new, LootContextParamSets.BLOCK)));
    }

}
