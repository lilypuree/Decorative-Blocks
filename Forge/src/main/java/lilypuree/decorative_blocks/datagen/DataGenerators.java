package lilypuree.decorative_blocks.datagen;

import lilypuree.decorative_blocks.Constants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        generator.addProvider(event.includeServer(), new DBRecipes(generator));
        generator.addProvider(event.includeServer(), new DBLootTables(generator));
        BlockTagsProvider blockTagsProvider = new DBBlockTagsProvider(generator, Constants.MODID, event.getExistingFileHelper());
        generator.addProvider(event.includeServer(), blockTagsProvider);
        generator.addProvider(event.includeServer(), new DBItemTagsProvider(generator, blockTagsProvider, event.getExistingFileHelper()));
        generator.addProvider(event.includeClient(), new DBBlockStates(generator, Constants.MODID, event.getExistingFileHelper()));
        generator.addProvider(event.includeClient(), new DBItemModels(generator, Constants.MODID, event.getExistingFileHelper()));
//        generator.addProvider(event.includeClient(), new Languages(generator, "en_us"));
    }

}
