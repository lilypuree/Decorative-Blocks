package com.lilypuree.decorative_blocks.mixin;

import com.lilypuree.decorative_blocks.core.DBTags;
import com.lilypuree.decorative_blocks.core.setup.Registration;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.util.IItemProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Map;

@Mixin(AbstractFurnaceTileEntity.class)
public abstract class AbstractFurnaceTileEntityMixin {

    @Shadow
    protected static void addItemTagBurnTime(Map<Item, Integer> map, ITag<Item> itemTag, int burnTimeIn) {
    }

    @Shadow
    protected static void addItemBurnTime(Map<Item, Integer> map, IItemProvider itemProvider, int burnTimeIn) {
    }

    @Inject(method = "Lnet/minecraft/tileentity/AbstractFurnaceTileEntity;getBurnTimes()Ljava/util/Map;", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private static void onGetBurnTime(CallbackInfoReturnable<Map<Item, Integer>> cir, Map<Item, Integer> map){
        addItemTagBurnTime(map, DBTags.Items.BEAMS, 300);
        addItemTagBurnTime(map, DBTags.Items.PALISADES, 300);
        addItemTagBurnTime(map, DBTags.Items.SEATS, 300);
        addItemTagBurnTime(map, DBTags.Items.SUPPORTS, 300);
        addItemTagBurnTime(map, DBTags.Items.CHANDELIERS, 1600);
    }
}
