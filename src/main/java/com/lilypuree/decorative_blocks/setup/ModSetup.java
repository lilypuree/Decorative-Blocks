package com.lilypuree.decorative_blocks.setup;

import com.lilypuree.decorative_blocks.Config;
import com.lilypuree.decorative_blocks.DecorativeBlocks;
import com.lilypuree.decorative_blocks.blocks.BrazierBlock;
import com.lilypuree.decorative_blocks.entity.DummyEntityForSitting;
import com.lilypuree.decorative_blocks.entity.ItemEntityBonfireActivator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = DecorativeBlocks.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModSetup {

    public static final ItemGroup ITEM_GROUP = new ItemGroup("decorative_blocks") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(Registration.BRAZIER.get());
        }
    };

    public void init(FMLCommonSetupEvent e) {
    }

    @SubscribeEvent
    public static void onServerSetUp(FMLServerStartingEvent event) {
    }

    @SubscribeEvent
    public static void onEntityMountEvent(EntityMountEvent event) {
        if (!event.getWorldObj().isRemote && event.isDismounting()) {
            Entity seat = event.getEntityBeingMounted();
            if (seat instanceof DummyEntityForSitting) {
                seat.remove();
            }
        }
    }

    @SubscribeEvent
    public static void onProjectileCollisionEvent(ProjectileImpactEvent.Throwable event) {
        ThrowableEntity potion = event.getThrowable();
        World world = potion.getEntityWorld();
        BlockPos pos = potion.getPosition();
        BlockState state = world.getBlockState(pos);
        if (world.isRemote) return;
        if (potion instanceof PotionEntity && PotionUtils.getPotionFromItem(((PotionEntity) potion).getItem()) == Potions.WATER) {
            if (state.getBlock() == Registration.BRAZIER.get() && state.get(BrazierBlock.LIT)) {
                world.setBlockState(pos, state.with(BrazierBlock.LIT, Boolean.FALSE));
                world.playSound((PlayerEntity) null, pos, SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.BLOCKS, 0.8F, 1.0F);
            }
        }
    }

    @SubscribeEvent
    public static void onLeftClickEvent(PlayerInteractEvent.LeftClickBlock event) {
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        Block block = world.getBlockState(pos).getBlock();

        if (block == Registration.BONFIRE.get()) {
            event.setCanceled(true);

            world.playEvent(null, 1009, pos, 0);
            world.removeBlock(pos, false);
        }
    }

    @SubscribeEvent
    public static void onRightClickEvent(PlayerInteractEvent.RightClickBlock event) {
        World world = event.getWorld();
        Item item = event.getItemStack().getItem();
        BlockPos pos = event.getPos();
        Block block = world.getBlockState(pos).getBlock();
        PlayerEntity player = event.getPlayer();
        if (item == Items.SHEARS && block == Blocks.HAY_BLOCK) {
            if (world.isRemote) {
                player.swingArm(event.getHand());
            } else {
                world.setBlockState(pos, Registration.THATCH.get().getDefaultState());
                world.playSound(null, pos, SoundEvents.BLOCK_CROP_BREAK, SoundCategory.BLOCKS, 0.8f, 1.0f);
                event.getItemStack().damageItem(1, event.getEntityLiving(), (p_220036_0_) -> {
                    p_220036_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
                });
            }
        }
    }

    @SubscribeEvent
    public static void onEntityDamage(LivingDamageEvent event) {
        if (event.getSource() == DamageSource.FALL) {
            LivingEntity entity = event.getEntityLiving();
            BlockPos pos = entity.getPosition();
            World world = entity.getEntityWorld();
            if (world.getFluidState(pos).getFluid() == Registration.STILL_THATCH.get().getFluid()) {
                event.setAmount(event.getAmount() * 0.2f);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerToss(ItemTossEvent event) {
        PlayerEntity player = event.getPlayer();
        ItemEntity thrownItemEntity = event.getEntityItem();
        if (bonfireActivatorItem == null) {
            if (!isBonfireActivatorConfigValueValid()) {
                if(!didSendMessage){
                    player.sendMessage(new TranslationTextComponent("message.decorative_blocks.invalid_bonfire_activator_config"));
                    didSendMessage = true;
                }
                return;
            }
        }

        if (thrownItemEntity.getItem().getItem() == bonfireActivatorItem) {
            event.setCanceled(true);
            ItemEntity bonfireActivator = new ItemEntityBonfireActivator(thrownItemEntity);
            player.getEntityWorld().addEntity(bonfireActivator);
        }
    }

    private static boolean didSendMessage = false;
    private static Item bonfireActivatorItem = null;

    public static boolean isBonfireActivatorConfigValueValid() {
        String bonfireActivator = Config.BONFIRE_ACTIVATOR.get();
        if (ResourceLocation.isResouceNameValid(bonfireActivator)) {
            ResourceLocation bonfireActivatorResourceLocation = new ResourceLocation(bonfireActivator);
            if (ForgeRegistries.ITEMS.containsKey(bonfireActivatorResourceLocation)) {
                bonfireActivatorItem = ForgeRegistries.ITEMS.getValue(bonfireActivatorResourceLocation);
                return true;
            }
        }
        return false;
    }

}
