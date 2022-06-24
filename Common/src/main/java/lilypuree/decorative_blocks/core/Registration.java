package lilypuree.decorative_blocks.core;

import lilypuree.decorative_blocks.Constants;
import lilypuree.decorative_blocks.entity.DummyEntityForSitting;
import lilypuree.decorative_blocks.fluid.ThatchFluid;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class Registration {
    private static final ResourceLocation thatchStillTexture = new ResourceLocation(Constants.MODID, "block/thatch_still");
    private static final ResourceLocation thatchFlowingTexture = new ResourceLocation(Constants.MODID, "block/thatch_flowing");
    public static final ThatchFluid.FluidReferenceHolder referenceHolder = new ThatchFluid.FluidReferenceHolder(() -> Blocks.HAY_BLOCK, thatchStillTexture, thatchFlowingTexture, 0xAC8D08);

    public static EntityType<DummyEntityForSitting> DUMMY_ENTITY_TYPE;

    public static FlowingFluid FLOWING_THATCH;
    public static FlowingFluid STILL_THATCH;
    public static Block THATCH;

    public static final Material thatchMaterial = (new Material.Builder(MaterialColor.COLOR_YELLOW)).noCollider().nonSolid().replaceable().liquid().build();
    public static final BlockBehaviour.Properties thatchProperties = Block.Properties.of(thatchMaterial).noCollission().randomTicks().strength(100.0F).noLootTable();

    static {
        referenceHolder.setFlowingFluid(() -> FLOWING_THATCH);
        referenceHolder.setStillFluid(() -> STILL_THATCH);
        referenceHolder.setFluidBlock(() -> THATCH);
    }

    public static void registerBlocks(RegistryHelper<Block> helper) {
        DBBlocks.init();
        helper.register(THATCH, DBNames.THATCH);
        helper.register(DBBlocks.ROCKY_DIRT, DBNames.ROCKY_DIRT);
        helper.register(DBBlocks.STONE_PILLAR, DBNames.STONE_PILLAR);
        helper.register(DBBlocks.CHAIN, DBNames.CHAIN);
        helper.register(DBBlocks.BAR_PANEL, DBNames.BAR_PANEL);
        helper.register(DBBlocks.LATTICE, DBNames.LATTICE);
        helper.register(DBBlocks.CHANDELIER, DBNames.CHANDELIER);
        helper.register(DBBlocks.SOUL_CHANDELIER, DBNames.SOUL_CHANDELIER);
        helper.register(DBBlocks.BONFIRE, DBNames.BONFIRE);
        helper.register(DBBlocks.SOUL_BONFIRE, DBNames.SOUL_BONFIRE);
        helper.register(DBBlocks.BRAZIER, DBNames.BRAZIER);
        helper.register(DBBlocks.SOUL_BRAZIER, DBNames.SOUL_BRAZIER);
        DBBlocks.BEAMS.forEach((wood, block) -> helper.register(block, wood + "_beam"));
        DBBlocks.PALISADES.forEach((wood, block) -> helper.register(block, wood + "_palisade"));
        DBBlocks.SUPPORTS.forEach((wood, block) -> helper.register(block, wood + "_support"));
        DBBlocks.SEATS.forEach((wood, block) -> helper.register(block, wood + "_seat"));
    }

    public static void registerItems(RegistryHelper<Item> helper){
        DBItems.init();
        helper.register(DBItems.ROCKY_DIRT, DBNames.ROCKY_DIRT);
        helper.register(DBItems.STONE_PILLAR, DBNames.STONE_PILLAR);
        helper.register(DBItems.CHAIN, DBNames.CHAIN);
        helper.register(DBItems.BAR_PANEL, DBNames.BAR_PANEL);
        helper.register(DBItems.BRAZIER, DBNames.BRAZIER);
        helper.register(DBItems.SOUL_BRAZIER, DBNames.SOUL_BRAZIER);
        helper.register(DBItems.LATTICE, DBNames.LATTICE);
        helper.register(DBItems.CHANDELIER, DBNames.CHANDELIER);
        helper.register(DBItems.SOUL_CHANDELIER, DBNames.SOUL_CHANDELIER);
        helper.register(DBItems.BLOCKSTATE_COPY_ITEM, DBNames.BLOCKSTATE_COPY_ITEM);
        DBItems.BEAM_ITEMBLOCKS.forEach((wood, item)->helper.register(item, wood + "_beam"));
        DBItems.PALISADE_ITEMBLOCKS.forEach((wood, item)->helper.register(item, wood + "_palisade"));
        DBItems.SUPPORT_ITEMBLOCKS.forEach((wood, item)->helper.register(item, wood + "_support"));
        DBItems.SEAT_ITEMBLOCKS.forEach((wood, item)->helper.register(item, wood + "_seat"));
    }

    public static void registerFluids(RegistryHelper<Fluid> helper){
        helper.register(STILL_THATCH, DBNames.STILL_THATCH);
        helper.register(FLOWING_THATCH, DBNames.FLOWING_THATCH);
    }
}
