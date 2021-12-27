package com.lilypuree.decorative_blocks.core.setup;

import com.lilypuree.decorative_blocks.Config;
import com.lilypuree.decorative_blocks.DecorativeBlocks;
import com.lilypuree.decorative_blocks.blocks.*;
import com.lilypuree.decorative_blocks.datagen.types.IWoodType;
import com.lilypuree.decorative_blocks.entity.DummyEntityForSitting;
import com.lilypuree.decorative_blocks.entity.ItemEntityBonfireActivator;
import com.lilypuree.decorative_blocks.fluid.ThatchFluid;
import com.lilypuree.decorative_blocks.fluid.ThatchFluidBlock;
import com.lilypuree.decorative_blocks.items.BlockstateCopyItem;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Mod.EventBusSubscriber(modid = DecorativeBlocks.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModSetup {

    public static final ItemGroup ITEM_GROUP = new ItemGroup("decorative_blocks") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(Registration.BRAZIER.get());
        }
    };

    public void init(FMLCommonSetupEvent e) {
        ThatchFluid.shearMap.put(Registration.referenceHolder.getSourceBlock(), Registration.referenceHolder);
        Registry.BLOCK.forEach(block -> {
            if (block instanceof PalisadeBlock) {
                BlockstateCopyItem.addProperties(block, PalisadeBlock.NORTH, PalisadeBlock.EAST, PalisadeBlock.SOUTH, PalisadeBlock.WEST);
            } else if (block instanceof SeatBlock) {
                BlockstateCopyItem.addProperties(block, SeatBlock.HORIZONTAL_FACING, SeatBlock.POST, SeatBlock.ATTACHED);
            } else if (block instanceof SupportBlock) {
                BlockstateCopyItem.addProperties(block, SupportBlock.HORIZONTAL_SHAPE, SupportBlock.VERTICAL_SHAPE, SupportBlock.HORIZONTAL_FACING, SupportBlock.UP);
            }
        });

        e.enqueueWork(() -> {
            Method setFireInfo = ObfuscationReflectionHelper.findMethod(FireBlock.class, "func_180686_a", Block.class, int.class, int.class);
            Registry.BLOCK.forEach(block -> {
                if (block instanceof IWoodenBlock) {
                    IWoodType woodType = ((IWoodenBlock) block).getWoodType();
                    if (woodType.isFlammable()) {
                        try {
                            setFireInfo.invoke(Blocks.FIRE, block, 5, 20);
                        } catch (IllegalAccessException | InvocationTargetException exception) {
                            exception.printStackTrace();
                        }
                    }
                }
            });
            try {
                setFireInfo.invoke(Blocks.FIRE, Registration.LATTICE.get(), 5, 20);
                setFireInfo.invoke(Blocks.FIRE, Registration.THATCH.get(), 60, 80);
            } catch (IllegalAccessException | InvocationTargetException exception) {
                exception.printStackTrace();
            }
        });
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
            if ((state.getBlock() instanceof BrazierBlock) && state.get(BrazierBlock.LIT)) {
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

        if (block instanceof BonfireBlock) {
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
        if (!world.isBlockLoaded(pos)) {
            return;
        }
        Block block = world.getBlockState(pos).getBlock();
        PlayerEntity player = event.getPlayer();
        if (item == Items.SHEARS && ThatchFluid.shearMap.containsKey(block)) {
            if (world.isRemote) {
                player.swingArm(event.getHand());
            } else if (Config.THATCH_ENABLED.get()) {
                world.setBlockState(pos, ThatchFluid.shearMap.get(block).getFluidBlock().getDefaultState());
                world.playSound(null, pos, SoundEvents.BLOCK_CROP_BREAK, SoundCategory.BLOCKS, 0.8f, 1.0f);
                event.getItemStack().damageItem(1, event.getEntityLiving(), (p_220036_0_) -> {
                    p_220036_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
                });
            }
        }

        if (item instanceof HoeItem) {
            RayTraceResult rayTraceResult = Item.rayTrace(world, player, RayTraceContext.FluidMode.SOURCE_ONLY);
            if (rayTraceResult.getType() != RayTraceResult.Type.BLOCK) {
                return;
            }

            BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult) rayTraceResult;
            BlockPos blockpos = blockraytraceresult.getPos();
            Direction direction = blockraytraceresult.getFace();
            BlockPos blockpos1 = blockpos.offset(direction);

            if (world.isBlockModifiable(player, blockpos) && player.canPlayerEdit(blockpos1, direction, event.getItemStack())) {
                BlockState blockstate1 = world.getBlockState(blockpos);
                if (blockstate1.getBlock() instanceof ThatchFluidBlock) {
                    event.setCanceled(true);
                    if (blockstate1.get(FlowingFluidBlock.LEVEL) == 0) {
                        if (world.isRemote()) {
                            player.swingArm(event.getHand());
                        } else {
                            world.playSound(null, blockpos, SoundEvents.BLOCK_CROP_BREAK, SoundCategory.BLOCKS, 0.8f, 1.0f);
                            world.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 11);
                        }
                    }
                }
            }
        }
        if (block instanceof SupportBlock && item.getToolTypes(event.getItemStack()).contains(ToolType.AXE)) {
            event.setUseBlock(Event.Result.ALLOW);
        }
        if (item == Registration.BLOCKSTATE_COPY_ITEM.get()){
            event.setUseBlock(Event.Result.DENY);
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
                if (!didSendMessage) {
                    player.sendMessage(new TranslationTextComponent("message.decorative_blocks.invalid_bonfire_activator_config"), player.getUniqueID());
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
        ResourceLocation bonfireActivatorResourceLocation = ResourceLocation.tryCreate(bonfireActivator);
        if (bonfireActivatorResourceLocation != null) {
            if (ForgeRegistries.ITEMS.containsKey(bonfireActivatorResourceLocation)) {
                bonfireActivatorItem = ForgeRegistries.ITEMS.getValue(bonfireActivatorResourceLocation);
                return true;
            }
        }
        return false;
    }

}
