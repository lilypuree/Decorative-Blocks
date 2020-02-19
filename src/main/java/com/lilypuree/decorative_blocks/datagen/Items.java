package com.lilypuree.decorative_blocks.datagen;

import com.lilypuree.decorative_blocks.datagen.types.BOPWoodTypes;
import com.lilypuree.decorative_blocks.datagen.types.IWoodType;
import com.lilypuree.decorative_blocks.datagen.types.WoodTypes;
import com.lilypuree.decorative_blocks.setup.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.fml.ModList;

public class Items extends ItemModelProvider {

    public Items(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
        super(generator, modid, existingFileHelper);
    }


    @Override
    protected void registerModels() {
        for(IWoodType wood : WoodTypes.values()){
            getBuilder(wood+"_beam").parent(new ModelFile.UncheckedModelFile(modLoc("block/"+wood+"_beam_y")));
            getBuilder(wood+"_palisade").parent(new ModelFile.UncheckedModelFile(modLoc("block/"+wood+"_palisade_inventory")));
            getBuilder(wood+"_seat").parent(new ModelFile.UncheckedModelFile(modLoc("block/"+wood+"_seat")));
            getBuilder(wood+"_support").parent(new ModelFile.UncheckedModelFile(modLoc("block/"+wood+"_support")));
        }

        if(ModList.get().isLoaded("biomesoplenty")){
            for(IWoodType wood : BOPWoodTypes.values()){
                getBuilder(wood+"_beam").parent(new ModelFile.UncheckedModelFile(modLoc("block/"+wood+"_beam_y")));
                getBuilder(wood+"_palisade").parent(new ModelFile.UncheckedModelFile(modLoc("block/"+wood+"_palisade_inventory")));
                getBuilder(wood+"_seat").parent(new ModelFile.UncheckedModelFile(modLoc("block/"+wood+"_seat")));
                getBuilder(wood+"_support").parent(new ModelFile.UncheckedModelFile(modLoc("block/"+wood+"_support")));
            }
        }
    }


    @Override
    public String getName() {
        return "Decorative Block Item Models";
    }
}
