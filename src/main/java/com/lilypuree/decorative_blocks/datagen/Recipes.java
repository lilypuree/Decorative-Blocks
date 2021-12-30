package com.lilypuree.decorative_blocks.datagen;

import com.lilypuree.decorative_blocks.blocks.*;
import com.lilypuree.decorative_blocks.datagen.types.IWoodType;
import com.lilypuree.decorative_blocks.datagen.types.ModWoodTypes;
import com.lilypuree.decorative_blocks.core.setup.Registration;
import com.lilypuree.decorative_blocks.datagen.types.VanillaWoodTypes;
import com.lilypuree.decorative_blocks.datagen.types.WoodDecorativeBlockTypes;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.crafting.ConditionalAdvancement;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;

import java.util.function.Consumer;

public class Recipes extends RecipeProvider {
    public Recipes(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumerIn) {
        for (IWoodType wood : ModWoodTypes.allWoodTypes()) {
            if (wood instanceof VanillaWoodTypes) {
                makeWoodenBlockRecipes(wood, consumerIn);
            } else {
                makeCompatBlockRecipes(wood, consumerIn);
            }
        }
    }

    public static void makeCompatBlockRecipes(IWoodType woodType, Consumer<IFinishedRecipe> recipeConsumer) {
        modConditionalRecipe(consumer -> {
            makeBeamRecipeOf(getBlock(woodType, WoodDecorativeBlockTypes.BEAM), consumer);
        }, woodType.namespace(), recipeConsumer);
        modConditionalRecipe(consumer -> {
            makePalisadeRecipeOf(getBlock(woodType, WoodDecorativeBlockTypes.PALISADE), consumer);
        }, woodType.namespace(), recipeConsumer);
        modConditionalRecipe(consumer -> {
            makeSeatRecipeOf(getBlock(woodType, WoodDecorativeBlockTypes.SEAT), consumer);
        }, woodType.namespace(), recipeConsumer);
        modConditionalRecipe(consumer -> {
            makeSupportRecipeOf(getBlock(woodType, WoodDecorativeBlockTypes.SUPPORT), consumer);
        }, woodType.namespace(), recipeConsumer);
    }

    public static void makeWoodenBlockRecipes(IWoodType woodType, Consumer<IFinishedRecipe> consumer) {
        makeBeamRecipeOf(getBlock(woodType, WoodDecorativeBlockTypes.BEAM), consumer);
        makePalisadeRecipeOf(getBlock(woodType, WoodDecorativeBlockTypes.PALISADE), consumer);
        makeSeatRecipeOf(getBlock(woodType, WoodDecorativeBlockTypes.SEAT), consumer);
        makeSupportRecipeOf(getBlock(woodType, WoodDecorativeBlockTypes.SUPPORT), consumer);
    }

    private static IWoodenBlock getBlock(IWoodType woodType, WoodDecorativeBlockTypes type) {
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

    private static void makeBeamRecipeOf(IWoodenBlock block, Consumer<IFinishedRecipe> consumer) {
        IWoodType wood = block.getWoodType();
        ShapedRecipeBuilder.shaped((Block) block, 2)
                .pattern(" x ")
                .pattern(" x ")
                .define('x', wood.getStrippedLog())
                .unlockedBy("has_stripped_log", InventoryChangeTrigger.Instance.hasItems(wood.getStrippedLog()))
                .save(consumer);
    }

    public static void makePalisadeRecipeOf(IWoodenBlock block, Consumer<IFinishedRecipe> consumer) {
        IWoodType wood = block.getWoodType();
        ShapedRecipeBuilder.shaped((Block) block, 6)
                .pattern("xx ")
                .define('x', wood.getLog())
                .unlockedBy("has_log", InventoryChangeTrigger.Instance.hasItems(wood.getLog()))
                .save(consumer);
    }

    public static void makeSeatRecipeOf(IWoodenBlock block, Consumer<IFinishedRecipe> consumer) {
        IWoodType wood = block.getWoodType();
        ShapedRecipeBuilder.shaped(((Block) block), 2)
                .pattern("x  ")
                .pattern("y  ")
                .define('x', wood.getSlab())
                .define('y', wood.getFence())
                .unlockedBy("has_plank", InventoryChangeTrigger.Instance.hasItems(wood.getPlanks()))
                .save(consumer);
    }

    public static void makeSupportRecipeOf(IWoodenBlock block, Consumer<IFinishedRecipe> consumer) {
        IWoodType wood = block.getWoodType();
        ShapedRecipeBuilder.shaped(((Block) block), 3)
                .pattern("xx ")
                .pattern("x  ")
                .define('x', wood.getPlanks())
                .unlockedBy("has_plank", InventoryChangeTrigger.Instance.hasItems(wood.getPlanks()))
                .save(consumer);
    }

    public static void modConditionalRecipe(Consumer<Consumer<IFinishedRecipe>> baseRecipeBuilder, String modid, Consumer<IFinishedRecipe> consumer) {
        ICondition modLoadedCondition = new ModLoadedCondition(modid);
        baseRecipeBuilder.accept(iFinishedRecipe -> {
            ConditionalRecipe.builder().addCondition(modLoadedCondition).addRecipe(iFinishedRecipe)
                    .setAdvancement(ConditionalAdvancement.builder().addCondition(modLoadedCondition).addAdvancement(iFinishedRecipe))
                    .build(consumer, iFinishedRecipe.getId());
        });
    }
}
