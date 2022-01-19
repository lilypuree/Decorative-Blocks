package lilypuree.decorative_blocks.datagen;

import lilypuree.decorative_blocks.core.DBBlocks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.function.BiConsumer;

public class DBBlockLoots extends BlockLootTableAccessor {
    @Override
    public void accept(BiConsumer<ResourceLocation, LootTable.Builder> biConsumer) {
        dropSelf(DBBlocks.BAR_PANEL);
        dropSelf(DBBlocks.CHANDELIER);
        dropSelf(DBBlocks.CHAIN);
        dropSelf(DBBlocks.LATTICE);
        dropSelf(DBBlocks.BRAZIER);
        dropSelf(DBBlocks.STONE_PILLAR);
        dropSelf(DBBlocks.ROCKY_DIRT);
        dropSelf(DBBlocks.SOUL_BRAZIER);
        dropSelf(DBBlocks.SOUL_CHANDELIER);
        DBBlocks.BEAMS.values().forEach(this::dropSelf);
        DBBlocks.PALISADES.values().forEach(this::dropSelf);
        DBBlocks.SEATS.values().forEach(this::dropSelf);
        DBBlocks.SUPPORTS.values().forEach(this::dropSelf);
        map.forEach(biConsumer);
    }
}
