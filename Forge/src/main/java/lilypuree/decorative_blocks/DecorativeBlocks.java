package lilypuree.decorative_blocks;

import lilypuree.decorative_blocks.registration.DBBlocks;
import lilypuree.decorative_blocks.registration.DBItems;
import lilypuree.decorative_blocks.registration.DBNames;
import lilypuree.decorative_blocks.registration.DBTags;
import lilypuree.decorative_blocks.registration.Registration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fml.common.Mod;
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
        
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener((FMLCommonSetupEvent e) -> {
            DecorativeBlocksCommon.init();
        });
        
 

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
