package lilypuree.decorative_blocks.datagen;

import lilypuree.decorative_blocks.Constants;
import lilypuree.decorative_blocks.blocks.types.VanillaWoodTypes;
import lilypuree.decorative_blocks.registration.DBBlocks;
import lilypuree.decorative_blocks.registration.DBTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class DBBlockTagsProvider extends BlockTagsProvider {

    public DBBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        for (WoodType woodType : VanillaWoodTypes.VANILLA) {
            if (!VanillaWoodTypes.isNetherWood(woodType)) {
                if (woodType != WoodType.BAMBOO)
                    tag(DBTags.Blocks.BEAMS_THAT_BURN).add(DBBlocks.BEAMS.get(woodType).get());
                tag(DBTags.Blocks.SEATS_THAT_BURN).add(DBBlocks.SEATS.get(woodType).get());
                tag(DBTags.Blocks.PALISADES_THAT_BURN).add(DBBlocks.PALISADES.get(woodType).get());
                tag(DBTags.Blocks.SUPPORTS_THAT_BURN).add(DBBlocks.SUPPORTS.get(woodType).get());
            } else {
                tag(DBTags.Blocks.BEAMS).add(DBBlocks.BEAMS.get(woodType).get());
                tag(DBTags.Blocks.SEATS).add(DBBlocks.SEATS.get(woodType).get());
                tag(DBTags.Blocks.PALISADES).add(DBBlocks.PALISADES.get(woodType).get());
                tag(DBTags.Blocks.SUPPORTS).add(DBBlocks.SUPPORTS.get(woodType).get());
            }
        }
        tag(DBTags.Blocks.BEAMS).addTag(DBTags.Blocks.BEAMS_THAT_BURN);
        tag(DBTags.Blocks.SUPPORTS).addTag(DBTags.Blocks.SUPPORTS_THAT_BURN);
        tag(DBTags.Blocks.SEATS).addTag(DBTags.Blocks.SEATS_THAT_BURN);
        tag(DBTags.Blocks.PALISADES).addTag(DBTags.Blocks.PALISADES_THAT_BURN);
        tag(BlockTags.WALLS).addTag(DBTags.Blocks.PALISADES);
        tag(DBTags.Blocks.CHANDELIERS).add(DBBlocks.CHANDELIER.get(), DBBlocks.SOUL_CHANDELIER.get());
    }
}
