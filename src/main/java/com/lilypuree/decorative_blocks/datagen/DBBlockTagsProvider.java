package com.lilypuree.decorative_blocks.datagen;

import com.lilypuree.decorative_blocks.core.DBTags;
import com.lilypuree.decorative_blocks.core.setup.Registration;
import com.lilypuree.decorative_blocks.datagen.types.IWoodType;
import com.lilypuree.decorative_blocks.datagen.types.ModWoodTypes;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class DBBlockTagsProvider extends BlockTagsProvider {
    public DBBlockTagsProvider(DataGenerator generatorIn, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(generatorIn, modId, existingFileHelper);
    }

    @Override
    protected void registerTags() {
        for (IWoodType woodType : ModWoodTypes.allWoodTypes()) {
            getOrCreateBuilder(DBTags.Blocks.BEAMS).add(Registration.getBeamBlock(woodType));
            getOrCreateBuilder(DBTags.Blocks.PALISADES).add(Registration.getPalisadeBlock(woodType));
            getOrCreateBuilder(DBTags.Blocks.SEATS).add(Registration.getSeatBlock(woodType));
            getOrCreateBuilder(DBTags.Blocks.SUPPORTS).add(Registration.getSupportBlock(woodType));
            getOrCreateBuilder(BlockTags.WALLS).add(Registration.getPalisadeBlock(woodType));
            if (!woodType.isFlammable()) {
                getOrCreateBuilder(BlockTags.NON_FLAMMABLE_WOOD).add(Registration.getBeamBlock(woodType),
                        Registration.getPalisadeBlock(woodType),
                        Registration.getSeatBlock(woodType),
                        Registration.getSupportBlock(woodType));
            }
        }
        getOrCreateBuilder(DBTags.Blocks.CHANDELIERS).add(Registration.CHANDELIER.get(), Registration.SOUL_CHANDELIER.get());
    }
}
