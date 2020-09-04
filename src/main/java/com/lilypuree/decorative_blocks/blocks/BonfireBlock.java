package com.lilypuree.decorative_blocks.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;

public class BonfireBlock extends Block implements IWaterLoggable {
    public BonfireBlock(Properties properties) {
        super(properties);
    }

    private static final VoxelShape BONFIRE_SHAPE = Block.makeCuboidShape(0.0, 0.0, 0.0, 16D, 2D, 16D);


    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        BlockPos blockpos = pos.down();
        return worldIn.getBlockState(blockpos).isSolidSide(worldIn, blockpos, Direction.UP);
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return BONFIRE_SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.empty();
    }

    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if (!entityIn.func_230279_az_() && entityIn instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity) entityIn)) {
            entityIn.attackEntityFrom(DamageSource.IN_FIRE, 1.0F);
        }
        super.onEntityCollision(state, worldIn, pos, entityIn);
    }

    @Override
    public boolean isBurning(BlockState state, IBlockReader world, BlockPos pos) {
        return true;
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        return 15;
    }

    @Override
    public boolean canCreatureSpawn(BlockState state, IBlockReader world, BlockPos pos, EntitySpawnPlacementRegistry.PlacementType type, @Nullable EntityType<?> entityType) {
        return false;
    }

    @Override
    public boolean addDestroyEffects(BlockState state, World world, BlockPos pos, ParticleManager manager) {
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (rand.nextInt(18) == 0) {
            worldIn.playSound((double)((float)pos.getX() + 0.5F), (double)((float)pos.getY() + 0.5F), (double)((float)pos.getZ() + 0.5F), SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 1.5F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.3F, false);
        }
        if (rand.nextInt(10) == 0) {
            worldIn.playSound((double)((float)pos.getX() + 0.5F), (double)((float)pos.getY() + 0.5F), (double)((float)pos.getZ() + 0.5F), SoundEvents.BLOCK_CAMPFIRE_CRACKLE, SoundCategory.BLOCKS, (float) (0.6F + rand.nextFloat() * 0.3), rand.nextFloat() * 0.6F + 0.5F, false);
        }
        for(int i = 0; i < 3; ++i) {
            double x = (double)pos.getX() + rand.nextDouble();
            double y = (double)pos.getY() + rand.nextDouble() * 0.5D + 1.5D;
            double z = (double)pos.getZ() + rand.nextDouble();
            worldIn.addParticle(ParticleTypes.LARGE_SMOKE, x, y, z, -0.03 + rand.nextDouble()*0.06, + rand.nextDouble()*0.1, -0.03 + rand.nextDouble()*0.06);
        }
    }

    @Override
    public boolean canContainFluid(IBlockReader worldIn, BlockPos pos, BlockState state, Fluid fluidIn) {
        return false;
    }

    @Override
    public boolean receiveFluid(IWorld worldIn, BlockPos pos, BlockState state, FluidState fluidStateIn) {
        if(fluidStateIn.getFluid() == Fluids.WATER){
            if (!worldIn.isRemote()) {
                worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
                worldIn.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.2f, 0.6f);
            } else {
                spawnExtinguishSmoke(worldIn, pos);
            }
            return true;
        }else
            return false;
    }



    public static void spawnExtinguishSmoke(IWorld world, BlockPos pos){
        Random rand = world.getRandom();
        for (int i = 0; i < 5; ++i) {
            double d0 = world.getRandom().nextGaussian() * 0.02D;
            double d1 = world.getRandom().nextGaussian() * 0.02D;
            double d2 = world.getRandom().nextGaussian() * 0.02D;
            world.addParticle(ParticleTypes.CLOUD, pos.getX() + (double) (rand.nextFloat()), pos.getY() + 0.4D + (double) (rand.nextFloat()), pos.getZ() + (double) (rand.nextFloat()), d0, d1, d2);
        }
        world.addParticle(ParticleTypes.SMOKE, (double)pos.getX() + 0.25D + rand.nextDouble() / 2.0D * (double)(rand.nextBoolean() ? 1 : -1), (double)pos.getY() + 0.4D, (double)pos.getZ() + 0.25D + rand.nextDouble() / 2.0D * (double)(rand.nextBoolean() ? 1 : -1), 0.0D, 0.005D, 0.0D);
    }


    @Override
    public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onBlockAdded(state, worldIn, pos, oldState, isMoving);
    }

    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        return this.isValidPosition(stateIn, worldIn, currentPos) ? stateIn : Blocks.AIR.getDefaultState();
    }
}
