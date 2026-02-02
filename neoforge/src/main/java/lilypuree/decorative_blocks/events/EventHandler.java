package lilypuree.decorative_blocks.events;

import lilypuree.decorative_blocks.Callbacks;
import lilypuree.decorative_blocks.Constants;
import lilypuree.decorative_blocks.blocks.BrazierBlock;
import lilypuree.decorative_blocks.registration.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = Constants.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class EventHandler {

    @SubscribeEvent
    public static void onUseItem(PlayerInteractEvent.RightClickItem event) {
        InteractionResultHolder<ItemStack> result = Callbacks.onUseItem(event.getLevel(), event.getEntity(), event.getItemStack());
        if (result.getResult() != InteractionResult.PASS) {
            if (event.getLevel().isClientSide) {
                event.getEntity().swing(event.getHand());
            }
        }
    }

    @SubscribeEvent
    public static void onRightClick(PlayerInteractEvent.RightClickBlock event) {
        InteractionResult result = Callbacks.onRightClickBlock(event.getEntity(), event.getLevel(), event.getItemStack(), event.getHitVec());
        if (result != InteractionResult.PASS) {
            if (event.getLevel().isClientSide) {
                event.getEntity().swing(event.getHand());
            }
            event.setUseBlock(TriState.FALSE);
            event.setUseItem(TriState.FALSE);
        }
    }

    @SubscribeEvent
    public static void onLeftClick(PlayerInteractEvent.LeftClickBlock event) {
        if (Callbacks.onLeftClick(event.getLevel(), event.getPos())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onEntityDamage(LivingDamageEvent.Pre event) {
        if (event.getSource() == event.getEntity().damageSources().fall()) {
            LivingEntity entity = event.getEntity();
            BlockPos pos = entity.blockPosition();
            Level world = entity.getCommandSenderWorld();
            if (world.getFluidState(pos).getType() == Registration.STILL_THATCH) {
                event.setNewDamage(event.getOriginalDamage() * 0.2f);
            }
        }
    }

    @SubscribeEvent
    public static void onProjectileCollisionEvent(ProjectileImpactEvent event) {
        Projectile potion = event.getProjectile();
        Level world = potion.getCommandSenderWorld();
        BlockPos pos = potion.blockPosition();
        BlockState state = world.getBlockState(pos);
        if (world.isClientSide) return;
        if (potion instanceof ThrownPotion) {
            PotionContents contents = ((ThrownPotion) potion).getItem().get(DataComponents.POTION_CONTENTS);
            if (contents != null && contents.is(Potions.WATER) && (state.getBlock() instanceof BrazierBlock) && state.getValue(BrazierBlock.LIT)) {
                world.setBlockAndUpdate(pos, state.setValue(BrazierBlock.LIT, Boolean.FALSE));
                world.playSound((Player) null, pos, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.BLOCKS, 0.8F, 1.0F);
            }
        }
    }
}
