package lilypuree.decorative_blocks.client;

import com.ibm.icu.impl.StaticUnicodeSets;
import com.mojang.blaze3d.platform.InputConstants;
import com.sun.jna.platform.unix.X11;
import lilypuree.decorative_blocks.blocks.SeatBlock;
import lilypuree.decorative_blocks.blocks.SupportBlock;
import lilypuree.decorative_blocks.core.DBBlocks;
import lilypuree.decorative_blocks.items.SeatItem;
import lilypuree.decorative_blocks.items.SupportItem;
import lilypuree.decorative_blocks.items.SwitchableBlockItem;
import lilypuree.decorative_blocks.platform.Services;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.player.KeyboardInput;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import org.lwjgl.glfw.GLFW;

import java.awt.event.KeyEvent;

public class ClientSetup {

    public static final KeyMapping switchItemState = new KeyMapping("key.decorative_blocks.switch_item_state", InputConstants.Type.KEYSYM, -1, "key.categories.inventory");

    
    public static void initRenderLayers() {
        Services.PLATFORM.setRenderLayer(DBBlocks.BAR_PANEL.get(), RenderType.cutoutMipped());
        Services.PLATFORM.setRenderLayer(DBBlocks.LATTICE.get(), RenderType.cutoutMipped());
        Services.PLATFORM.setRenderLayer(DBBlocks.BONFIRE.get(), RenderType.cutout());
        Services.PLATFORM.setRenderLayer(DBBlocks.SOUL_BONFIRE.get(), RenderType.cutout());
        Services.PLATFORM.setRenderLayer(DBBlocks.BRAZIER.get(), RenderType.cutout());
        Services.PLATFORM.setRenderLayer(DBBlocks.SOUL_BRAZIER.get(), RenderType.cutout());
        Services.PLATFORM.setRenderLayer(DBBlocks.CHANDELIER.get(), RenderType.cutoutMipped());
        Services.PLATFORM.setRenderLayer(DBBlocks.SOUL_CHANDELIER.get(), RenderType.cutoutMipped());
        Services.PLATFORM.setRenderLayer(DBBlocks.THATCH.get(), RenderType.solid());
        
    }

    public static void initItemPropertyFunctions() {
        BuiltInRegistries.BLOCK.forEach(block -> {
            if (block instanceof SupportBlock) {
                Services.PLATFORM.registerItemFunc(block.asItem(), SupportItem.OVERRIDE_TAG, (stack, level, entity, i) -> {
                    return stack.hasTag() ? stack.getTag().getInt(SupportItem.OVERRIDE_TAG.getPath()) : 0.0f;
                });
            } else if (block instanceof SeatBlock) {
                Services.PLATFORM.registerItemFunc(block.asItem(), SeatItem.OVERRIDE_TAG, (stack, level, entity, i) -> {
                    return stack.hasTag() ? stack.getTag().getInt(SeatItem.OVERRIDE_TAG.getPath()) : 0.0f;
                });
            }
        });
    }


}
