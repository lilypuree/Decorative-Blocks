package lilypuree.decorative_blocks;

import lilypuree.decorative_blocks.core.*;
import lilypuree.decorative_blocks.core.factory.BlockSuppliers;
import lilypuree.decorative_blocks.core.setup.ModSetup;
import lilypuree.decorative_blocks.entity.DummyEntityForSitting;
import lilypuree.decorative_blocks.events.ClientEventHandler;
import lilypuree.decorative_blocks.fluid.ForgeThatchFluid;
import net.minecraft.network.protocol.Packet;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
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

@Mod(Constants.MODID)
public class DecorativeBlocks {

    public DecorativeBlocks() {
        tagInit();
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

        modBus.addGenericListener(Block.class, this::registerBlocks);
        modBus.addGenericListener(Item.class, this::registerItems);
        modBus.addGenericListener(Fluid.class, this::registerFluids);
        modBus.addGenericListener(EntityType.class, this::registerEntities);
    }

    public void tagInit() {
        DBTags.Blocks.PALISADES = BlockTags.bind(getName("palisades"));
        DBTags.Blocks.SUPPORTS = BlockTags.bind(getName("supports"));
        DBTags.Blocks.SEATS = BlockTags.bind(getName("seats"));
        DBTags.Blocks.BEAMS = BlockTags.bind(getName("beams"));
        DBTags.Blocks.CHANDELIERS = BlockTags.bind(getName("chandeliers"));
        DBTags.Blocks.BRAZIERS = BlockTags.bind(getName("braziers"));
        DBTags.Blocks.BONFIRES = BlockTags.bind(getName("bonfires"));

        DBTags.Items.PALISADES = ItemTags.bind(getName("palisades"));
        DBTags.Items.SUPPORTS = ItemTags.bind(getName("supports"));
        DBTags.Items.SEATS = ItemTags.bind(getName("seats"));
        DBTags.Items.BEAMS = ItemTags.bind(getName("beams"));
        DBTags.Items.CHANDELIERS = ItemTags.bind(getName("chandeliers"));

        DBTags.Fluids.THATCH = FluidTags.bind(getName("thatch"));
    }

    private String getName(String name) {
        return Constants.MODID + ":" + name;
    }


    public void registerBlocks(RegistryEvent.Register<Block> event) {
        DBBlocks.init();
        if (Registration.STILL_THATCH == null) {
            Registration.STILL_THATCH = new ForgeThatchFluid.Source(Registration.referenceHolder);
            Registration.FLOWING_THATCH = new ForgeThatchFluid.Flowing(Registration.referenceHolder);
        }
        Registration.THATCH = BlockSuppliers.THATCH.get();
        IForgeRegistry<Block> registry = event.getRegistry();

        registry.registerAll(
                Registration.THATCH.setRegistryName(DBNames.THATCH),
                DBBlocks.ROCKY_DIRT.setRegistryName(DBNames.ROCKY_DIRT),
                DBBlocks.STONE_PILLAR.setRegistryName(DBNames.STONE_PILLAR),
                DBBlocks.CHAIN.setRegistryName(DBNames.CHAIN),
                DBBlocks.BAR_PANEL.setRegistryName(DBNames.BAR_PANEL),
                DBBlocks.LATTICE.setRegistryName(DBNames.LATTICE),
                DBBlocks.CHANDELIER.setRegistryName(DBNames.CHANDELIER),
                DBBlocks.SOUL_CHANDELIER.setRegistryName(DBNames.SOUL_CHANDELIER),
                DBBlocks.BONFIRE.setRegistryName(DBNames.BONFIRE),
                DBBlocks.SOUL_BONFIRE.setRegistryName(DBNames.SOUL_BONFIRE),
                DBBlocks.BRAZIER.setRegistryName(DBNames.BRAZIER),
                DBBlocks.SOUL_BRAZIER.setRegistryName(DBNames.SOUL_BRAZIER)
        );
        DBBlocks.BEAMS.forEach((wood, block) -> registry.register(block.setRegistryName(wood + "_beam")));
        DBBlocks.PALISADES.forEach((wood, block) -> registry.register(block.setRegistryName(wood + "_palisade")));
        DBBlocks.SUPPORTS.forEach((wood, block) -> registry.register(block.setRegistryName(wood + "_support")));
        DBBlocks.SEATS.forEach((wood, block) -> registry.register(block.setRegistryName(wood + "_seat")));
    }

    public void registerItems(RegistryEvent.Register<Item> event) {
        DBItems.init();
        IForgeRegistry<Item> registry = event.getRegistry();
        registry.registerAll(
                DBItems.ROCKY_DIRT.setRegistryName(DBNames.ROCKY_DIRT),
                DBItems.STONE_PILLAR.setRegistryName(DBNames.STONE_PILLAR),
                DBItems.CHAIN.setRegistryName(DBNames.CHAIN),
                DBItems.BAR_PANEL.setRegistryName(DBNames.BAR_PANEL),
                DBItems.BRAZIER.setRegistryName(DBNames.BRAZIER),
                DBItems.SOUL_BRAZIER.setRegistryName(DBNames.SOUL_BRAZIER),
                DBItems.LATTICE.setRegistryName(DBNames.LATTICE),
                DBItems.CHANDELIER.setRegistryName(DBNames.CHANDELIER),
                DBItems.SOUL_CHANDELIER.setRegistryName(DBNames.SOUL_CHANDELIER)
//                DBItems.BLOCKSTATE_COPY_ITEM.setRegistryName(DBNames.BLOCKSTATE_COPY_ITEM)
        );
        DBItems.BEAM_ITEMBLOCKS.forEach((wood, item) -> registry.register(item.setRegistryName(wood + "_beam")));
        DBItems.PALISADE_ITEMBLOCKS.forEach((wood, item) -> registry.register(item.setRegistryName(wood + "_palisade")));
        DBItems.SUPPORT_ITEMBLOCKS.forEach((wood, item) -> registry.register(item.setRegistryName(wood + "_support")));
        DBItems.SEAT_ITEMBLOCKS.forEach((wood, item) -> registry.register(item.setRegistryName(wood + "_seat")));
    }

    public void registerFluids(RegistryEvent.Register<Fluid> event) {
        if (Registration.STILL_THATCH == null) {
            Registration.STILL_THATCH = new ForgeThatchFluid.Source(Registration.referenceHolder);
            Registration.FLOWING_THATCH = new ForgeThatchFluid.Flowing(Registration.referenceHolder);
        }
        event.getRegistry().registerAll(
                Registration.STILL_THATCH.setRegistryName(DBNames.STILL_THATCH),
                Registration.FLOWING_THATCH.setRegistryName(DBNames.FLOWING_THATCH)
        );
    }

    public void registerEntities(RegistryEvent.Register<EntityType<?>> event) {
        event.getRegistry().register(Registration.DUMMY_ENTITY_TYPE = (EntityType<DummyEntityForSitting>) EntityType.Builder.of(DummyEntityForSitting.factory, MobCategory.MISC)
                .clientTrackingRange(256)
                .updateInterval(20)
                .sized(0.0001F, 0.0001F)
                .build(Constants.MODID + ":dummy").setRegistryName(DBNames.DUMMY_ENTITY));
    }
}
