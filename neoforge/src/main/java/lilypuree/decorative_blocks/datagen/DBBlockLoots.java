package lilypuree.decorative_blocks.datagen;

import lilypuree.decorative_blocks.registration.DBBlocks;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class DBBlockLoots extends BlockLootSubProvider {
    protected DBBlockLoots() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        
    }

    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> pOutput) {

        dropSelf(DBBlocks.BAR_PANEL.get());
        dropSelf(DBBlocks.CHANDELIER.get());
        dropSelf(DBBlocks.CHAIN.get());
        dropSelf(DBBlocks.LATTICE.get());
        dropSelf(DBBlocks.BRAZIER.get());
        dropSelf(DBBlocks.STONE_PILLAR.get());
        dropSelf(DBBlocks.ROCKY_DIRT.get());
        dropSelf(DBBlocks.SOUL_BRAZIER.get());
        dropSelf(DBBlocks.SOUL_CHANDELIER.get());
        DBBlocks.BEAMS.values().stream().map(Supplier::get).forEach(this::dropSelf);
        DBBlocks.PALISADES.values().stream().map(Supplier::get).forEach(this::dropSelf);
        DBBlocks.SEATS.values().stream().map(Supplier::get).forEach(this::dropSelf);
        DBBlocks.SUPPORTS.values().stream().map(Supplier::get).forEach(this::dropSelf);

        map.forEach(pOutput);
    }

}
