package lilypuree.decorative_blocks.mixin;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BlockTags.class)
public interface BlockTagsInvoker {

    @Invoker("bind")
    public static Tag.Named<Block> invokeBind(String name) {
        throw new AssertionError();
    }
}
