package lilypuree.decorative_blocks.registration;

import lilypuree.decorative_blocks.Constants;
import lilypuree.decorative_blocks.blocks.types.WoodDecorativeBlockTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.WoodType;

public class DBNames {

    public static ResourceLocation FLOWING_THATCH = create("flowing_thatch");
    public static ResourceLocation STILL_THATCH = create("thatch");

    protected static ResourceLocation create(String name) {
        return ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name);
    }

    public static String name(WoodType wood, WoodDecorativeBlockTypes type) {
        return wood.name() + "_" + type;
    }
}
