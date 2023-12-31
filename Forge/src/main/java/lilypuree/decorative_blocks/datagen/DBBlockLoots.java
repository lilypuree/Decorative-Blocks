//package lilypuree.decorative_blocks.datagen;
//
//import lilypuree.decorative_blocks.core.DBBlocks;
//import lilypuree.decorative_blocks.registration.RegistryObject;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.level.storage.loot.LootTable;
//
//import java.util.function.BiConsumer;
//
//public class DBBlockLoots extends BlockLootTableAccessor {
//    @Override
//    public void accept(BiConsumer<ResourceLocation, LootTable.Builder> biConsumer) {
//        dropSelf(DBBlocks.BAR_PANEL.get());
//        dropSelf(DBBlocks.CHANDELIER.get());
//        dropSelf(DBBlocks.CHAIN.get());
//        dropSelf(DBBlocks.LATTICE.get());
//        dropSelf(DBBlocks.BRAZIER.get());
//        dropSelf(DBBlocks.STONE_PILLAR.get());
//        dropSelf(DBBlocks.ROCKY_DIRT.get());
//        dropSelf(DBBlocks.SOUL_BRAZIER.get());
//        dropSelf(DBBlocks.SOUL_CHANDELIER.get());
//        DBBlocks.BEAMS.values().stream().map(RegistryObject::get).forEach(this::dropSelf);
//        DBBlocks.PALISADES.values().stream().map(RegistryObject::get).forEach(this::dropSelf);
//        DBBlocks.SEATS.values().stream().map(RegistryObject::get).forEach(this::dropSelf);
//        DBBlocks.SUPPORTS.values().stream().map(RegistryObject::get).forEach(this::dropSelf);
//        map.forEach(biConsumer);
//    }
//}
