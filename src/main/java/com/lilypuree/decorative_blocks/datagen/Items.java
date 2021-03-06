package com.lilypuree.decorative_blocks.datagen;

import com.lilypuree.decorative_blocks.datagen.types.IWoodType;
import com.lilypuree.decorative_blocks.datagen.types.ModWoodTypes;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class Items extends ItemModelProvider {

    public Items(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
        super(generator, modid, existingFileHelper);
    }


    @Override
    protected void registerModels() {
        for (IWoodType wood : ModWoodTypes.allWoodTypes()){
            getBuilder(wood+"_beam").parent(new ModelFile.UncheckedModelFile(modLoc("block/"+wood+"_beam_y")));
            getBuilder(wood+"_palisade").parent(new ModelFile.UncheckedModelFile(modLoc("block/"+wood+"_palisade_inventory")));
            getBuilder(wood+"_seat").parent(new ModelFile.UncheckedModelFile(modLoc("block/"+wood+"_seat_inventory")));
            getBuilder(wood+"_support").parent(new ModelFile.UncheckedModelFile(modLoc("block/"+wood+"_support")));
        }
    }


    @Override
    public String getName() {
        return "Decorative Block Item Models";
    }
}
