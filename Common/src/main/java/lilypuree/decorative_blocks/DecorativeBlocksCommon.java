package lilypuree.decorative_blocks;

import lilypuree.decorative_blocks.blocks.*;
import lilypuree.decorative_blocks.core.DBBlocks;
import lilypuree.decorative_blocks.core.DBItems;
import lilypuree.decorative_blocks.core.Registration;
import lilypuree.decorative_blocks.items.BlockstateCopyItem;
import lilypuree.decorative_blocks.mixin.FireBlockInvoker;
import lilypuree.decorative_blocks.platform.Services;
import lilypuree.decorative_blocks.registration.RegistryObject;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.Blocks;

public class DecorativeBlocksCommon {


    public static void init() {
        CommonAPI.addThatchlikeFluid(Registration.referenceHolder);
        CommonAPI.bonfireMap.put(Blocks.FIRE, DBBlocks.BONFIRE.get());
        CommonAPI.bonfireMap.put(Blocks.SOUL_FIRE, DBBlocks.SOUL_BONFIRE.get());

        BuiltInRegistries.BLOCK.forEach(block -> {
            if (block instanceof PalisadeBlock) {
                BlockstateCopyItem.addProperties(block, PalisadeBlock.NORTH, PalisadeBlock.EAST, PalisadeBlock.SOUTH, PalisadeBlock.WEST);
            } else if (block instanceof SeatBlock) {
                BlockstateCopyItem.addProperties(block, SeatBlock.FACING, SeatBlock.POST, SeatBlock.ATTACHED);
            } else if (block instanceof SupportBlock) {
                BlockstateCopyItem.addProperties(block, SupportBlock.HORIZONTAL_SHAPE, SupportBlock.VERTICAL_SHAPE, SupportBlock.FACING, SupportBlock.UP);
            }
        });

        FireBlockInvoker invoker = ((FireBlockInvoker) ((Object) Blocks.FIRE));
        BuiltInRegistries.BLOCK.forEach(block -> {
            if (block instanceof IWoodenBlock woodenBlock) {
                if (woodenBlock.getWoodType().isFlammable()) {
                    invoker.invokeSetFlammable(block, 5, 20);
                }
            }
        });

        invoker.invokeSetFlammable(DBBlocks.LATTICE.get(), 5, 20);
        invoker.invokeSetFlammable(DBBlocks.THATCH.get(), 60, 80);
    }
}
