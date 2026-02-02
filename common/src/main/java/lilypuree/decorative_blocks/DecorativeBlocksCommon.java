package lilypuree.decorative_blocks;

import lilypuree.decorative_blocks.blocks.IWoodenBlock;
import lilypuree.decorative_blocks.blocks.types.VanillaWoodTypes;
import lilypuree.decorative_blocks.mixin.FireBlockInvoker;
import lilypuree.decorative_blocks.registration.DBBlocks;
import lilypuree.decorative_blocks.registration.Registration;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Blocks;

public class DecorativeBlocksCommon {


    public static void init() {
        CommonAPI.addThatchlikeFluid(Registration.referenceHolder);
        CommonAPI.bonfireMap.put(Blocks.FIRE, DBBlocks.BONFIRE.get());
        CommonAPI.bonfireMap.put(Blocks.SOUL_FIRE, DBBlocks.SOUL_BONFIRE.get());

        FireBlockInvoker invoker = ((FireBlockInvoker) ((Object) Blocks.FIRE));
        BuiltInRegistries.BLOCK.forEach(block -> {
            if (block instanceof IWoodenBlock woodenBlock) {
                if (!VanillaWoodTypes.isNetherWood(woodenBlock.getWoodType())) {
                    invoker.invokeSetFlammable(block, 5, 20);
                }
            }
        });

        invoker.invokeSetFlammable(DBBlocks.LATTICE.get(), 5, 20);
        invoker.invokeSetFlammable(Registration.THATCH_BLOCK.get(), 60, 80);
    }
}
