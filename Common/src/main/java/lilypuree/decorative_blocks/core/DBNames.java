package lilypuree.decorative_blocks.core;

import lilypuree.decorative_blocks.Constants;
import lilypuree.decorative_blocks.blocks.types.IWoodType;
import lilypuree.decorative_blocks.blocks.types.WoodDecorativeBlockTypes;
import net.minecraft.resources.ResourceLocation;

public class DBNames {
    public static ResourceLocation BONFIRE = create("bonfire");
    public static ResourceLocation SOUL_BONFIRE = create("soul_bonfire");
    public static ResourceLocation CHANDELIER = create("chandelier");
    public static ResourceLocation SOUL_CHANDELIER = create("soul_chandelier");
    public static ResourceLocation BRAZIER = create("brazier");
    public static ResourceLocation SOUL_BRAZIER = create("soul_brazier");
    public static ResourceLocation BAR_PANEL = create("bar_panel");
    public static ResourceLocation LATTICE = create("lattice");
    public static ResourceLocation CHAIN = create("chain");
    public static ResourceLocation STONE_PILLAR = create("stone_pillar");
    public static ResourceLocation ROCKY_DIRT = create("rocky_dirt");
    public static ResourceLocation THATCH = create("thatch");

    public static ResourceLocation BLOCKSTATE_COPY_ITEM = create("blockstate_copy_item");

    public static ResourceLocation DUMMY_ENTITY = create("dummy");

    public static ResourceLocation FLOWING_THATCH = create("flowing_thatch");
    public static ResourceLocation STILL_THATCH = create("thatch");

    protected static ResourceLocation create(String name) {
        return new ResourceLocation(Constants.MOD_ID, name);
    }

    public static ResourceLocation create(IWoodType wood, WoodDecorativeBlockTypes type) {
        return new ResourceLocation(Constants.MOD_ID, wood + "_" + type);
    }

    public static String name(IWoodType wood, WoodDecorativeBlockTypes type) {
        return wood + "_" + type;
    }
}
