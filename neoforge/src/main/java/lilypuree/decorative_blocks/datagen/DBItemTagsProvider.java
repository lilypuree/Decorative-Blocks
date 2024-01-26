package lilypuree.decorative_blocks.datagen;

import lilypuree.decorative_blocks.Constants;
import lilypuree.decorative_blocks.registration.DBTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class DBItemTagsProvider extends ItemTagsProvider {

    public DBItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, pBlockTags, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        copy(DBTags.Blocks.BEAMS, DBTags.Items.BEAMS);
        copy(DBTags.Blocks.PALISADES, DBTags.Items.PALISADES);
        copy(DBTags.Blocks.SEATS, DBTags.Items.SEATS);
        copy(DBTags.Blocks.SUPPORTS, DBTags.Items.SUPPORTS);
        copy(DBTags.Blocks.BEAMS_THAT_BURN, DBTags.Items.BEAMS_THAT_BURN);
        copy(DBTags.Blocks.PALISADES_THAT_BURN, DBTags.Items.PALISADES_THAT_BURN);
        copy(DBTags.Blocks.SEATS_THAT_BURN, DBTags.Items.SEATS_THAT_BURN);
        copy(DBTags.Blocks.SUPPORTS_THAT_BURN, DBTags.Items.SUPPORTS_THAT_BURN);
        copy(DBTags.Blocks.CHANDELIERS, DBTags.Items.CHANDELIERS);
    }
}
