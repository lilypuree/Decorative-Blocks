package lilypuree.decorative_blocks.datagen;

import lilypuree.decorative_blocks.Constants;
import lilypuree.decorative_blocks.core.DBBlocks;
import lilypuree.decorative_blocks.core.DBItems;
import lilypuree.decorative_blocks.blocks.types.IWoodType;
import lilypuree.decorative_blocks.blocks.types.VanillaWoodTypes;
import lilypuree.decorative_blocks.items.SeatItem;
import lilypuree.decorative_blocks.items.SupportItem;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class DBItemModels extends ItemModelProvider {

    public DBItemModels(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ItemGenerationHelper helper = new ItemGenerationHelper(Constants.MOD_ID, this);
        for (IWoodType wood : VanillaWoodTypes.values()) {
            if (wood != VanillaWoodTypes.BAMBOO)
                getBuilder(wood + "_beam").parent(new ModelFile.UncheckedModelFile(modLoc("block/" + wood + "_beam_y")));
            getBuilder(wood + "_palisade").parent(new ModelFile.UncheckedModelFile(modLoc("block/" + wood + "_palisade_inventory")));
            helper.seatModel(wood);
            helper.supportModel(wood);
        }
        helper.blockItem(DBBlocks.ROCKY_DIRT.get());
        helper.simpleItem(DBItems.CHAIN.get());
        helper.simpleItem(DBItems.BRAZIER.get());
        helper.simpleItem(DBItems.SOUL_BRAZIER.get());
//        simpleItem(DBItems.BLOCKSTATE_COPY_ITEM);
        /*
        bar panels, chandeliers, lattices, stone pillars have a custom item model
         */
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

        private void blockItem(Block block) {
            String name = ForgeRegistries.BLOCKS.getKey(block).getPath();
            provider.getBuilder(name).parent(newModel(provider.modLoc(ModelProvider.BLOCK_FOLDER + "/" + name)));
        }

        private void simpleItem(ItemLike item) {
            String name = ForgeRegistries.ITEMS.getKey(item.asItem()).getPath();
            generated(name, provider.modLoc(ModelProvider.ITEM_FOLDER + "/" + name));
        }

        public void simpleItem(ItemLike item, ResourceLocation texture) {
            String name = ForgeRegistries.ITEMS.getKey(item.asItem()).getPath();
            generated(name, texture);
        }

        public void generated(String path, ResourceLocation texture) {
            provider.getBuilder(path).parent(newModel(provider.mcLoc("item/generated"))).texture("layer0", texture);
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
