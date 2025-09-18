package lilypuree.decorative_blocks.datagen;

import lilypuree.decorative_blocks.Constants;
import lilypuree.decorative_blocks.DecorativeBlocks;
import lilypuree.decorative_blocks.blocks.*;
<<<<<<< Updated upstream
import lilypuree.decorative_blocks.core.DBBlocks;
import lilypuree.decorative_blocks.blocks.types.IWoodType;
import lilypuree.decorative_blocks.blocks.types.VanillaWoodTypes;
import lilypuree.decorative_blocks.blocks.types.WoodDecorativeBlockTypes;
=======
import lilypuree.decorative_blocks.blocks.types.VanillaWoodTypes;
import lilypuree.decorative_blocks.blocks.types.WoodDecorativeBlockTypes;
import lilypuree.decorative_blocks.registration.DBBlocks;
import lilypuree.decorative_blocks.registration.DBItems;
import lilypuree.decorative_blocks.registration.DBTags;
>>>>>>> Stashed changes
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
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
<<<<<<< Updated upstream
=======
import net.minecraft.world.level.block.state.properties.WoodType;
>>>>>>> Stashed changes
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Consumer;

public class DBRecipes extends RecipeProvider {
    public DBRecipes(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
<<<<<<< Updated upstream
        for (IWoodType wood : VanillaWoodTypes.values()) {
=======
        for (WoodType wood : VanillaWoodTypes.VANILLA) {
>>>>>>> Stashed changes
            makeWoodenBlockRecipes(wood, consumer);
        }
        addRecipe(consumer, shapedRecipe(RecipeCategory.BUILDING_BLOCKS, DBBlocks.BAR_PANEL, 2)
                .pattern("##")
                .define('#', Items.IRON_BARS), "has_ingot", Items.IRON_INGOT);
        addRecipe(consumer, shapedRecipe(RecipeCategory.BUILDING_BLOCKS, DBBlocks.LATTICE, 1)
                        .pattern(" # ")
                        .pattern("# #")
                        .pattern(" # ")
                        .define('#', Items.STICK),
                "has_stick", Items.STICK);
        addRecipe(consumer, shapedRecipe(RecipeCategory.BUILDING_BLOCKS, DBBlocks.ROCKY_DIRT, 1)
                        .pattern("##")
                        .pattern("##")
                        .define('#', Items.DIRT),
                "has_dirt", Items.DIRT);
        addRecipe(consumer, shapedRecipe(RecipeCategory.DECORATIONS, DBBlocks.BRAZIER, 1)
                        .group("decorative_braziers")
                        .pattern("010")
                        .pattern(" 0 ")
                        .define('0', Items.IRON_BARS)
                        .define('1', ItemTags.COALS),
                "has_bar", Items.IRON_BARS);
        addRecipe(consumer, shapedRecipe(RecipeCategory.DECORATIONS, DBBlocks.SOUL_BRAZIER, 1)
                        .group("decorative_braziers")
                        .pattern(" 1 ")
                        .pattern("020")
                        .pattern(" 0 ")
                        .define('0', Items.IRON_BARS)
                        .define('1', ItemTags.COALS)
                        .define('2', ItemTags.SOUL_FIRE_BASE_BLOCKS),
                "has_bar", Items.IRON_BARS);
<<<<<<< Updated upstream

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(Blocks.STONE), RecipeCategory.BUILDING_BLOCKS, DBBlocks.STONE_PILLAR.get())
                .unlockedBy("has_stone", InventoryChangeTrigger.TriggerInstance.hasItems(Items.STONE)).save(consumer);

=======
        addRecipe(consumer, shapedRecipe(RecipeCategory.DECORATIONS, DBBlocks.ROPE_COIL, 1)
                        .pattern("000")
                        .pattern("000")
                        .pattern("000")
                        .define('0', Items.STRING),
                "has_string", Items.STRING);
        addRecipe(consumer, new ResourceLocation(Constants.MOD_ID, "rope_coil_to_string"), shapelessRecipe(RecipeCategory.BUILDING_BLOCKS, Items.STRING, 9)
                        .requires(DBItems.ROPE_COIL),
                "has_string", Items.STRING);

        pillarRecipe(Blocks.STONE, DBBlocks.STONE_PILLAR.get(), consumer);
        pillarRecipe(Blocks.SMOOTH_STONE, DBBlocks.SMOOTH_STONE_PILLAR.get(), consumer);
        pillarRecipe(Blocks.SANDSTONE, DBBlocks.SANDSTONE_PILLAR.get(), consumer);
        pillarRecipe(Blocks.RED_SANDSTONE, DBBlocks.RED_SANDSTONE_PILLAR.get(), consumer);
        pillarRecipe(Blocks.BLACKSTONE, DBBlocks.BLACKSTONE_PILLAR.get(), consumer);
        pillarRecipe(Blocks.BASALT, DBBlocks.BASALT_PILLAR.get(), consumer);
        pillarRecipe(Blocks.TUFF, DBBlocks.TUFF_PILLAR.get(), consumer);
        pillarRecipe(Blocks.PACKED_MUD, DBBlocks.MUD_PILLAR.get(), consumer);
>>>>>>> Stashed changes

        makeChandelierRecipeOf(DBBlocks.CHANDELIER.get(), Items.TORCH, consumer);
        makeChandelierRecipeOf(DBBlocks.SOUL_CHANDELIER.get(), Items.SOUL_TORCH, consumer);

    }

<<<<<<< Updated upstream
=======
    public static void pillarRecipe(Block ingredient, Block output, Consumer<FinishedRecipe> consumer) {
        String ingredientName = ForgeRegistries.BLOCKS.getKey(ingredient).getPath();
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ingredient), RecipeCategory.BUILDING_BLOCKS, output)
                .group("decorative_pillars")
                .unlockedBy("has_" + ingredientName, InventoryChangeTrigger.TriggerInstance.hasItems(ingredient))
                .save(consumer, RecipeBuilder.getDefaultRecipeId(output));

    }

