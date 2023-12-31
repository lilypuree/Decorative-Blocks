//package lilypuree.decorative_blocks.datagen;
//
//import lilypuree.decorative_blocks.core.DBBlocks;
//import lilypuree.decorative_blocks.core.DBTags;
//import lilypuree.decorative_blocks.blocks.types.IWoodType;
//import lilypuree.decorative_blocks.blocks.types.VanillaWoodTypes;
//import net.minecraft.data.DataGenerator;
//import net.minecraft.data.tags.BlockTagsProvider;
//import net.minecraft.tags.BlockTags;
//import net.minecraftforge.common.data.ExistingFileHelper;
//
//import javax.annotation.Nullable;
//
//public class DBBlockTagsProvider extends BlockTagsProvider {
//    public DBBlockTagsProvider(DataGenerator generatorIn, String modId, @Nullable ExistingFileHelper existingFileHelper) {
//        super(generatorIn, modId, existingFileHelper);
//    }
//
//    @Override
//    protected void addTags() {
//        for (IWoodType woodType : VanillaWoodTypes.values()) {
//            tag(DBTags.Blocks.BEAMS).add(DBBlocks.BEAMS.get(woodType).get());
//            tag(DBTags.Blocks.PALISADES).add(DBBlocks.PALISADES.get(woodType).get());
//            tag(DBTags.Blocks.SEATS).add(DBBlocks.SEATS.get(woodType).get());
//            tag(DBTags.Blocks.SUPPORTS).add(DBBlocks.SUPPORTS.get(woodType).get());
//            if (!woodType.isFlammable()) {
//                tag(BlockTags.NON_FLAMMABLE_WOOD).add(DBBlocks.BEAMS.get(woodType).get(),
//                        DBBlocks.PALISADES.get(woodType).get(),
//                        DBBlocks.SEATS.get(woodType).get(),
//                        DBBlocks.SUPPORTS.get(woodType).get());
//            }
//        }
//        tag(BlockTags.WALLS).addTag(DBTags.Blocks.PALISADES);
//
//        tag(DBTags.Blocks.CHANDELIERS).add(DBBlocks.CHANDELIER.get(), DBBlocks.SOUL_CHANDELIER.get());
//    }
//}
