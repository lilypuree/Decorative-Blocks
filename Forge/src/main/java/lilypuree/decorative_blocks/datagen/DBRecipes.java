package lilypuree.decorative_blocks.datagen;

import lilypuree.decorative_blocks.blocks.*;
import lilypuree.decorative_blocks.core.DBBlocks;
import lilypuree.decorative_blocks.blocks.types.IWoodType;
import lilypuree.decorative_blocks.blocks.types.VanillaWoodTypes;
import lilypuree.decorative_blocks.blocks.types.WoodDecorativeBlockTypes;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Consumer;

public class DBRecipes extends RecipeProvider {
    public DBRecipes(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        for (IWoodType wood : VanillaWoodTypes.values()) {
            makeWoodenBlockRecipes(wood, consumer);
        }
        ShapedRecipeBuilder.shaped(DBBlocks.BAR_PANEL, 2).pattern("##").define('#', Items.IRON_BARS)
                .unlockedBy("has_ingot", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_INGOT)).save(consumer);
        ShapedRecipeBuilder.shaped(DBBlocks.LATTICE, 1).pattern(" # ").pattern("# #").pattern(" # ").define('#', Items.STICK)
                .unlockedBy("has_stick", InventoryChangeTrigger.TriggerInstance.hasItems(Items.STICK)).save(consumer);
        ShapedRecipeBuilder.shaped(DBBlocks.ROCKY_DIRT, 1).pattern("##").pattern("##").define('#', Items.DIRT)
                .unlockedBy("has_dirt", InventoryChangeTrigger.TriggerInstance.hasItems(Items.DIRT)).save(consumer);
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(Blocks.STONE), DBBlocks.STONE_PILLAR)
                .unlockedBy("has_stone",InventoryChangeTrigger.TriggerInstance.hasItems(Items.STONE)).save(consumer);

        ShapedRecipeBuilder.shaped(DBBlocks.BRAZIER).group("decorative_braziers").pattern("010").pattern(" 0 ")
                .define('0', Items.IRON_BARS).define('1', ItemTags.COALS)
                .unlockedBy("has_bar", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_BARS)).save(consumer);
        ShapedRecipeBuilder.shaped(DBBlocks.SOUL_BRAZIER).group("decorative_braziers").pattern(" 1 ").pattern("020").pattern(" 0 ")
                .define('0', Items.IRON_BARS).define('1', ItemTags.COALS).define('2', ItemTags.SOUL_FIRE_BASE_BLOCKS)
                .unlockedBy("has_bar", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_BARS)).save(consumer);

        makeChandelierRecipeOf(DBBlocks.CHANDELIER.get(), Items.TORCH, consumer);
        makeChandelierRecipeOf(DBBlocks.SOUL_CHANDELIER.get(), Items.SOUL_TORCH, consumer);
    }

