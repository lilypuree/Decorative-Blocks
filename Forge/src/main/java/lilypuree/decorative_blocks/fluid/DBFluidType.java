package lilypuree.decorative_blocks.fluid;

import com.mojang.blaze3d.shaders.FogShape;
import lilypuree.decorative_blocks.client.FogHelper;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

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
    public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
        consumer.accept(new IClientFluidTypeExtensions() {
            @Override
            public ResourceLocation getFlowingTexture() {
                return flowingTexture;
            }

            @Override
            public ResourceLocation getStillTexture() {
                return stillTexture;
            }

            @Override
            public @Nullable ResourceLocation getOverlayTexture() {
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