>>>>>>> Stashed changes
    public static ShapedRecipeBuilder shapedRecipe(RecipeCategory category, ItemLike result, int count) {
        return ShapedRecipeBuilder.shaped(category, result, count);
    }

    public static ShapelessRecipeBuilder shapelessRecipe(RecipeCategory category, ItemLike result, int count) {
        return ShapelessRecipeBuilder.shapeless(category, result, count);
    }

    public static void addRecipe(Consumer<FinishedRecipe> consumer, RecipeBuilder builder, String criterion, ItemLike... triggers) {
        addRecipe(consumer, RecipeBuilder.getDefaultRecipeId(builder.getResult()), builder, criterion, triggers);
    }

    public static void addRecipe(Consumer<FinishedRecipe> consumer, ResourceLocation name, RecipeBuilder builder, String criterion, ItemLike... triggers) {
        builder.unlockedBy(criterion, InventoryChangeTrigger.TriggerInstance.hasItems(triggers)).save(consumer, name);
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

<<<<<<< Updated upstream
    public static void makeWoodenBlockRecipes(IWoodType woodType, Consumer<FinishedRecipe> consumer) {
        if (woodType != VanillaWoodTypes.BAMBOO)
=======
    public static void makeWoodenBlockRecipes(WoodType woodType, Consumer<FinishedRecipe> consumer) {
        if (woodType != WoodType.BAMBOO)
>>>>>>> Stashed changes
            makeBeamRecipeOf(getBlock(woodType, WoodDecorativeBlockTypes.BEAM), consumer);
        makePalisadeRecipeOf(getBlock(woodType, WoodDecorativeBlockTypes.PALISADE), consumer);
        makeSeatRecipeOf(getBlock(woodType, WoodDecorativeBlockTypes.SEAT), consumer);
        makeSupportRecipeOf(getBlock(woodType, WoodDecorativeBlockTypes.SUPPORT), consumer);
    }

<<<<<<< Updated upstream
    public static IWoodenBlock getBlock(IWoodType woodType, WoodDecorativeBlockTypes type) {
=======
    public static IWoodenBlock getBlock(WoodType woodType, WoodDecorativeBlockTypes type) {
>>>>>>> Stashed changes
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

    public static void makeChandelierRecipeOf(ItemLike chandelier, Item torch, Consumer<FinishedRecipe> consumer) {
        addRecipe(consumer, shapedRecipe(RecipeCategory.DECORATIONS, chandelier, 1)
                        .group("decorative_chandeliers")
                        .pattern("##")
                        .pattern("##")
                        .define('#', torch),
                "has_torch", torch);
        String chandelierId = ForgeRegistries.ITEMS.getKey(chandelier.asItem()).getPath();
        String torchId = ForgeRegistries.ITEMS.getKey(torch).getPath();
        addRecipe(consumer, new ResourceLocation(Constants.MOD_ID, torchId + "_from_" + chandelierId), shapelessRecipe(RecipeCategory.DECORATIONS, torch, 4)
                        .group("torches_from_chandeliers")
                        .requires(chandelier),
                "has_torch", torch);
    }


    public static void makeBeamRecipeOf(IWoodenBlock block, Consumer<FinishedRecipe> consumer) {
<<<<<<< Updated upstream
        IWoodType wood = block.getWoodType();
=======
        WoodType wood = block.getWoodType();
        Block strippedLog = VanillaWoodTypes.getStrippedLog(wood);
>>>>>>> Stashed changes
        addRecipe(consumer, shapedRecipe(RecipeCategory.BUILDING_BLOCKS, (Block) block, 2)
                        .group("decorative_beams")
                        .pattern(" x ")
                        .pattern(" x ")
<<<<<<< Updated upstream
                        .define('x', wood.getStrippedLog()),
                "has_stripped_log", wood.getStrippedLog());
    }

    public static void makePalisadeRecipeOf(IWoodenBlock block, Consumer<FinishedRecipe> consumer) {
        IWoodType wood = block.getWoodType();
        addRecipe(consumer, shapedRecipe(RecipeCategory.BUILDING_BLOCKS, (Block) block, 6)
                        .group("decorative_palisades")
                        .pattern("xx ")
                        .define('x', wood.getLog()),
                "has_log", wood.getLog());
    }

    public static void makeSeatRecipeOf(IWoodenBlock block, Consumer<FinishedRecipe> consumer) {
        IWoodType wood = block.getWoodType();
=======
                        .define('x', strippedLog),
                "has_stripped_log", strippedLog);
    }

    public static void makePalisadeRecipeOf(IWoodenBlock block, Consumer<FinishedRecipe> consumer) {
        WoodType wood = block.getWoodType();
        Block log = VanillaWoodTypes.getLog(wood);
        addRecipe(consumer, shapedRecipe(RecipeCategory.BUILDING_BLOCKS, (Block) block, 6)
                        .group("decorative_palisades")
                        .pattern("xx ")
                        .define('x', log),
                "has_log", log);
    }

    public static void makeSeatRecipeOf(IWoodenBlock block, Consumer<FinishedRecipe> consumer) {
        WoodType wood = block.getWoodType();
>>>>>>> Stashed changes
        addRecipe(consumer, shapedRecipe(RecipeCategory.BUILDING_BLOCKS, (Block) block, 6)
                        .group("decorative_seats")
                        .pattern("x  ")
                        .pattern("y  ")
<<<<<<< Updated upstream
                        .define('x', wood.getSlab())
                        .define('y', wood.getFence()),
                "has_plank", wood.getPlanks());
=======
                        .define('x', VanillaWoodTypes.getSlab(wood))
                        .define('y', VanillaWoodTypes.getFence(wood)),
                "has_plank", VanillaWoodTypes.getPlanks(wood));
>>>>>>> Stashed changes

    }

    public static void makeSupportRecipeOf(IWoodenBlock block, Consumer<FinishedRecipe> consumer) {
<<<<<<< Updated upstream
        IWoodType wood = block.getWoodType();
=======
        WoodType wood = block.getWoodType();
        Block planks = VanillaWoodTypes.getLog(wood);
>>>>>>> Stashed changes
        addRecipe(consumer, shapedRecipe(RecipeCategory.BUILDING_BLOCKS, (Block) block, 6)
                        .group("decorative_supports")
                        .pattern("xx ")
                        .pattern("x  ")
<<<<<<< Updated upstream
                        .define('x', wood.getPlanks()),
                "has_plank", wood.getPlanks());
=======
                        .define('x', planks),
                "has_plank", planks);
>>>>>>> Stashed changes
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
