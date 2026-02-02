package lilypuree.decorative_blocks.datagen;

import lilypuree.decorative_blocks.Constants;
import lilypuree.decorative_blocks.blocks.*;
import lilypuree.decorative_blocks.blocks.types.VanillaWoodTypes;
import lilypuree.decorative_blocks.blocks.types.WoodDecorativeBlockTypes;
import lilypuree.decorative_blocks.registration.DBBlocks;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.WoodType;

import java.util.concurrent.CompletableFuture;

public class DBRecipes extends RecipeProvider {
    public DBRecipes(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(pOutput, lookupProvider);
    }


    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        for (WoodType wood : VanillaWoodTypes.VANILLA) {
            makeWoodenBlockRecipes(wood, recipeOutput);
        }
        addRecipe(recipeOutput, shapedRecipe(RecipeCategory.BUILDING_BLOCKS, DBBlocks.BAR_PANEL, 2)
                .pattern("##")
                .define('#', Items.IRON_BARS), "has_ingot", Items.IRON_INGOT);
        addRecipe(recipeOutput, shapedRecipe(RecipeCategory.BUILDING_BLOCKS, DBBlocks.LATTICE, 1)
                        .pattern(" # ")
                        .pattern("# #")
                        .pattern(" # ")
                        .define('#', Items.STICK),
                "has_stick", Items.STICK);
        addRecipe(recipeOutput, shapedRecipe(RecipeCategory.BUILDING_BLOCKS, DBBlocks.ROCKY_DIRT, 1)
                        .pattern("##")
                        .pattern("##")
                        .define('#', Items.DIRT),
                "has_dirt", Items.DIRT);
        addRecipe(recipeOutput, shapedRecipe(RecipeCategory.DECORATIONS, DBBlocks.BRAZIER, 1)
                        .group("decorative_braziers")
                        .pattern("010")
                        .pattern(" 0 ")
                        .define('0', Items.IRON_BARS)
                        .define('1', ItemTags.COALS),
                "has_bar", Items.IRON_BARS);
        addRecipe(recipeOutput, shapedRecipe(RecipeCategory.DECORATIONS, () -> DBBlocks.SOUL_BRAZIER.get().asItem(), 1)
                        .group("decorative_braziers")
                        .pattern(" 1 ")
                        .pattern("020")
                        .pattern(" 0 ")
                        .define('0', Items.IRON_BARS)
                        .define('1', ItemTags.COALS)
                        .define('2', ItemTags.SOUL_FIRE_BASE_BLOCKS),
                "has_bar", Items.IRON_BARS);

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(Blocks.STONE), RecipeCategory.BUILDING_BLOCKS, DBBlocks.STONE_PILLAR.get())
                .unlockedBy("has_stone", InventoryChangeTrigger.TriggerInstance.hasItems(Items.STONE)).save(recipeOutput);


        makeChandelierRecipeOf(DBBlocks.CHANDELIER.get(), Items.TORCH, recipeOutput);
        makeChandelierRecipeOf(DBBlocks.SOUL_CHANDELIER.get(), Items.SOUL_TORCH, recipeOutput);

    }

    public static ShapedRecipeBuilder shapedRecipe(RecipeCategory category, ItemLike result, int count) {
        return ShapedRecipeBuilder.shaped(category, result, count);
    }

    public static ShapelessRecipeBuilder shapelessRecipe(RecipeCategory category, ItemLike result, int count) {
        return ShapelessRecipeBuilder.shapeless(category, result, count);
    }

    public static void addRecipe(RecipeOutput recipeOutput, RecipeBuilder builder, String criterion, ItemLike... triggers) {
        addRecipe(recipeOutput, RecipeBuilder.getDefaultRecipeId(builder.getResult()), builder, criterion, triggers);
    }

    public static void addRecipe(RecipeOutput recipeOutput, ResourceLocation name, RecipeBuilder builder, String criterion, ItemLike... triggers) {
        builder.unlockedBy(criterion, InventoryChangeTrigger.TriggerInstance.hasItems(triggers)).save(recipeOutput, name);
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

    public static void makeWoodenBlockRecipes(WoodType woodType, RecipeOutput recipeOutput) {
        if (woodType != WoodType.BAMBOO)
            makeBeamRecipeOf(getBlock(woodType, WoodDecorativeBlockTypes.BEAM), recipeOutput);
        makePalisadeRecipeOf(getBlock(woodType, WoodDecorativeBlockTypes.PALISADE), recipeOutput);
        makeSeatRecipeOf(getBlock(woodType, WoodDecorativeBlockTypes.SEAT), recipeOutput);
        makeSupportRecipeOf(getBlock(woodType, WoodDecorativeBlockTypes.SUPPORT), recipeOutput);
    }

    public static IWoodenBlock getBlock(WoodType woodType, WoodDecorativeBlockTypes type) {
        return (IWoodenBlock) BuiltInRegistries.BLOCK.stream().filter(block -> {
            if (block instanceof IWoodenBlock woodenBlock) {
                if (woodenBlock.getWoodType() == woodType) {
                    switch (type) {
                        case BEAM -> {
                            return block instanceof BeamBlock;
                        }
                        case PALISADE -> {
                            return block instanceof PalisadeBlock;
                        }
                        case SEAT -> {
                            return block instanceof SeatBlock;
                        }
                        case SUPPORT -> {
                            return block instanceof SupportBlock;
                        }
                    }
                }
            }
            return false;
        }).findFirst().orElse(null);
    }

    public static void makeChandelierRecipeOf(ItemLike chandelier, Item torch, RecipeOutput recipeOutput) {
        addRecipe(recipeOutput, shapedRecipe(RecipeCategory.DECORATIONS, chandelier, 1)
                        .group("decorative_chandeliers")
                        .pattern("##")
                        .pattern("##")
                        .define('#', torch),
                "has_torch", torch);
        String chandelierId = BuiltInRegistries.ITEM.getKey(chandelier.asItem()).getPath();
        String torchId = BuiltInRegistries.ITEM.getKey(torch).getPath();
        addRecipe(recipeOutput, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, torchId + "_from_" + chandelierId), shapelessRecipe(RecipeCategory.DECORATIONS, torch, 4)
                        .group("torches_from_chandeliers")
                        .requires(chandelier),
                "has_torch", torch);
    }


    public static void makeBeamRecipeOf(IWoodenBlock block, RecipeOutput recipeOutput) {
        WoodType wood = block.getWoodType();
        Block strippedLog = VanillaWoodTypes.getStrippedLog(wood);
        addRecipe(recipeOutput, shapedRecipe(RecipeCategory.BUILDING_BLOCKS, (Block) block, 2)
                        .group("decorative_beams")
                        .pattern(" x ")
                        .pattern(" x ")
                        .define('x', strippedLog),
                "has_stripped_log", strippedLog);
    }

    public static void makePalisadeRecipeOf(IWoodenBlock block, RecipeOutput recipeOutput) {
        WoodType wood = block.getWoodType();
        Block log = VanillaWoodTypes.getLog(wood);

        addRecipe(recipeOutput, shapedRecipe(RecipeCategory.BUILDING_BLOCKS, (Block) block, 6)
                        .group("decorative_palisades")
                        .pattern("xx ")
                        .define('x', log),
                "has_log", log);
    }

    public static void makeSeatRecipeOf(IWoodenBlock block, RecipeOutput recipeOutput) {
        WoodType wood = block.getWoodType();
        addRecipe(recipeOutput, shapedRecipe(RecipeCategory.BUILDING_BLOCKS, (Block) block, 6)
                        .group("decorative_seats")
                        .pattern("x  ")
                        .pattern("y  ")
                        .define('x', VanillaWoodTypes.getSlab(wood))
                        .define('y', VanillaWoodTypes.getFence(wood)),
                "has_plank", VanillaWoodTypes.getPlanks(wood));

    }

    public static void makeSupportRecipeOf(IWoodenBlock block, RecipeOutput recipeOutput) {
        WoodType wood = block.getWoodType();
        Block planks = VanillaWoodTypes.getLog(wood);

        addRecipe(recipeOutput, shapedRecipe(RecipeCategory.BUILDING_BLOCKS, (Block) block, 6)
                        .group("decorative_supports")
                        .pattern("xx ")
                        .pattern("x  ")
                        .define('x', planks),
                "has_plank", planks);
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
