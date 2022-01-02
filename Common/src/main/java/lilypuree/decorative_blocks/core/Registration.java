package lilypuree.decorative_blocks.core;

import com.google.common.collect.ImmutableMap;
import lilypuree.decorative_blocks.CommonAPI;
import lilypuree.decorative_blocks.Constants;
import lilypuree.decorative_blocks.blocks.*;
import lilypuree.decorative_blocks.datagen.types.IWoodType;
import lilypuree.decorative_blocks.datagen.types.ModWoodTypes;
import lilypuree.decorative_blocks.datagen.types.VanillaWoodTypes;
import lilypuree.decorative_blocks.datagen.types.WoodDecorativeBlockTypes;
import lilypuree.decorative_blocks.entity.DummyEntityForSitting;
import lilypuree.decorative_blocks.fluid.ThatchFluid;
import lilypuree.decorative_blocks.fluid.ThatchFluidBlock;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import java.util.function.Supplier;

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
