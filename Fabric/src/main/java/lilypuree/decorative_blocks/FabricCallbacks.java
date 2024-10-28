package lilypuree.decorative_blocks;

import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.world.InteractionResult;

public class FabricCallbacks {
    public static void initCallbacks() {
        AttackBlockCallback.EVENT.register(((player, world, hand, pos, direction) -> {
            if (Callbacks.onLeftClick(world, pos)) {
                return InteractionResult.sidedSuccess(world.isClientSide);
            }
            return InteractionResult.PASS;
        }));
        UseBlockCallback.EVENT.register((player, world, hand, hitResult)
                -> Callbacks.onRightClickBlock(player, world, player.getItemInHand(hand), hitResult));
        UseItemCallback.EVENT.register((player, world, hand) ->
                Callbacks.onUseItem(world, player, player.getItemInHand(hand)));

    }
}
