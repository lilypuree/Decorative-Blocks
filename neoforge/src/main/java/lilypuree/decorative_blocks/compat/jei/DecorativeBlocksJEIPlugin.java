package lilypuree.decorative_blocks.compat.jei;

import lilypuree.decorative_blocks.Constants;
import lilypuree.decorative_blocks.registration.Registration;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.neoforge.NeoForgeTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.fluids.FluidStack;

@JeiPlugin
public class DecorativeBlocksJEIPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "main");
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        FluidStack thatch = new FluidStack(Registration.STILL_THATCH.get(), 1000);
        Component message = Component.translatable("wiki.decorative_blocks.thatch");
        registration.addIngredientInfo(thatch, NeoForgeTypes.FLUID_STACK, message);
    }
}
