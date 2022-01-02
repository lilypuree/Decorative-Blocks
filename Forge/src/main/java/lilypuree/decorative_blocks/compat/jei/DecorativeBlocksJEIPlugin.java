package lilypuree.decorative_blocks.compat.jei;

import lilypuree.decorative_blocks.CommonConfig;
import lilypuree.decorative_blocks.Constants;
import lilypuree.decorative_blocks.core.Registration;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

@JeiPlugin
public class DecorativeBlocksJEIPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Constants.MODID, "main");
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        if(CommonConfig.THATCH_ENABLED){
            FluidStack thatch = new FluidStack(Registration.STILL_THATCH, 1000);
            Component message = new TranslatableComponent("wiki.decorative_blocks.thatch");
            registration.addIngredientInfo(thatch, VanillaTypes.FLUID, message);
        }
    }
}