//    public static void makeCompatBlockRecipes(IWoodType woodType, Consumer<FinishedRecipe> recipeConsumer) {
//        modConditionalRecipe(consumer -> {
//            makeBeamRecipeOf(getBlock(woodType, WoodDecorativeBlockTypes.BEAM), consumer);
//        }, woodType.namespace(), recipeConsumer);
//        modConditionalRecipe(consumer -> {
//            makePalisadeRecipeOf(getBlock(woodType, WoodDecorativeBlockTypes.PALISADE), consumer);
//        }, woodType.namespace(), recipeConsumer);
//        modConditionalRecipe(consumer -> {
//            makeSeatRecipeOf(getBlock(woodType, WoodDecorativeBlockTypes.SEAT), consumer);
//        }, woodType.namespace(), recipeConsumer);
//        modConditionalRecipe(consumer -> {
//            makeSupportRecipeOf(getBlock(woodType, WoodDecorativeBlockTypes.SUPPORT), consumer);
//        }, woodType.namespace(), recipeConsumer);
//    }

    public static void makeWoodenBlockRecipes(IWoodType woodType, Consumer<FinishedRecipe> consumer) {
        makeBeamRecipeOf(getBlock(woodType, WoodDecorativeBlockTypes.BEAM), consumer);
        makePalisadeRecipeOf(getBlock(woodType, WoodDecorativeBlockTypes.PALISADE), consumer);
        makeSeatRecipeOf(getBlock(woodType, WoodDecorativeBlockTypes.SEAT), consumer);
        makeSupportRecipeOf(getBlock(woodType, WoodDecorativeBlockTypes.SUPPORT), consumer);
    }

    public static IWoodenBlock getBlock(IWoodType woodType, WoodDecorativeBlockTypes type) {
        return (IWoodenBlock) Registry.BLOCK.stream().filter(block -> {
            if (block instanceof IWoodenBlock) {
                if (((IWoodenBlock) block).getWoodType() == woodType) {
                    switch (type) {
                        case SEAT:
                            return block instanceof SeatBlock;
                        case BEAM:
                            return block instanceof BeamBlock;
                        case PALISADE:
                            return block instanceof PalisadeBlock;
                        case SUPPORT:
                            return block instanceof SupportBlock;
                    }
                }
            }
            return false;
        }).findFirst().orElse(null);
    }

    public static void makeChandelierRecipeOf(Block chandelier, Item torch, Consumer<FinishedRecipe> consumer){
        ShapedRecipeBuilder.shaped(chandelier).group("decorative_chandeliers").pattern("##").pattern("##").define('#', torch)
                .unlockedBy("has_torch", InventoryChangeTrigger.TriggerInstance.hasItems(torch)).save(consumer);
        ShapelessRecipeBuilder.shapeless(torch, 4).group("torches_from_chandeliers").requires(chandelier)
                .unlockedBy("has_torch", InventoryChangeTrigger.TriggerInstance.hasItems(torch)).save(consumer, "decorative_blocks:"+ ForgeRegistries.ITEMS.getKey(torch).getPath()+"_from_"+ForgeRegistries.BLOCKS.getKey(chandelier).getPath());
    }


    public static void makeBeamRecipeOf(IWoodenBlock block, Consumer<FinishedRecipe> consumer) {
        IWoodType wood = block.getWoodType();
        ShapedRecipeBuilder.shaped((Block) block, 2).group("decorative_beams")
                .pattern(" x ")
                .pattern(" x ")
                .define('x', wood.getStrippedLog())
                .unlockedBy("has_stripped_log", InventoryChangeTrigger.TriggerInstance.hasItems(wood.getStrippedLog()))
                .save(consumer);
    }

    public static void makePalisadeRecipeOf(IWoodenBlock block, Consumer<FinishedRecipe> consumer) {
        IWoodType wood = block.getWoodType();
        ShapedRecipeBuilder.shaped((Block) block, 6).group("decorative_palisades")
                .pattern("xx ")
                .define('x', wood.getLog())
                .unlockedBy("has_log", InventoryChangeTrigger.TriggerInstance.hasItems(wood.getLog()))
                .save(consumer);
    }

    public static void makeSeatRecipeOf(IWoodenBlock block, Consumer<FinishedRecipe> consumer) {
        IWoodType wood = block.getWoodType();
        ShapedRecipeBuilder.shaped(((Block) block), 2).group("decorative_seats")
                .pattern("x  ")
                .pattern("y  ")
                .define('x', wood.getSlab())
                .define('y', wood.getFence())
                .unlockedBy("has_plank", InventoryChangeTrigger.TriggerInstance.hasItems(wood.getPlanks()))
                .save(consumer);
    }

    public static void makeSupportRecipeOf(IWoodenBlock block, Consumer<FinishedRecipe> consumer) {
        IWoodType wood = block.getWoodType();
        ShapedRecipeBuilder.shaped(((Block) block), 3).group("decorative_supports")
                .group("support")
                .pattern("xx ")
                .pattern("x  ")
                .define('x', wood.getPlanks())
                .unlockedBy("has_plank", InventoryChangeTrigger.TriggerInstance.hasItems(wood.getPlanks()))
                .save(consumer);
    }

//    public static void modConditionalRecipe(Consumer<Consumer<FinishedRecipe>> baseRecipeBuilder, String modid, Consumer<FinishedRecipe> consumer) {
//        ICondition modLoadedCondition = new ModLoadedCondition(modid);
//        baseRecipeBuilder.accept(iFinishedRecipe -> {
//            ConditionalRecipe.builder().addCondition(modLoadedCondition).addRecipe(iFinishedRecipe)
//                    .setAdvancement(ConditionalAdvancement.builder().addCondition(modLoadedCondition).addAdvancement(iFinishedRecipe))
//                    .build(consumer, iFinishedRecipe.getId());
//        });
//    }
}
