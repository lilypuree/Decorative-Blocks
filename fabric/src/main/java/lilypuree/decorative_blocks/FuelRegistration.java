package lilypuree.decorative_blocks;

import lilypuree.decorative_blocks.core.DBBlocks;
import lilypuree.decorative_blocks.core.DBTags;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

public class FuelRegistration {
    public static void init() {
        register(DBTags.Items.BEAMS_THAT_BURN, 300);
        register(DBTags.Items.PALISADES_THAT_BURN, 300);
        register(DBTags.Items.SEATS_THAT_BURN, 300);
        register(DBTags.Items.SUPPORTS_THAT_BURN, 300);
        register(DBTags.Items.CHANDELIERS, 1600);
        register(DBBlocks.LATTICE, 100);
    }

    private static void register(ItemLike item, int time) {
        FuelRegistry.INSTANCE.add(item, time);
    }

    private static void register(TagKey<Item> tag, int time) {
        FuelRegistry.INSTANCE.add(tag, time);
    }
}