package lilypuree.decorative_blocks.datagen;

<<<<<<< Updated upstream
import lilypuree.decorative_blocks.core.DBBlocks;
import lilypuree.decorative_blocks.registration.RegistryObject;
=======
import lilypuree.decorative_blocks.registration.BlockWrapper;
import lilypuree.decorative_blocks.registration.DBBlocks;
>>>>>>> Stashed changes
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.Set;
import java.util.function.BiConsumer;

public class DBBlockLoots extends BlockLootSubProvider {
    protected DBBlockLoots() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
<<<<<<< Updated upstream
        
=======

>>>>>>> Stashed changes
    }

    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> pOutput) {

        dropSelf(DBBlocks.BAR_PANEL.get());
        dropSelf(DBBlocks.CHANDELIER.get());
        dropSelf(DBBlocks.CHAIN.get());
        dropSelf(DBBlocks.LATTICE.get());
        dropSelf(DBBlocks.BRAZIER.get());
<<<<<<< Updated upstream
        dropSelf(DBBlocks.STONE_PILLAR.get());
        dropSelf(DBBlocks.ROCKY_DIRT.get());
        dropSelf(DBBlocks.SOUL_BRAZIER.get());
        dropSelf(DBBlocks.SOUL_CHANDELIER.get());
        DBBlocks.BEAMS.values().stream().map(RegistryObject::get).forEach(this::dropSelf);
        DBBlocks.PALISADES.values().stream().map(RegistryObject::get).forEach(this::dropSelf);
        DBBlocks.SEATS.values().stream().map(RegistryObject::get).forEach(this::dropSelf);
        DBBlocks.SUPPORTS.values().stream().map(RegistryObject::get).forEach(this::dropSelf);
=======
        dropSelf(DBBlocks.TABLE_POT.get());
        dropSelf(DBBlocks.ROPE_COIL.get());
        dropSelf(DBBlocks.STEP_LADDER.get());

        dropSelf(DBBlocks.STONE_PILLAR.get());
        dropSelf(DBBlocks.SMOOTH_STONE_PILLAR.get());
        dropSelf(DBBlocks.SANDSTONE_PILLAR.get());
        dropSelf(DBBlocks.RED_SANDSTONE_PILLAR.get());
        dropSelf(DBBlocks.BLACKSTONE_PILLAR.get());
        dropSelf(DBBlocks.BASALT_PILLAR.get());
        dropSelf(DBBlocks.TUFF_PILLAR.get());
        dropSelf(DBBlocks.MUD_PILLAR.get());

        dropSelf(DBBlocks.ROCKY_DIRT.get());
        dropSelf(DBBlocks.SOUL_BRAZIER.get());
        dropSelf(DBBlocks.SOUL_CHANDELIER.get());
        DBBlocks.BEAMS.values().stream().map(BlockWrapper::get).forEach(this::dropSelf);
        DBBlocks.PALISADES.values().stream().map(BlockWrapper::get).forEach(this::dropSelf);
        DBBlocks.SEATS.values().stream().map(BlockWrapper::get).forEach(this::dropSelf);
        DBBlocks.SUPPORTS.values().stream().map(BlockWrapper::get).forEach(this::dropSelf);
>>>>>>> Stashed changes

        map.forEach(pOutput);
    }

}
