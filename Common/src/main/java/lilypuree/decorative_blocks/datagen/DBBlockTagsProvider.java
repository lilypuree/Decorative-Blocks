//package lilypuree.decorative_blocks.datagen;
//
//import com.lilypuree.decorative_blocks.core.DBTags;
//import com.lilypuree.decorative_blocks.core.setup.Registration;
//import com.lilypuree.decorative_blocks.datagen.types.IWoodType;
//import com.lilypuree.decorative_blocks.datagen.types.ModWoodTypes;
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
//        for (IWoodType woodType : ModWoodTypes.allWoodTypes()) {
//            tag(DBTags.Blocks.BEAMS).add(Registration.getBeamBlock(woodType));
//            tag(DBTags.Blocks.PALISADES).add(Registration.getPalisadeBlock(woodType));
//            tag(DBTags.Blocks.SEATS).add(Registration.getSeatBlock(woodType));
//            tag(DBTags.Blocks.SUPPORTS).add(Registration.getSupportBlock(woodType));
//            tag(BlockTags.WALLS).add(Registration.getPalisadeBlock(woodType));
//            if (!woodType.isFlammable()) {
//                tag(BlockTags.NON_FLAMMABLE_WOOD).add(Registration.getBeamBlock(woodType),
//                        Registration.getPalisadeBlock(woodType),
//                        Registration.getSeatBlock(woodType),
//                        Registration.getSupportBlock(woodType));
//            }
//        }
//        tag(DBTags.Blocks.CHANDELIERS).add(Registration.CHANDELIER.get(), Registration.SOUL_CHANDELIER.get());
//    }
//}
