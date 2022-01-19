package lilypuree.decorative_blocks.datagen;

import lilypuree.decorative_blocks.Constants;
import lilypuree.decorative_blocks.core.DBTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class DBItemTagsProvider extends ItemTagsProvider {
    public DBItemTagsProvider(DataGenerator generatorIn, BlockTagsProvider blockTagsProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(generatorIn, blockTagsProvider, Constants.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        copy(DBTags.Blocks.BEAMS, DBTags.Items.BEAMS);
        copy(DBTags.Blocks.PALISADES, DBTags.Items.PALISADES);
        copy(DBTags.Blocks.SEATS, DBTags.Items.SEATS);
        copy(DBTags.Blocks.SUPPORTS, DBTags.Items.SUPPORTS);
        copy(DBTags.Blocks.CHANDELIERS, DBTags.Items.CHANDELIERS);

        copy(BlockTags.NON_FLAMMABLE_WOOD, ItemTags.NON_FLAMMABLE_WOOD);
    }
}
