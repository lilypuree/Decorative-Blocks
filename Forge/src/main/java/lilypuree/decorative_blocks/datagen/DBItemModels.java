package lilypuree.decorative_blocks.datagen;

import lilypuree.decorative_blocks.Constants;
import lilypuree.decorative_blocks.core.DBBlocks;
import lilypuree.decorative_blocks.core.DBItems;
import lilypuree.decorative_blocks.blocks.types.IWoodType;
import lilypuree.decorative_blocks.blocks.types.VanillaWoodTypes;
import lilypuree.decorative_blocks.items.SeatItem;
import lilypuree.decorative_blocks.items.SupportItem;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class DBItemModels extends ItemModelProvider {

    public DBItemModels(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
        super(generator, modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ItemGenerationHelper generationHelper = new ItemGenerationHelper(Constants.MODID, this);
        for (IWoodType wood : VanillaWoodTypes.values()) {
            getBuilder(wood + "_beam").parent(new ModelFile.UncheckedModelFile(modLoc("block/" + wood + "_beam_y")));
            getBuilder(wood + "_palisade").parent(new ModelFile.UncheckedModelFile(modLoc("block/" + wood + "_palisade_inventory")));
            generationHelper.seatModel(wood);
            generationHelper.supportModel(wood);
        }
        blockItemModel(DBBlocks.ROCKY_DIRT);
        simpleItem(DBItems.CHAIN);
//        simpleItem(DBItems.BLOCKSTATE_COPY_ITEM);
        simpleItem(DBItems.BRAZIER);
        simpleItem(DBItems.SOUL_BRAZIER);
        /*
        bar panels, chandeliers, lattices, stone pillars have a custom item model
         */
    }

    private void simpleItem(ItemLike provider) {
        String name = provider.asItem().getRegistryName().getPath();
        generated(name, modLoc("item/" + name));
    }

    private void simpleItem(ItemLike provider, ResourceLocation texture) {
        String name = provider.asItem().getRegistryName().getPath();
        generated(name, texture);
    }

    private void generated(String path, ResourceLocation texture) {
        getBuilder(path).parent(new ModelFile.UncheckedModelFile(mcLoc("item/generated"))).texture("layer0", texture);
    }

    private void blockItemModel(Block block) {
        String name = block.getRegistryName().getPath();
        getBuilder(name).parent(new ModelFile.UncheckedModelFile(modLoc(ModelProvider.BLOCK_FOLDER + "/" + name)));
    }

    @Override
    public String getName() {
        return "Decorative Block Item Models";
    }

    public static class ItemGenerationHelper {
        private String modid;
        private ItemModelProvider provider;

        public ItemGenerationHelper(String modid, ItemModelProvider provider) {
            this.modid = modid;
            this.provider = provider;
        }

        public void supportModel(IWoodType wood) {
            String name = wood + "_support";
            provider.getBuilder(name).parent(newModel(name + "_inventory"))
                    .override().model(newModel(wood + "_upside_down_support_inventory"))
                    .predicate(SupportItem.OVERRIDE_TAG, 1.0f).end();
        }

        public void seatModel(IWoodType wood) {
            String name = wood + "_seat";
            provider.getBuilder(name).parent(newModel(name + "_inventory"))
                    .override().model(newModel(name + "_post_inventory"))
                    .predicate(SeatItem.OVERRIDE_TAG, 1.0f).end();
        }

        public ModelFile newModel(String name) {
            return newModel(modBlockLoc(name));
        }

        public ModelFile newModel(ResourceLocation location) {
            return new ModelFile.UncheckedModelFile(location);
        }

        public ResourceLocation modBlockLoc(String name) {
            return new ResourceLocation(modid, "block/" + name);
        }
    }
}
