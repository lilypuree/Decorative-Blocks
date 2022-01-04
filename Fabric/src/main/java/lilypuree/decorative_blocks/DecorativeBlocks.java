package lilypuree.decorative_blocks;


import lilypuree.decorative_blocks.core.*;
import lilypuree.decorative_blocks.core.factory.BlockSuppliers;
import lilypuree.decorative_blocks.core.setup.ModSetup;
import lilypuree.decorative_blocks.datagen.types.WoodDecorativeBlockTypes;
import lilypuree.decorative_blocks.entity.DummyEntityForSitting;
import lilypuree.decorative_blocks.fluid.ThatchFluid;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.core.Registry;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.ItemStack;

public class DecorativeBlocks implements ModInitializer {

    @Override
    public void onInitialize() {
        tagInit();
        Constants.ITEM_GROUP = FabricItemGroupBuilder.build(new ResourceLocation(Constants.MODID, "general"), () -> new ItemStack(DBItems.BRAZIER));
        DummyEntityForSitting.factory = (type, level) -> new DummyEntityForSitting(type, level) {
            @Override
            public Packet<?> getAddEntityPacket() {
                return new ClientboundAddEntityPacket(this);
            }
        };
        registerBlocks();
        registerItems();
        register();
        ModSetup.init();
        Callbacks.initCallbacks();
    }

    public void tagInit(){
        DBTags.Blocks.PALISADES = TagFactory.BLOCK.create(tagName("palisades"));
        DBTags.Blocks.SUPPORTS = TagFactory.BLOCK.create(tagName("supports"));
        DBTags.Blocks.SEATS = TagFactory.BLOCK.create(tagName("seats"));
        DBTags.Blocks.BEAMS = TagFactory.BLOCK.create(tagName("beams"));
        DBTags.Blocks.CHANDELIERS = TagFactory.BLOCK.create(tagName("chandeliers"));
        DBTags.Blocks.BRAZIERS = TagFactory.BLOCK.create(tagName("braziers"));
        DBTags.Blocks.BONFIRES = TagFactory.BLOCK.create(tagName("bonfires"));

        DBTags.Items.PALISADES = TagFactory.ITEM.create(tagName("palisades"));
        DBTags.Items.SUPPORTS = TagFactory.ITEM.create(tagName("supports"));
        DBTags.Items.SEATS = TagFactory.ITEM.create(tagName("seats"));
        DBTags.Items.BEAMS = TagFactory.ITEM.create(tagName("beams"));
        DBTags.Items.CHANDELIERS = TagFactory.ITEM.create(tagName("chandeliers"));

        DBTags.Fluids.THATCH = TagFactory.FLUID.create(tagName("thatch"));
        FluidTags.getStaticTags().add(DBTags.Fluids.THATCH);
    }

    private ResourceLocation tagName(String name){
        return new ResourceLocation(Constants.MODID, name);
    }

    public void registerBlocks() {
        DBBlocks.init();
        if (Registration.STILL_THATCH == null) {
            Registration.STILL_THATCH = new ThatchFluid.Source(Registration.referenceHolder);
            Registration.FLOWING_THATCH = new ThatchFluid.Flowing(Registration.referenceHolder);
        }
        Registration.THATCH = BlockSuppliers.THATCH.get();
        Registry.register(Registry.BLOCK, DBNames.THATCH, Registration.THATCH);
        Registry.register(Registry.BLOCK, DBNames.ROCKY_DIRT, DBBlocks.ROCKY_DIRT);
        Registry.register(Registry.BLOCK, DBNames.STONE_PILLAR, DBBlocks.STONE_PILLAR);
        Registry.register(Registry.BLOCK, DBNames.CHAIN, DBBlocks.CHAIN);
        Registry.register(Registry.BLOCK, DBNames.BAR_PANEL, DBBlocks.BAR_PANEL);
        Registry.register(Registry.BLOCK, DBNames.BRAZIER, DBBlocks.BRAZIER);
        Registry.register(Registry.BLOCK, DBNames.SOUL_BRAZIER, DBBlocks.SOUL_BRAZIER);
        Registry.register(Registry.BLOCK, DBNames.LATTICE, DBBlocks.LATTICE);
        Registry.register(Registry.BLOCK, DBNames.BONFIRE, DBBlocks.BONFIRE);
        Registry.register(Registry.BLOCK, DBNames.SOUL_BONFIRE, DBBlocks.SOUL_BONFIRE);
        Registry.register(Registry.BLOCK, DBNames.CHANDELIER, DBBlocks.CHANDELIER);
        Registry.register(Registry.BLOCK, DBNames.SOUL_CHANDELIER, DBBlocks.SOUL_CHANDELIER);
        DBBlocks.BEAMS.forEach((wood, block) -> Registry.register(Registry.BLOCK, DBNames.create(wood, WoodDecorativeBlockTypes.BEAM), block));
        DBBlocks.PALISADES.forEach((wood, block) -> Registry.register(Registry.BLOCK, DBNames.create(wood, WoodDecorativeBlockTypes.PALISADE), block));
        DBBlocks.SUPPORTS.forEach((wood, block) -> Registry.register(Registry.BLOCK, DBNames.create(wood, WoodDecorativeBlockTypes.SUPPORT), block));
        DBBlocks.SEATS.forEach((wood, block) -> Registry.register(Registry.BLOCK, DBNames.create(wood, WoodDecorativeBlockTypes.SEAT), block));
    }

