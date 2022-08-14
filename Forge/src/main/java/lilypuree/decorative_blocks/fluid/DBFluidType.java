package lilypuree.decorative_blocks.fluid;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.math.Vector3f;
import lilypuree.decorative_blocks.client.FogHelper;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.client.IFluidTypeRenderProperties;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class DBFluidType extends FluidType {
    private final ResourceLocation flowingTexture;
    private final ResourceLocation stillTexture;

    private final ResourceLocation overlayTexture;

    private final int fogColor;

    /**
     * Default constructor.
     *
     * @param properties the general properties of the fluid type
     */
    public DBFluidType(Properties properties, ResourceLocation stillTexture, ResourceLocation flowingTexture, ResourceLocation overlayTexture, int fogColor) {
        super(properties);
        this.flowingTexture = flowingTexture;
        this.stillTexture = stillTexture;
        this.overlayTexture = overlayTexture;
        this.fogColor = fogColor;
    }

    @Override
    public void initializeClient(Consumer<IFluidTypeRenderProperties> consumer) {
        consumer.accept(new IFluidTypeRenderProperties() {
            @Override
            public ResourceLocation getFlowingTexture() {
                return flowingTexture;
            }

            @Override
            public ResourceLocation getFlowingTexture(FluidStack stack) {
                return flowingTexture;
            }

            @Override
            public ResourceLocation getStillTexture(FluidStack stack) {
                return stillTexture;
            }

            @Override
            public ResourceLocation getStillTexture(FluidState state, BlockAndTintGetter getter, BlockPos pos) {
                return stillTexture;
            }

            @Override
            public @Nullable ResourceLocation getOverlayTexture() {
                return overlayTexture;
            }

            @Override
            public ResourceLocation getOverlayTexture(FluidState state, BlockAndTintGetter getter, BlockPos pos) {
                return overlayTexture;
            }

            @Override
            public @NotNull Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor) {
                FogHelper.Info colorInfo = FogHelper.decodeColor(fogColor);
                return new Vector3f(colorInfo.fogRed(), colorInfo.fogGreen(), colorInfo.fogBlue());
            }

            @Override
            public void modifyFogRender(Camera camera, FogRenderer.FogMode mode, float renderDistance, float partialTick, float nearDistance, float farDistance, FogShape shape) {
                FogHelper.onFogSetup(camera.getEntity(), farDistance);
            }
        });
    }
}
