package com.lilypuree.decorative_blocks.datagen;

import com.lilypuree.decorative_blocks.DecorativeBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event){
        DataGenerator generator = event.getGenerator();
        if (event.includeServer()) {
            generator.addProvider(new Recipes(generator));
            generator.addProvider(new LootTables(generator));

        }
        if (event.includeClient()) {

            generator.addProvider(new BlockStates(generator, DecorativeBlocks.MODID, event.getExistingFileHelper()));
            generator.addProvider(new Items(generator, DecorativeBlocks.MODID, event.getExistingFileHelper()));
            generator.addProvider(new Languages(generator, "en_us"));
        }
    }

}
