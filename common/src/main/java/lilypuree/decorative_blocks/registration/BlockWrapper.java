package lilypuree.decorative_blocks.registration;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public record BlockWrapper<T extends Block>(Supplier<T> supplier) implements Supplier<T>, ItemLike {

    @Override
    public T get() {
        return supplier().get();
    }

    @Override
    public @NotNull Item asItem() {
        return supplier.get().asItem();
    }
}
