package lilypuree.decorative_blocks.entity;

import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class EmptyRenderer extends EntityRenderer<DummyEntityForSitting> {
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
