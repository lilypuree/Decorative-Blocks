package lilypuree.decorative_blocks.core.setup;

import lilypuree.decorative_blocks.core.DBBlocks;
import lilypuree.decorative_blocks.core.Registration;
import lilypuree.decorative_blocks.entity.DummyEntityForSitting;
import lilypuree.decorative_blocks.mixin.ItemBlockRenderTypesAccessor;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class ClientSetup {

    public static void init() {
        ItemBlockRenderTypesAccessor.getTypeByBlock().put(DBBlocks.BAR_PANEL, RenderType.cutoutMipped());
        ItemBlockRenderTypesAccessor.getTypeByBlock().put(DBBlocks.LATTICE, RenderType.cutoutMipped());
        ItemBlockRenderTypesAccessor.getTypeByBlock().put(DBBlocks.BONFIRE, RenderType.cutout());
        ItemBlockRenderTypesAccessor.getTypeByBlock().put(DBBlocks.SOUL_BONFIRE, RenderType.cutout());
        ItemBlockRenderTypesAccessor.getTypeByBlock().put(DBBlocks.BRAZIER, RenderType.cutout());
        ItemBlockRenderTypesAccessor.getTypeByBlock().put(DBBlocks.SOUL_BRAZIER, RenderType.cutout());
        ItemBlockRenderTypesAccessor.getTypeByBlock().put(DBBlocks.CHANDELIER, RenderType.cutoutMipped());
        ItemBlockRenderTypesAccessor.getTypeByBlock().put(DBBlocks.SOUL_CHANDELIER, RenderType.cutoutMipped());
        ItemBlockRenderTypesAccessor.getTypeByBlock().put(Registration.THATCH, RenderType.solid());

    }

    public static class EmptyRenderer extends EntityRenderer<DummyEntityForSitting> {
        public EmptyRenderer(EntityRendererProvider.Context ctx) {
            super(ctx);
        }

        @Override
        public boolean shouldRender(DummyEntityForSitting livingEntityIn, Frustum camera, double camX, double camY, double camZ) {
            return false;
        }

        @Override
        public ResourceLocation getTextureLocation(DummyEntityForSitting entity) {
            return null;
        }
    }
}
