package lilypuree.decorative_blocks;


import lilypuree.decorative_blocks.core.*;
import lilypuree.decorative_blocks.core.setup.ModSetup;
import lilypuree.decorative_blocks.blocks.types.WoodDecorativeBlockTypes;
import lilypuree.decorative_blocks.entity.DummyEntityForSitting;
import lilypuree.decorative_blocks.fluid.ThatchFluid;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.core.Registry;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.ItemStack;

public class DecorativeBlocks implements ModInitializer {

    @Override
    public void onInitialize() {
        DBTags.init();
        Constants.ITEM_GROUP = FabricItemGroupBuilder.build(new ResourceLocation(Constants.MODID, "general"), () -> new ItemStack(DBItems.BRAZIER));
        DummyEntityForSitting.factory = (type, level) -> new DummyEntityForSitting(type, level) {
            @Override
            public Packet<?> getAddEntityPacket() {
                return new ClientboundAddEntityPacket(this);
            }
        };
        Registration.STILL_THATCH = new ThatchFluid.Source(Registration.referenceHolder);
        Registration.FLOWING_THATCH = new ThatchFluid.Flowing(Registration.referenceHolder);
        Registration.THATCH = new FabricThatchFluidBlock(Registration.STILL_THATCH, Registration.thatchProperties);
        Registration.registerBlocks(new RegistryHelperFabric<>(Registry.BLOCK));
        Registration.registerItems(new RegistryHelperFabric<>(Registry.ITEM));
        Registration.registerFluids(new RegistryHelperFabric<>(Registry.FLUID));
        Registration.DUMMY_ENTITY_TYPE = EntityType.Builder.of(DummyEntityForSitting.factory, MobCategory.MISC)
                .clientTrackingRange(256)
                .updateInterval(20)
                .sized(0.0001F, 0.0001F)
                .build(Constants.MODID + ":dummy");
        Registry.register(Registry.ENTITY_TYPE, DBNames.DUMMY_ENTITY, Registration.DUMMY_ENTITY_TYPE);

        ModSetup.init();
        Callbacks.initCallbacks();
    }

    public static class RegistryHelperFabric<T> implements RegistryHelper<T> {
        Registry<T> registry;

        public RegistryHelperFabric(Registry<T> registry) {
            this.registry = registry;
        }

        @Override
        public void register(T entry, ResourceLocation name) {
            Registry.register(registry, name, entry);
        }
    }
}
