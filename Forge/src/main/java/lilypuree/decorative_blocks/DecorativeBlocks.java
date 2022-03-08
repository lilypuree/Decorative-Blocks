package lilypuree.decorative_blocks;

import lilypuree.decorative_blocks.core.*;
import lilypuree.decorative_blocks.core.setup.ModSetup;
import lilypuree.decorative_blocks.entity.DummyEntityForSitting;
import lilypuree.decorative_blocks.events.ClientEventHandler;
import lilypuree.decorative_blocks.fluid.ForgeThatchFluid;
import lilypuree.decorative_blocks.fluid.ForgeThatchFluidBlock;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

@Mod(Constants.MODID)
public class DecorativeBlocks {

    public DecorativeBlocks() {
        DBTags.init();
        Constants.ITEM_GROUP = new CreativeModeTab(-1, Constants.MODID + ".general") {
            @Override
            public ItemStack makeIcon() {
                return new ItemStack(DBItems.BRAZIER);
            }
        };
        DummyEntityForSitting.factory = (type, level) -> new DummyEntityForSitting(type, level) {
            @Override
            public Packet<?> getAddEntityPacket() {
                return NetworkHooks.getEntitySpawningPacket(this);
            }
        };

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);

        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener((FMLCommonSetupEvent e) -> {
            ModSetup.init();
        });
        modBus.addListener(ClientEventHandler::initRenderLayers);
        modBus.addListener(ClientEventHandler::registerItemFunc);
        modBus.addListener(ClientEventHandler::onEntityRendererRegistry);

        modBus.addGenericListener(Block.class, this::registerBlocks);
        modBus.addGenericListener(Item.class, (RegistryEvent.Register<Item> e) -> Registration.registerItems(new RegistryHelperForge<>(e.getRegistry())));
        modBus.addGenericListener(Fluid.class, this::registerFluids);
        modBus.addGenericListener(EntityType.class, this::registerEntities);
    }

    public void registerBlocks(RegistryEvent.Register<Block> event) {
        Registration.THATCH = new ForgeThatchFluidBlock(() -> Registration.STILL_THATCH, Registration.thatchProperties);
        Registration.registerBlocks(new RegistryHelperForge<>(event.getRegistry()));
    }

    public void registerFluids(RegistryEvent.Register<Fluid> event) {
        Registration.STILL_THATCH = new ForgeThatchFluid.Source(Registration.referenceHolder);
        Registration.FLOWING_THATCH = new ForgeThatchFluid.Flowing(Registration.referenceHolder);
        Registration.registerFluids(new RegistryHelperForge<>(event.getRegistry()));
    }

    public void registerEntities(RegistryEvent.Register<EntityType<?>> event) {
        event.getRegistry().register(Registration.DUMMY_ENTITY_TYPE = (EntityType<DummyEntityForSitting>) EntityType.Builder.of(DummyEntityForSitting.factory, MobCategory.MISC)
                .clientTrackingRange(256)
                .updateInterval(20)
                .sized(0.0001F, 0.0001F)
                .build(Constants.MODID + ":dummy").setRegistryName(DBNames.DUMMY_ENTITY));
    }

    public static class RegistryHelperForge<T extends IForgeRegistryEntry<T>> implements RegistryHelper<T> {
        IForgeRegistry<T> registry;

        public RegistryHelperForge(IForgeRegistry<T> registry) {
            this.registry = registry;
        }

        @Override
        public void register(T entry, ResourceLocation name) {
            registry.register(entry.setRegistryName(name));
        }
    }
}
