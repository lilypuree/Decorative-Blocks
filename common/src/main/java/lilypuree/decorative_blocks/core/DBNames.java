package lilypuree.decorative_blocks.core;

import lilypuree.decorative_blocks.Constants;
import lilypuree.decorative_blocks.blocks.types.IWoodType;
import lilypuree.decorative_blocks.blocks.types.WoodDecorativeBlockTypes;
import net.minecraft.resources.ResourceLocation;

public class DBNames {

    public static ResourceLocation FLOWING_THATCH = create("flowing_thatch");
    public static ResourceLocation STILL_THATCH = create("thatch");

    protected static ResourceLocation create(String name) {
        return new ResourceLocation(Constants.MOD_ID, name);
    }

    public static String name(IWoodType wood, WoodDecorativeBlockTypes type) {
        return wood + "_" + type;
    }
}
