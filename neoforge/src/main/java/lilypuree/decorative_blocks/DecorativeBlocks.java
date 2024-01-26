package lilypuree.decorative_blocks;

import lilypuree.decorative_blocks.platform.NeoForgePlatformHelper;
import lilypuree.decorative_blocks.registration.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(Constants.MOD_ID)
public class DecorativeBlocks {

    public DecorativeBlocks(IEventBus modBus) {
        DBTags.init();
        Registration.init();
        DBBlocks.init();
        DBItems.init();
        NeoForgePlatformHelper.registries.values().forEach(reg -> reg.register(modBus));
        modBus.addListener((FMLCommonSetupEvent e) -> {
            DecorativeBlocksCommon.init();
        });
        modBus.addListener(this::onRegisterEvent);
    }

    private void onRegisterEvent(RegisterEvent event) {
        if (event.getRegistryKey() == NeoForgeRegistries.FLUID_TYPES.key()) {
            event.register(NeoForgeRegistries.FLUID_TYPES.key(), helper -> helper.register(DBNames.STILL_THATCH, Registration.STILL_THATCH.get().getFluidType()));
            event.register(NeoForgeRegistries.FLUID_TYPES.key(), helper -> helper.register(DBNames.FLOWING_THATCH, Registration.FLOWING_THATCH.get().getFluidType()));
        }
    }
}
