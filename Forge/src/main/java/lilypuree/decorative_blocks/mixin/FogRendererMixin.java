package lilypuree.decorative_blocks.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import lilypuree.decorative_blocks.Constants;
import lilypuree.decorative_blocks.core.DBTags;
import lilypuree.decorative_blocks.fluid.ThatchFluid;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.FogType;
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
        Level world = camera.getEntity().getCommandSenderWorld();
        BlockPos pos = camera.getBlockPosition();
        FluidState state = world.getFluidState(pos);

        if (isEntityInHay(state)) {
            int color = ((ThatchFluid) state.getType()).getReferenceHolder().getColor();
            fogRed = ((float) (color >> 16 & 0xFF) / 0xFF);
            fogGreen = ((float) ((color >> 8) & 0xFF) / 0xFF);
            fogBlue = ((float) (color & 0xFF) / 0xFF);
            biomeChangedTime = -1L;
        }
    }

    @Inject(method = "setupFog*", at = @At("RETURN"))
    private static void onSetupFog(Camera camera, FogRenderer.FogMode mode, float pFarPlaneDistance, boolean pNearFog, float partialTicks,CallbackInfo ci) {
        Level world = camera.getEntity().getCommandSenderWorld();
        BlockPos pos = camera.getBlockPosition();
        FluidState state = world.getFluidState(pos);

        if (isEntityInHay(state)) {
            float start;
            float end;

            if (camera.getEntity().isSpectator()) {
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


    private static boolean isEntityInHay(FluidState fluidState) {
        return DBTags.Fluids.THATCH.contains(fluidState.getType());
    }

}
