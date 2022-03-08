package lilypuree.decorative_blocks.mixin;

import lilypuree.decorative_blocks.client.FogHelper;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
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

    @Inject(method = "setupColor", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel$ClientLevelData;getClearColorScale()F"))
    private static void onColorFog(Camera camera, float pPartialTicks, ClientLevel pLevel, int pRenderDistanceChunks, float pBossColorModifier, CallbackInfo ci) {
        FogHelper.Info info = FogHelper.onColorFog(camera);
        if (info != null) {
            fogRed = info.fogRed();
            fogBlue = info.fogBlue();
            fogGreen = info.fogGreen();
            biomeChangedTime = info.time();
        }
    }

    @Inject(method = "setupFog*", at = @At("RETURN"))
    private static void onSetupFog(Camera camera, FogRenderer.FogMode mode, float pFarPlaneDistance, boolean pNearFog, CallbackInfo ci) {
        FogHelper.onFogSetup(camera, pFarPlaneDistance);
    }
}
