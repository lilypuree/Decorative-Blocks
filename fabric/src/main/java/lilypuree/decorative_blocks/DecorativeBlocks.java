package lilypuree.decorative_blocks;


import lilypuree.decorative_blocks.registration.DBBlocks;
import lilypuree.decorative_blocks.registration.DBItems;
import lilypuree.decorative_blocks.registration.DBTags;
import lilypuree.decorative_blocks.registration.Registration;
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
