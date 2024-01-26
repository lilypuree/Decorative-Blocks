package lilypuree.decorative_blocks.mixin;

import lilypuree.decorative_blocks.CommonAPI;
import lilypuree.decorative_blocks.registration.DBTags;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {


    @Shadow public abstract ItemStack getItem();

    public ItemEntityMixin(EntityType<?> $$0, Level $$1) {
        super($$0, $$1);
    }

    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    public void onHurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (source == this.damageSources().inFire() && this.getItem().is(DBTags.Items.BONFIRE_ACTIVATORS)) {
            Level level = this.level();
            if (level.isClientSide || this.isRemoved()) cir.setReturnValue(false);
            else {
                Block block = level.getBlockState(this.blockPosition()).getBlock();
                if (CommonAPI.bonfireMap.containsKey(block)) {
                    level.setBlockAndUpdate(this.blockPosition(), CommonAPI.bonfireMap.get(block).defaultBlockState());
                    level.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 1.0f, 0.7f);
                    this.discard();
                    cir.setReturnValue(true);
                }
            }
        }
    }

}
