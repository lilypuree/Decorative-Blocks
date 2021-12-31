package lilypuree.decorative_blocks.mixin;

import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ItemProperties.class)
public interface ItemPropertiesForgeInvoker {

    @Invoker("register")
    public static void invokeRegister(Item item, ResourceLocation rl, ItemPropertyFunction property) {
        throw new AssertionError();
    }
}
