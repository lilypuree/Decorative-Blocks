package com.lilypuree.decorative_blocks.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathNodeType;
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
    protected static final VoxelShape BRAZIER_SHAPE = Block.box(2D, 0.0D, 2D, 14D, 14D, 14D);
    protected static final VoxelShape BRAZIER_COLLISION_SHAPE = Block.box(1.5D, 0.0D, 1.5D, 14.5D, 13.5D, 14.5D);
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private final boolean isSoul;

    public BrazierBlock(Block.Properties properties, boolean isSoul) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(LIT, Boolean.TRUE).setValue(WATERLOGGED, Boolean.FALSE));
        this.isSoul = isSoul;
    }


    public void entityInside(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if (!entityIn.fireImmune() && state.getValue(LIT) && entityIn instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity) entityIn)) {
            if (entityIn.getY() >= state.getCollisionShape(worldIn, pos).max(Direction.Axis.Y) + pos.getY() - 0.1f) {
                entityIn.hurt(DamageSource.IN_FIRE, 1.0F);
            }
        }
        super.entityInside(state, worldIn, pos, entityIn);
    }


    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        IWorld iworld = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        boolean flag = iworld.getFluidState(blockpos).getType() == Fluids.WATER;
        return this.defaultBlockState().setValue(WATERLOGGED, flag).setValue(LIT, !flag);
    }

    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.getValue(WATERLOGGED)) {
            worldIn.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        }
        return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack heldItem = player.getItemInHand(handIn);
        if (state.getValue(LIT)) {
            if (heldItem.getToolTypes().contains(ToolType.SHOVEL)) {
                worldIn.playSound(null, pos, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundCategory.BLOCKS, 0.8F, 1.0F);

                worldIn.setBlockAndUpdate(pos, state.setValue(LIT, Boolean.FALSE));
                return ActionResultType.SUCCESS;
            }
        } else if (!state.getValue(WATERLOGGED)) {
            if (hit.getDirection() == Direction.UP && heldItem.getItem() == Items.FLINT_AND_STEEL || heldItem.getItem() == Items.FIRE_CHARGE) {

                SoundEvent sound = (heldItem.getItem() == Items.FIRE_CHARGE) ? SoundEvents.FIRECHARGE_USE : SoundEvents.FLINTANDSTEEL_USE;
                worldIn.playSound(null, pos, sound, SoundCategory.BLOCKS, 1.0F, worldIn.random.nextFloat() * 0.4F + 0.8F);

                worldIn.setBlockAndUpdate(pos, state.setValue(LIT, true));
                return ActionResultType.CONSUME;

            }
        }
        return super.use(state, worldIn, pos, player, handIn, hit);
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
        if (stateIn.getValue(LIT)) {
            if (rand.nextInt(10) == 0) {
                worldIn.playLocalSound((double) ((float) pos.getX() + 0.5F), (double) ((float) pos.getY() + 0.5F), (double) ((float) pos.getZ() + 0.5F), SoundEvents.CAMPFIRE_CRACKLE, SoundCategory.BLOCKS, 0.5F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.6F, false);
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
    public void onProjectileHit(World worldIn, BlockState state, BlockRayTraceResult hit, ProjectileEntity projectile) {
        if (!worldIn.isClientSide && projectile.isOnFire()) {
            Entity entity = projectile.getOwner();
            boolean flag = entity == null || entity instanceof PlayerEntity || net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(worldIn, entity);
            if (flag && !state.getValue(LIT) && !state.getValue(WATERLOGGED)) {
                BlockPos blockpos = hit.getBlockPos();
                worldIn.setBlock(blockpos, state.setValue(BlockStateProperties.LIT, Boolean.TRUE), 11);
            }
        }
    }

    @Override
    public boolean placeLiquid(IWorld worldIn, BlockPos pos, BlockState state, FluidState fluidStateIn) {
        if (!state.getValue(BlockStateProperties.WATERLOGGED) && fluidStateIn.getType() == Fluids.WATER) {
            boolean flag = state.getValue(LIT);
            if (flag) {
                worldIn.playSound((PlayerEntity) null, pos, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
            worldIn.setBlock(pos, state.setValue(WATERLOGGED, Boolean.TRUE).setValue(LIT, false), 3);
            worldIn.getLiquidTicks().scheduleTick(pos, fluidStateIn.getType(), fluidStateIn.getType().getTickDelay(worldIn));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(LIT, WATERLOGGED);
    }


    public boolean isPathfindable(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        return false;
    }

    @Nullable
    @Override
    public PathNodeType getAiPathNodeType(BlockState state, IBlockReader world, BlockPos pos, @Nullable MobEntity entity) {
        return PathNodeType.DAMAGE_FIRE;
    }
}
