package lilypuree.decorative_blocks.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import lilypuree.decorative_blocks.core.DBTags;
import lilypuree.decorative_blocks.fluid.ThatchFluid;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FogRenderer.class)
public class FogRendererMixin {

    @Shadow
    private static float fogRed;

    @Shadow
    private static float fogGreen;

    @Shadow
    private static float fogBlue;

    @Shadow
    private static long biomeChangedTime;

    @Inject(method = "setupColor", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel$ClientLevelData;getClearColorScale()D"))
    private static void onColorFog(Camera camera, float pPartialTicks, ClientLevel pLevel, int pRenderDistanceChunks, float pBossColorModifier, CallbackInfo ci) {
        Entity entity = camera.getEntity();
        if (entity.isEyeInFluid(DBTags.Fluids.THATCH)) {

            Level world = entity.getCommandSenderWorld();
            FluidState state = world.getFluidState(entity.eyeBlockPosition());
            int color = ((ThatchFluid) state.getType()).getReferenceHolder().getColor();
            fogRed = ((float) (color >> 16 & 0xFF) / 0xFF);
            fogGreen = ((float) ((color >> 8) & 0xFF) / 0xFF);
            fogBlue = ((float) (color & 0xFF) / 0xFF);
            biomeChangedTime = -1L;
        }
    }

    @Inject(method = "setupFog*", at = @At("RETURN"))
    private static void onSetupFog(Camera camera, FogRenderer.FogMode mode, float pFarPlaneDistance, boolean pNearFog, CallbackInfo ci) {
        Entity entity = camera.getEntity();
        if (entity.isEyeInFluid(DBTags.Fluids.THATCH)) {
            float start;
            float end;

            if (entity.isSpectator()) {
                start = -8.0F;
                end = pFarPlaneDistance * 0.5F;
            } else {
                start = 0.25F;
                end = 1.0F;
            }
            RenderSystem.setShaderFogStart(start);
            RenderSystem.setShaderFogEnd(end);
        }
    }
}
