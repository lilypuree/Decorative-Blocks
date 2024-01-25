package lilypuree.decorative_blocks.datagen;

import lilypuree.decorative_blocks.Constants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        
        generator.addProvider(event.includeServer(), new DBRecipes(packOutput));
        generator.addProvider(event.includeServer(), new DBLootTables(packOutput));
        BlockTagsProvider blockTagsProvider = new DBBlockTagsProvider(packOutput, lookupProvider, existingFileHelper);
        generator.addProvider(event.includeServer(), blockTagsProvider);
        generator.addProvider(event.includeServer(), new DBItemTagsProvider(packOutput, lookupProvider, blockTagsProvider.contentsGetter(), existingFileHelper));
        generator.addProvider(event.includeClient(), new DBBlockStates(packOutput, Constants.MOD_ID, existingFileHelper));
        generator.addProvider(event.includeClient(), new DBItemModels(packOutput, Constants.MOD_ID, existingFileHelper));
//        generator.addProvider(event.includeClient(), new Languages(generator, "en_us"));
    }

}
