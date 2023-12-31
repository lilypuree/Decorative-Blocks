package lilypuree.decorative_blocks.compat.jei;

import lilypuree.decorative_blocks.CommonConfig;
import lilypuree.decorative_blocks.Constants;
import lilypuree.decorative_blocks.config.Config;
import lilypuree.decorative_blocks.core.Registration;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

@JeiPlugin
public class DecorativeBlocksJEIPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Constants.MOD_ID, "main");
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        if (Config.get().isThatchEnabled()) {
            FluidStack thatch = new FluidStack(Registration.STILL_THATCH.get(), 1000);
            Component message = Component.translatable("wiki.decorative_blocks.thatch");
            registration.addIngredientInfo(thatch, ForgeTypes.FLUID_STACK, message);
        }
    }
}
