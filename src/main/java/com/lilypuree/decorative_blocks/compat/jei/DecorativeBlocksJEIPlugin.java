package com.lilypuree.decorative_blocks.compat.jei;

import com.lilypuree.decorative_blocks.Config;
import com.lilypuree.decorative_blocks.DecorativeBlocks;
import com.lilypuree.decorative_blocks.setup.Registration;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

@JeiPlugin
public class DecorativeBlocksJEIPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(DecorativeBlocks.MODID, "main");
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        if(Config.THATCH_ENABLED.get()){
            registration.addIngredientInfo(new FluidStack(Registration.STILL_THATCH.get(), 1000), VanillaTypes.FLUID, I18n.format("wiki.decorative_blocks.thatch"));
        }
    }


}
