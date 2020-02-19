package com.lilypuree.decorative_blocks.datagen;

import com.lilypuree.decorative_blocks.DecorativeBlocks;
import com.lilypuree.decorative_blocks.datagen.types.BOPWoodTypes;
import com.lilypuree.decorative_blocks.datagen.types.IWoodType;
import com.lilypuree.decorative_blocks.setup.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.fml.ModList;
import org.apache.commons.lang3.StringUtils;

public class Languages extends LanguageProvider {

    public Languages(DataGenerator gen, String locale) {
        super(gen, DecorativeBlocks.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        if (ModList.get().isLoaded("biomesoplenty")) {
            for (IWoodType wood : BOPWoodTypes.values()) {
                add(Registration.getBeamBlock(wood).asItem(), cap(wood.toString()) + " Beam");
                add(Registration.getPalisadeBlock(wood).asItem(), cap(wood.toString()) + " Palisade");
                add(Registration.getSeatBlock(wood).asItem(), cap(wood.toString()) + " Seat");
                add(Registration.getSupportBlock(wood).asItem(), cap(wood.toString()) + " Support");
            }
        }
    }

    private String cap(String string) {
        return StringUtils.capitalize(string);
    }
}
