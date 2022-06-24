package lilypuree.decorative_blocks.fluid;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.IFluidTypeRenderProperties;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class DBFluidType extends FluidType {
    private final ResourceLocation flowingTexture;
    private final ResourceLocation stillTexture;

    private final ResourceLocation overlayTexture;

    /**
     * Default constructor.
     *
     * @param properties the general properties of the fluid type
     */
    public DBFluidType(Properties properties, ResourceLocation stillTexture, ResourceLocation flowingTexture, ResourceLocation overlayTexture) {
        super(properties);
        this.flowingTexture = flowingTexture;
        this.stillTexture = stillTexture;
        this.overlayTexture = overlayTexture;
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
        });
    }
}
