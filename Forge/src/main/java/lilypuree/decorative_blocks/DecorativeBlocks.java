package lilypuree.decorative_blocks;

import lilypuree.decorative_blocks.core.*;
import lilypuree.decorative_blocks.events.ClientEventHandler;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegisterEvent;

@Mod(Constants.MOD_ID)
public class DecorativeBlocks {

    public DecorativeBlocks() {
        DBTags.init();
        Registration.init();
        DBBlocks.init();
        DBItems.init();

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ForgeConfig.COMMON_CONFIG);

        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.register(ForgeConfig.INSTANCE);
        modBus.addListener((FMLCommonSetupEvent e) -> {
            DecorativeBlocksCommon.init();
        });
        
        modBus.addListener(ClientEventHandler::clientSetup);
        modBus.addListener(ClientEventHandler::onEntityRendererRegistry);

        modBus.addListener(this::onRegisterEvent);
    }

    private void onRegisterEvent(RegisterEvent event) {
        if (event.getRegistryKey() == ForgeRegistries.FLUID_TYPES.get().getRegistryKey()) {
            registerFluidTypes(event.getForgeRegistry());
        }
    }


    private void registerFluidTypes(IForgeRegistry<FluidType> registry) {
        registry.register(DBNames.STILL_THATCH, Registration.STILL_THATCH.get().getFluidType());
        registry.register(DBNames.FLOWING_THATCH, Registration.FLOWING_THATCH.get().getFluidType());
    }
}
