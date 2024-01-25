package lilypuree.decorative_blocks.mixin;

import lilypuree.decorative_blocks.core.DBBlocks;
import lilypuree.decorative_blocks.core.DBTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Map;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class AbstractFurnaceTileEntityMixin {

    @Shadow
    private static void add(Map<Item, Integer> map, TagKey<Item> itemTag, int burnTimeIn) {
    }

    @Shadow
    private static void add(Map<Item, Integer> map, ItemLike itemProvider, int burnTimeIn) {
    }

    @Inject(method = "getFuel", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private static void onGetBurnTime(CallbackInfoReturnable<Map<Item, Integer>> cir, Map<Item, Integer> map) {
        add(map, DBTags.Items.BEAMS_THAT_BURN, 300);
        add(map, DBTags.Items.PALISADES_THAT_BURN, 300);
        add(map, DBTags.Items.SEATS_THAT_BURN, 300);
        add(map, DBTags.Items.SUPPORTS_THAT_BURN, 300);
        add(map, DBTags.Items.CHANDELIERS, 1600);
        add(map, DBBlocks.LATTICE, 100);
    }
}
