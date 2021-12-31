//package lilypuree.decorative_blocks.datagen;
//
//import com.lilypuree.decorative_blocks.DecorativeBlocks;
//import com.lilypuree.decorative_blocks.core.setup.Registration;
//import com.lilypuree.decorative_blocks.datagen.types.IWoodType;
//import com.lilypuree.decorative_blocks.datagen.types.ModWoodTypes;
//import com.lilypuree.decorative_blocks.items.SeatItem;
//import com.lilypuree.decorative_blocks.items.SupportItem;
//import net.minecraft.data.DataGenerator;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.level.ItemLike;
//import net.minecraft.world.level.block.Block;
//import net.minecraftforge.common.data.ExistingFileHelper;
//
//public class Items extends ItemModelProvider {
//
//    public Items(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
//        super(generator, modid, existingFileHelper);
//    }
//
//    @Override
//    protected void registerModels() {
//        ItemGenerationHelper generationHelper = new ItemGenerationHelper(DecorativeBlocks.MODID, this);
//        for (IWoodType wood : ModWoodTypes.allWoodTypes()) {
//            getBuilder(wood + "_beam").parent(new ModelFile.UncheckedModelFile(modLoc("block/" + wood + "_beam_y")));
//            getBuilder(wood + "_palisade").parent(new ModelFile.UncheckedModelFile(modLoc("block/" + wood + "_palisade_inventory")));
//            generationHelper.seatModel(wood);
//            generationHelper.supportModel(wood);
//        }
//        blockItemModel(Registration.ROCKY_DIRT.get());
//        simpleItem(Registration.CHAIN.get());
//        simpleItem(Registration.BLOCKSTATE_COPY_ITEM.get());
//        simpleItem(Registration.BRAZIER.get());
//        simpleItem(Registration.SOUL_BRAZIER.get());
//    }
//
//    private void simpleItem(ItemLike provider) {
//        String name = provider.asItem().getRegistryName().getPath();
//        generated(name, modLoc("item/" + name));
//    }
//
//    private void simpleItem(ItemLike provider, ResourceLocation texture) {
//        String name = provider.asItem().getRegistryName().getPath();
//        generated(name, texture);
//    }
//
//    private void generated(String path, ResourceLocation texture) {
//        getBuilder(path).parent(new ModelFile.UncheckedModelFile(mcLoc("item/generated"))).texture("layer0", texture);
//    }
//
//    private void blockItemModel(Block block) {
//        String name = block.getRegistryName().getPath();
//        getBuilder(name).parent(new ModelFile.UncheckedModelFile(modLoc(ModelProvider.BLOCK_FOLDER + "/" + name)));
//    }
//
//    @Override
//    public String getName() {
//        return "Decorative Block Item Models";
//    }
//
//    public static class ItemGenerationHelper {
//        private String modid;
//        private ItemModelProvider provider;
//
//        public ItemGenerationHelper(String modid, ItemModelProvider provider) {
//            this.modid = modid;
//            this.provider = provider;
//        }
//
//        public void supportModel(IWoodType wood) {
//            String name = wood + "_support";
//            provider.getBuilder(name).parent(newModel(name + "_inventory"))
//                    .override().model(newModel(wood + "_upside_down_support_inventory"))
//                    .predicate(SupportItem.OVERRIDE_TAG, 1.0f).end();
//        }
//
//        public void seatModel(IWoodType wood) {
//            String name = wood + "_seat";
//            provider.getBuilder(name).parent(newModel(name + "_inventory"))
//                    .override().model(newModel(name + "_post_inventory"))
//                    .predicate(SeatItem.OVERRIDE_TAG, 1.0f).end();
//        }
//
//        public ModelFile newModel(String name) {
//            return newModel(modBlockLoc(name));
//        }
//
//        public ModelFile newModel(ResourceLocation location) {
//            return new ModelFile.UncheckedModelFile(location);
//        }
//
//        public ResourceLocation modBlockLoc(String name) {
//            return new ResourceLocation(modid, "block/" + name);
//        }
//
//    }
//}
