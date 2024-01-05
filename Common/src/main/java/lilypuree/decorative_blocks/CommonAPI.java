package lilypuree.decorative_blocks;

import lilypuree.decorative_blocks.fluid.ThatchFluid;
import lilypuree.decorative_blocks.platform.Services;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;
import java.util.Map;

public class CommonAPI {

    public static GameRules.Key<GameRules.BooleanValue> RULE_DISABLE_THATCH = Services.PLATFORM.registerGameRule(Constants.MOD_ID + ":disableThatch", GameRules.Category.MISC, false);

    public static Map<Block, Block> bonfireMap = new HashMap<>();

    public static Map<Block, ThatchFluid.FluidReferenceHolder> shearMap = new HashMap<>();

    public static void addThatchlikeFluid(ThatchFluid.FluidReferenceHolder referenceHolder) {
        shearMap.put(referenceHolder.getSourceBlock(), referenceHolder);
    }
}
