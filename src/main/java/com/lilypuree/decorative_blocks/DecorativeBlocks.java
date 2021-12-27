package com.lilypuree.decorative_blocks;

import com.lilypuree.decorative_blocks.core.DBTags;
import com.lilypuree.decorative_blocks.core.setup.ClientSetup;
import com.lilypuree.decorative_blocks.core.setup.ModSetup;
import com.lilypuree.decorative_blocks.core.setup.Registration;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(DecorativeBlocks.MODID)
public class DecorativeBlocks {
    public static final String MODID = "decorative_blocks";

    public static ModSetup setup = new ModSetup();
    public static DecorativeBlocks instance;


    public DecorativeBlocks() {
        instance = this;
        DBTags.init();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);
        Registration.register();

        FMLJavaModLoadingContext.get().getModEventBus().addListener((FMLCommonSetupEvent e) -> setup.init(e));
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::init);

    }
}
