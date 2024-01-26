package lilypuree.decorative_blocks.datagen;

import lilypuree.decorative_blocks.Constants;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class Languages extends LanguageProvider {

    public Languages(PackOutput output, String locale) {
        super(output, Constants.MOD_ID, locale);
    }

    @Override
    protected void addTranslations() {

    }
}
