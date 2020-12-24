package com.lilypuree.decorative_blocks.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;
import java.util.Random;

public class BrazierBlock extends Block implements IWaterLoggable {
    protected static final VoxelShape BRAZIER_SHAPE = Block.makeCuboidShape(2D, 0.0D, 2D, 14D, 14D, 14D);
    protected static final VoxelShape BRAZIER_COLLISION_SHAPE = Block.makeCuboidShape(3D, 0.0D, 3D, 15D, 14D, 15D);
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private final boolean isSoul;

    public BrazierBlock(Block.Properties properties, boolean isSoul) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(LIT, Boolean.TRUE).with(WATERLOGGED, Boolean.FALSE));
        this.isSoul = isSoul;
    }


    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if (!entityIn.isImmuneToFire() && state.get(LIT) && entityIn instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity) entityIn)) {
            entityIn.attackEntityFrom(DamageSource.IN_FIRE, 1.0F);
        }
        super.onEntityCollision(state, worldIn, pos, entityIn);
    }


    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        IWorld iworld = context.getWorld();
        BlockPos blockpos = context.getPos();
        boolean flag = iworld.getFluidState(blockpos).getFluid() == Fluids.WATER;
        return this.getDefaultState().with(WATERLOGGED, Boolean.valueOf(flag)).with(LIT, Boolean.valueOf(!flag));
    }

    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.get(WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }
        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack heldItem = player.getHeldItem(handIn);
        if (state.get(LIT)) {
            if (heldItem.getToolTypes().contains(ToolType.SHOVEL)) {

                worldIn.playSound((PlayerEntity) null, pos, SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.BLOCKS, 0.8F, 1.0F);

                worldIn.setBlockState(pos, state.with(LIT, Boolean.FALSE));
                return ActionResultType.SUCCESS;
            }
        } else if (!state.get(WATERLOGGED)) {
            if (hit.getFace() == Direction.UP && heldItem.getItem() == Items.FLINT_AND_STEEL || heldItem.getItem() == Items.FIRE_CHARGE) {

                SoundEvent sound = (heldItem.getItem() == Items.FIRE_CHARGE) ? SoundEvents.ITEM_FIRECHARGE_USE : SoundEvents.ITEM_FLINTANDSTEEL_USE;
                worldIn.playSound((PlayerEntity) null, pos, sound, SoundCategory.BLOCKS, 1.0F, worldIn.rand.nextFloat() * 0.4F + 0.8F);

                worldIn.setBlockState(pos, state.with(LIT, Boolean.TRUE));
//                if (player != null) {
//                    heldItem.damageItem(1, player, (p_219998_1_) -> {
//                        p_219998_1_.sendBreakAnimation(handIn);
//                    });
//                }
                return ActionResultType.CONSUME;

            }
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return BRAZIER_SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return BRAZIER_COLLISION_SHAPE;
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (stateIn.get(LIT)) {
            if (rand.nextInt(10) == 0) {
                worldIn.playSound((double) ((float) pos.getX() + 0.5F), (double) ((float) pos.getY() + 0.5F), (double) ((float) pos.getZ() + 0.5F), SoundEvents.BLOCK_CAMPFIRE_CRACKLE, SoundCategory.BLOCKS, 0.5F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.6F, false);
            }

            if (rand.nextInt(5) == 0) {
                for (int i = 0; i < rand.nextInt(1) + 1; ++i) {
                    if (isSoul) {
                        worldIn.addParticle(ParticleTypes.SOUL, pos.getX() + 0.5f, pos.getY() + 0.8f, pos.getZ() + 0.5f, ((rand.nextFloat() - 0.5f) / 10.0f), (rand.nextFloat()) / 5.0f, ((rand.nextFloat() - 0.5) / 10.0f));
//                        worldIn.addParticle(ParticleTypes.SOUL, (double) ((float) pos.getX() + 0.5F), (double) ((float) pos.getY() + 0.8F), (double) ((float) pos.getZ() + 0.5F), (double) (rand.nextFloat() / 10.0F), 5.0E-5D, (double) (rand.nextFloat() / 10.0F));
                    } else {
                        worldIn.addParticle(ParticleTypes.LAVA, pos.getX() + 0.5F, pos.getY() + 0.8F, pos.getZ() + 0.5F, (rand.nextFloat() / 2.0F), 5.0E-5D, rand.nextFloat() / 2.0F);
                    }
                }
            }

        }
    }

    @Override
    public void onProjectileCollision(World worldIn, BlockState state, BlockRayTraceResult hit, ProjectileEntity projectile) {
        if (!worldIn.isRemote && projectile.isBurning()) {
            Entity entity = projectile.func_234616_v_();
            boolean flag = entity == null || entity instanceof PlayerEntity || net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(worldIn, entity);
            if (flag && !state.get(LIT) && !state.get(WATERLOGGED)) {
                BlockPos blockpos = hit.getPos();
                worldIn.setBlockState(blockpos, state.with(BlockStateProperties.LIT, Boolean.TRUE), 11);
            }
        }
    }

    @Override
    public boolean receiveFluid(IWorld worldIn, BlockPos pos, BlockState state, FluidState fluidStateIn) {
        if (!state.get(BlockStateProperties.WATERLOGGED) && fluidStateIn.getFluid() == Fluids.WATER) {
            boolean flag = state.get(LIT);
            if (flag) {
                worldIn.playSound((PlayerEntity) null, pos, SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
            worldIn.setBlockState(pos, state.with(WATERLOGGED, Boolean.TRUE).with(LIT, Boolean.valueOf(false)), 3);
            worldIn.getPendingFluidTicks().scheduleTick(pos, fluidStateIn.getFluid(), fluidStateIn.getFluid().getTickRate(worldIn));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(LIT, WATERLOGGED);
    }


    public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        return false;
    }

}
