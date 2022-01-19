package lilypuree.decorative_blocks.core;

import lilypuree.decorative_blocks.Constants;
import lilypuree.decorative_blocks.entity.DummyEntityForSitting;
import lilypuree.decorative_blocks.fluid.ThatchFluid;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.FlowingFluid;

public class Registration {
    private static final ResourceLocation thatchStillTexture = new ResourceLocation(Constants.MODID, "block/thatch_still");
    private static final ResourceLocation thatchFlowingTexture = new ResourceLocation(Constants.MODID, "block/thatch_flowing");
    public static final ThatchFluid.FluidReferenceHolder referenceHolder = new ThatchFluid.FluidReferenceHolder(() -> Blocks.HAY_BLOCK, thatchStillTexture, thatchFlowingTexture, 0xAC8D08);

    public static EntityType<DummyEntityForSitting> DUMMY_ENTITY_TYPE;

    public static FlowingFluid FLOWING_THATCH;
    public static FlowingFluid STILL_THATCH;
    public static Block THATCH;

    static {
        referenceHolder.setFlowingFluid(()->FLOWING_THATCH);
        referenceHolder.setStillFluid(()->STILL_THATCH);
        referenceHolder.setFluidBlock(()->THATCH);
    }
}
