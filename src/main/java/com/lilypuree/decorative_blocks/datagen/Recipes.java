package com.lilypuree.decorative_blocks.datagen;

import com.lilypuree.decorative_blocks.datagen.types.WoodTypes;
import com.lilypuree.decorative_blocks.setup.Registration;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;

import java.util.function.Consumer;

public class Recipes extends RecipeProvider {
    public Recipes(DataGenerator generatorIn) {
        super(generatorIn);
    }

    private Consumer<IFinishedRecipe> consumer;

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumerIn) {
        consumer = consumerIn;
        for (WoodTypes wood : WoodTypes.values()){
            makeBeamRecipeOf(wood);
            makePalisadeRecipeOf(wood);
            makeSeatRecipeOf(wood);
            makeSupportRecipeOf(wood);
        }
        super.registerRecipes(consumerIn);
    }

    private void makeBeamRecipeOf(WoodTypes wood) {
        ShapedRecipeBuilder.shapedRecipe(Registration.getBeamBlock(wood), 2)
                .patternLine(" x ")
                .patternLine(" x ")
                .key('x', wood.getStrippedLog())
                .addCriterion("has_stripped_log", InventoryChangeTrigger.Instance.forItems(wood.getStrippedLog()))
                .build(consumer);
    }

    private void makePalisadeRecipeOf(WoodTypes wood) {
        ShapedRecipeBuilder.shapedRecipe(Registration.getPalisadeBlock(wood), 6)
                .patternLine("xx ")
                .key('x', wood.getLog())
                .addCriterion("has_log", InventoryChangeTrigger.Instance.forItems(wood.getLog()))
                .build(consumer);
    }

    private void makeSeatRecipeOf(WoodTypes wood) {
        ShapedRecipeBuilder.shapedRecipe(Registration.getSeatBlock(wood), 2)
                .patternLine("x  ")
                .patternLine("y  ")
                .key('x', wood.getSlab())
                .key('y', wood.getFence())
                .addCriterion("has_plank", InventoryChangeTrigger.Instance.forItems(wood.getPlanks()))
                .build(consumer);
    }
    private void makeSupportRecipeOf(WoodTypes wood) {
        ShapedRecipeBuilder.shapedRecipe(Registration.getSupportBlock(wood), 3)
                .patternLine("xx ")
                .patternLine("x  ")
                .key('x', wood.getPlanks())
                .addCriterion("has_plank", InventoryChangeTrigger.Instance.forItems(wood.getPlanks()))
                .build(consumer);
    }

}