    public static void registerItems() {
        DBItems.init();
        Registry.register(Registry.ITEM, DBNames.ROCKY_DIRT, DBItems.ROCKY_DIRT);
        Registry.register(Registry.ITEM, DBNames.STONE_PILLAR, DBItems.STONE_PILLAR);
        Registry.register(Registry.ITEM, DBNames.CHAIN, DBItems.CHAIN);
        Registry.register(Registry.ITEM, DBNames.BAR_PANEL, DBItems.BAR_PANEL);
        Registry.register(Registry.ITEM, DBNames.BRAZIER, DBItems.BRAZIER);
        Registry.register(Registry.ITEM, DBNames.SOUL_BRAZIER, DBItems.SOUL_BRAZIER);
        Registry.register(Registry.ITEM, DBNames.LATTICE, DBItems.LATTICE);
        Registry.register(Registry.ITEM, DBNames.CHANDELIER, DBItems.CHANDELIER);
        Registry.register(Registry.ITEM, DBNames.SOUL_CHANDELIER, DBItems.SOUL_CHANDELIER);
        DBItems.BEAM_ITEMBLOCKS.forEach((wood, item) -> Registry.register(Registry.ITEM, DBNames.create(wood, WoodDecorativeBlockTypes.BEAM), item));
        DBItems.PALISADE_ITEMBLOCKS.forEach((wood, item) -> Registry.register(Registry.ITEM, DBNames.create(wood, WoodDecorativeBlockTypes.PALISADE), item));
        DBItems.SUPPORT_ITEMBLOCKS.forEach((wood, item) -> Registry.register(Registry.ITEM, DBNames.create(wood, WoodDecorativeBlockTypes.SUPPORT), item));
        DBItems.SEAT_ITEMBLOCKS.forEach((wood, item) -> Registry.register(Registry.ITEM, DBNames.create(wood, WoodDecorativeBlockTypes.SEAT), item));
    }

    public static void register() {
        if (Registration.STILL_THATCH == null) {
            Registration.STILL_THATCH = new ThatchFluid.Source(Registration.referenceHolder);
            Registration.FLOWING_THATCH = new ThatchFluid.Flowing(Registration.referenceHolder);
        }
        Registry.register(Registry.FLUID, DBNames.FLOWING_THATCH, Registration.FLOWING_THATCH);
        Registry.register(Registry.FLUID, DBNames.STILL_THATCH, Registration.STILL_THATCH);

        Registration.DUMMY_ENTITY_TYPE = (EntityType<DummyEntityForSitting>) EntityType.Builder.of(DummyEntityForSitting.factory, MobCategory.MISC)
                .clientTrackingRange(256)
                .updateInterval(20)
                .sized(0.0001F, 0.0001F)
                .build(Constants.MODID + ":dummy");
        Registry.register(Registry.ENTITY_TYPE, DBNames.DUMMY_ENTITY, Registration.DUMMY_ENTITY_TYPE);
    }
}
