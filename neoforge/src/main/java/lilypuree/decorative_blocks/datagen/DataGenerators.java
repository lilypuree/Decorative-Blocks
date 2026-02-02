package lilypuree.decorative_blocks.datagen;

import lilypuree.decorative_blocks.Constants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        generator.addProvider(event.includeServer(), new DBRecipes(packOutput, lookupProvider));
        generator.addProvider(event.includeServer(), new DBLootTables(packOutput, lookupProvider));
        BlockTagsProvider blockTagsProvider = new DBBlockTagsProvider(packOutput, lookupProvider, existingFileHelper);
        generator.addProvider(event.includeServer(), blockTagsProvider);
        generator.addProvider(event.includeServer(), new DBItemTagsProvider(packOutput, lookupProvider, blockTagsProvider.contentsGetter(), existingFileHelper));
        generator.addProvider(event.includeClient(), new DBBlockStates(packOutput, Constants.MOD_ID, existingFileHelper));
        generator.addProvider(event.includeClient(), new DBItemModels(packOutput, Constants.MOD_ID, existingFileHelper));
//        generator.addProvider(event.includeClient(), new Languages(generator, "en_us"));
    }

}
