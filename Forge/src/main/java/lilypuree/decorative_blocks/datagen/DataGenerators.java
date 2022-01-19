package lilypuree.decorative_blocks.datagen;

import lilypuree.decorative_blocks.Constants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        if (event.includeServer()) {
            generator.addProvider(new DBRecipes(generator));
            generator.addProvider(new DBLootTables(generator));
            BlockTagsProvider blockTagsProvider = new DBBlockTagsProvider(generator, Constants.MODID, event.getExistingFileHelper());
            generator.addProvider(blockTagsProvider);
            generator.addProvider(new DBItemTagsProvider(generator, blockTagsProvider, event.getExistingFileHelper()));
        }
        if (event.includeClient()) {
            generator.addProvider(new DBBlockStates(generator, Constants.MODID, event.getExistingFileHelper()));
            generator.addProvider(new DBItemModels(generator, Constants.MODID, event.getExistingFileHelper()));
//            generator.addProvider(new Languages(generator, "en_us"));
        }
    }

}
