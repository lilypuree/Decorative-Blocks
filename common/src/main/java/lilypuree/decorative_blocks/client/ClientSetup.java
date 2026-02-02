package lilypuree.decorative_blocks.client;

import com.mojang.blaze3d.platform.InputConstants;
import lilypuree.decorative_blocks.blocks.SeatBlock;
import lilypuree.decorative_blocks.blocks.SupportBlock;
import lilypuree.decorative_blocks.blocks.state.ModBlockProperties;
import lilypuree.decorative_blocks.items.SeatItem;
import lilypuree.decorative_blocks.items.SupportItem;
import lilypuree.decorative_blocks.items.SwitchableBlockItem;
import lilypuree.decorative_blocks.platform.Services;
import lilypuree.decorative_blocks.registration.DBBlocks;
import lilypuree.decorative_blocks.registration.Registration;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

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
        Services.PLATFORM.setRenderLayer(Registration.THATCH_BLOCK.get(), RenderType.solid());
        
    }

    public static void initItemPropertyFunctions() {
        BuiltInRegistries.BLOCK.forEach(block -> {
            if (block instanceof SupportBlock) {
                Services.PLATFORM.registerItemFunc(block.asItem(), SupportItem.OVERRIDE_TAG,
                        (stack, level, entity, i) -> SwitchableBlockItem.getValueForStack(stack) ? 0.0f : 1.0f);
            } else if (block instanceof SeatBlock) {
                Services.PLATFORM.registerItemFunc(block.asItem(), SeatItem.OVERRIDE_TAG,
                        (stack, level, entity, i) -> SwitchableBlockItem.getValueForStack(stack) ? 1.0f : 0.0f);
            }
        });
    }


}
