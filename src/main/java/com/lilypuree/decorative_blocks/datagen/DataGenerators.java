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
            String modid = DecorativeBlocks.MODID;
            generator.addProvider(new BlockStates(generator, modid, event.getExistingFileHelper()));
            generator.addProvider(new Items(generator, modid, event.getExistingFileHelper()));
            generator.addProvider(new Languages(generator, modid,"en_us"));
        }
    }

}
