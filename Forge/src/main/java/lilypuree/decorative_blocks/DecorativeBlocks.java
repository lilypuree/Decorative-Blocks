package lilypuree.decorative_blocks;

import lilypuree.decorative_blocks.core.*;
import lilypuree.decorative_blocks.core.setup.ClientSetup;
import lilypuree.decorative_blocks.core.setup.ModSetup;
import lilypuree.decorative_blocks.datagen.types.IWoodType;
import lilypuree.decorative_blocks.datagen.types.VanillaWoodTypes;
import lilypuree.decorative_blocks.entity.DummyEntityForSitting;
import lilypuree.decorative_blocks.events.ClientEventHandler;
import lilypuree.decorative_blocks.fluid.ForgeThatchFluid;
import lilypuree.decorative_blocks.fluid.ThatchFluid;
import lilypuree.decorative_blocks.fluid.ThatchFluidBlock;
import lilypuree.decorative_blocks.items.SeatItem;
import lilypuree.decorative_blocks.items.SupportItem;
import lilypuree.decorative_blocks.mixin.ItemPropertiesForgeInvoker;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkHooks;

import java.util.Arrays;

@Mod(Constants.MODID)
public class DecorativeBlocks {

    public DecorativeBlocks() {
        DBTags.init();
        CommonAPI.dummyEntityFactory = (type, level) -> new DummyEntityForSitting(type, level) {
            @Override
            public Packet<?> getAddEntityPacket() {
                return NetworkHooks.getEntitySpawningPacket(this);
            }
        };

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);

        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener((FMLCommonSetupEvent e) -> ModSetup.init());
        modBus.addListener((FMLClientSetupEvent e) -> ClientSetup.init());
        modBus.addListener(this::registerItemFunc);

        modBus.addGenericListener(Block.class, this::registerBlocks);
        modBus.addGenericListener(Item.class, this::registerItems);
        modBus.addGenericListener(Fluid.class, this::registerFluids);
        modBus.addGenericListener(EntityType.class, this::registerEntities);
    }

    public void registerItemFunc(FMLClientSetupEvent e) {
        for (IWoodType woodType : VanillaWoodTypes.values()) {
            ItemPropertiesForgeInvoker.invokeRegister(DBBlocks.SUPPORTS.get(woodType).asItem(), SupportItem.OVERRIDE_TAG, (stack, level, entity, i) -> {
                return stack.hasTag() ? stack.getTag().getInt(SupportItem.OVERRIDE_TAG.getPath()) : 0.0f;
            });
            ItemPropertiesForgeInvoker.invokeRegister(DBBlocks.SEATS.get(woodType).asItem(), SeatItem.OVERRIDE_TAG, (stack, world, livingEntity, i) -> {
                return stack.hasTag() ? stack.getTag().getInt(SeatItem.OVERRIDE_TAG.getPath()) : 0.0f;
            });
        }
    }

    public void registerBlocks(RegistryEvent.Register<Block> event) {
        if (Registration.STILL_THATCH == null) {
            Registration.STILL_THATCH = new ForgeThatchFluid.Source(Registration.referenceHolder);
            Registration.FLOWING_THATCH = new ForgeThatchFluid.Flowing(Registration.referenceHolder);
        }
        Registration.THATCH = new ThatchFluidBlock(Registration.STILL_THATCH, Block.Properties.of(Registration.THATCH_MATERIAL).noCollission().randomTicks().strength(100.0F).noDrops());

        event.getRegistry().registerAll(
                Registration.THATCH.setRegistryName(DBNames.THATCH),
                DBBlocks.ROCKY_DIRT.setRegistryName(DBNames.ROCKY_DIRT),
                DBBlocks.BAR_PANEL.setRegistryName(DBNames.BONFIRE),
                DBBlocks.LATTICE.setRegistryName(DBNames.LATTICE),
                DBBlocks.CHAIN.setRegistryName(DBNames.CHAIN),
                DBBlocks.STONE_PILLAR.setRegistryName(DBNames.STONE_PILLAR),
                DBBlocks.BONFIRE.setRegistryName(DBNames.BONFIRE),
                DBBlocks.CHANDELIER.setRegistryName(DBNames.CHANDELIER),
                DBBlocks.BRAZIER.setRegistryName(DBNames.BRAZIER),
                DBBlocks.SOUL_BONFIRE.setRegistryName(DBNames.SOUL_BONFIRE),
                DBBlocks.SOUL_CHANDELIER.setRegistryName(DBNames.SOUL_CHANDELIER),
                DBBlocks.SOUL_BRAZIER.setRegistryName(DBNames.SOUL_BRAZIER)
        );
        for (IWoodType wood : VanillaWoodTypes.values()) {
            event.getRegistry().registerAll(
                    DBBlocks.BEAMS.get(wood).setRegistryName(wood + "_beam"),
                    DBBlocks.PALISADES.get(wood).setRegistryName(wood + "_palisade"),
                    DBBlocks.SUPPORTS.get(wood).setRegistryName(wood + "_support"),
                    DBBlocks.SEATS.get(wood).setRegistryName(wood + "_seat")
            );
        }
    }

    public void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                DBItems.ROCKY_DIRT.setRegistryName(DBNames.ROCKY_DIRT),
                DBItems.BAR_PANEL.setRegistryName(DBNames.BAR_PANEL),
                DBItems.LATTICE.setRegistryName(DBNames.LATTICE),
                DBItems.CHAIN.setRegistryName(DBNames.CHAIN),
                DBItems.STONE_PILLAR.setRegistryName(DBNames.STONE_PILLAR),
                DBItems.CHANDELIER.setRegistryName(DBNames.CHANDELIER),
                DBItems.BRAZIER.setRegistryName(DBNames.BRAZIER),
                DBItems.SOUL_CHANDELIER.setRegistryName(DBNames.SOUL_CHANDELIER),
                DBItems.SOUL_BRAZIER.setRegistryName(DBNames.SOUL_BRAZIER),
                DBItems.BLOCKSTATE_COPY_ITEM.setRegistryName(DBNames.BLOCKSTATE_COPY_ITEM)
        );
        for (IWoodType wood : VanillaWoodTypes.values()) {
            event.getRegistry().registerAll(
                    DBItems.BEAM_ITEMBLOCKS.get(wood).setRegistryName(wood + "_beam"),
                    DBItems.PALISADE_ITEMBLOCKS.get(wood).setRegistryName(wood + "_palisade"),
                    DBItems.SUPPORT_ITEMBLOCKS.get(wood).setRegistryName(wood + "_support"),
                    DBItems.SEAT_ITEMBLOCKS.get(wood).setRegistryName(wood + "_seat")
            );
        }
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
        event.getRegistry().register(Registration.DUMMY_ENTITY_TYPE = (EntityType<DummyEntityForSitting>) EntityType.Builder.of(CommonAPI.dummyEntityFactory, MobCategory.MISC)
                .clientTrackingRange(256)
                .updateInterval(20)
                .sized(0.0001F, 0.0001F)
                .build(Constants.MODID + ":dummy").setRegistryName(DBNames.DUMMY_ENTITY));
    }
}
