package lilypuree.decorative_blocks;

import lilypuree.decorative_blocks.fluid.ThatchFluid;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;
import java.util.Map;

public class CommonAPI {
    public static Map<Block, Block> bonfireMap = new HashMap<>();

    public static Map<Block, ThatchFluid.FluidReferenceHolder> shearMap = new HashMap<>();

    public static void addThatchlikeFluid(ThatchFluid.FluidReferenceHolder referenceHolder){
        shearMap.put(referenceHolder.getSourceBlock(), referenceHolder);
    }
}
