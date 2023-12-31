package lilypuree.decorative_blocks;


import lilypuree.decorative_blocks.core.DBBlocks;
import lilypuree.decorative_blocks.core.DBItems;
import lilypuree.decorative_blocks.core.DBTags;
import lilypuree.decorative_blocks.core.Registration;
import net.fabricmc.api.ModInitializer;

public class DecorativeBlocks implements ModInitializer {

    @Override
    public void onInitialize() {
        DBTags.init();
        Registration.init();
        DBBlocks.init();
        DBItems.init();
        FuelRegistration.init();
        DecorativeBlocksCommon.init();
        FabricCallbacks.initCallbacks();
    }
}
