package lilypuree.decorative_blocks.mixin;

import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ItemTags.class)
public interface ItemTagsInvoker {

    @Invoker("bind")
    public static Tag.Named<Item> invokeBind(String name) {
        throw new AssertionError();
    }
}
