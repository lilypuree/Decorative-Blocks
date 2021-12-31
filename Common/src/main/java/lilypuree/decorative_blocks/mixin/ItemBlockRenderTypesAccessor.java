package lilypuree.decorative_blocks.mixin;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(ItemBlockRenderTypes.class)
public interface ItemBlockRenderTypesAccessor {
    @Accessor("TYPE_BY_BLOCK")
    public static Map<Block, RenderType> getTypeByBlock(){
        throw new AssertionError();
    }
}
