package lilypuree.decorative_blocks.blocks;

import lilypuree.decorative_blocks.platform.Services;
import lilypuree.decorative_blocks.registration.DBBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class BonfireBlock extends Block implements SimpleWaterloggedBlock {
    public BonfireBlock(Properties properties) {
        super(properties);
    }

    private static final VoxelShape BONFIRE_SHAPE = Block.box(0.0, 0.0, 0.0, 16D, 2D, 16D);

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        BlockPos blockpos = pos.below();
        return worldIn.getBlockState(blockpos).isFaceSturdy(worldIn, blockpos, Direction.UP);
    }

    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return BONFIRE_SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
        if (!entityIn.fireImmune() && entityIn instanceof LivingEntity) {
            entityIn.hurt(entityIn.damageSources().inFire(), 1.0F);
            if (Services.PLATFORM.isModLoaded("soulfired")) {
                Services.SOULFIRED.setSecondsOnFire(entityIn, 3, state.is(DBBlocks.SOUL_BONFIRE.get()));
            } else
                entityIn.igniteForSeconds(3);
        }
        super.entityInside(state, worldIn, pos, entityIn);
    }


//    @Override
//    public boolean isBurning(BlockState state, BlockGetter world, BlockPos pos) {
//        return true;
//    }

//    @Override
//    public boolean addDestroyEffects(BlockState state, World world, BlockPos pos, ParticleManager manager) {
//        return true;
//    }

    @Override
    public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, RandomSource rand) {
        if (rand.nextInt(18) == 0) {
            worldIn.playLocalSound((double) ((float) pos.getX() + 0.5F), (double) ((float) pos.getY() + 0.5F), (double) ((float) pos.getZ() + 0.5F), SoundEvents.FIRE_AMBIENT, SoundSource.BLOCKS, 1.5F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.3F, false);
        }
        if (rand.nextInt(10) == 0) {
            worldIn.playLocalSound((double) ((float) pos.getX() + 0.5F), (double) ((float) pos.getY() + 0.5F), (double) ((float) pos.getZ() + 0.5F), SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS, (float) (0.6F + rand.nextFloat() * 0.3), rand.nextFloat() * 0.6F + 0.5F, false);
        }
        for (int i = 0; i < 3; ++i) {
            double x = (double) pos.getX() + rand.nextDouble();
            double y = (double) pos.getY() + rand.nextDouble() * 0.5D + 1.5D;
            double z = (double) pos.getZ() + rand.nextDouble();
            worldIn.addParticle(ParticleTypes.LARGE_SMOKE, x, y, z, -0.03 + rand.nextDouble() * 0.06, +rand.nextDouble() * 0.1, -0.03 + rand.nextDouble() * 0.06);
        }
    }
    @Override
    public boolean canPlaceLiquid(@Nullable Player player, BlockGetter worldIn, BlockPos pos, BlockState state, Fluid fluidIn) {
        return false;
    }

    @Override
    public boolean placeLiquid(LevelAccessor worldIn, BlockPos pos, BlockState state, FluidState fluidStateIn) {
        if (fluidStateIn.getType() == Fluids.WATER) {
            if (!worldIn.isClientSide()) {
                worldIn.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                worldIn.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.2f, 0.6f);
            } else {
                spawnExtinguishSmoke(worldIn, pos);
            }
            return true;
        } else
            return false;
    }


    public static void spawnExtinguishSmoke(LevelAccessor world, BlockPos pos) {
        RandomSource rand = world.getRandom();
        for (int i = 0; i < 5; ++i) {
            double d0 = world.getRandom().nextGaussian() * 0.02D;
            double d1 = world.getRandom().nextGaussian() * 0.02D;
            double d2 = world.getRandom().nextGaussian() * 0.02D;
            world.addParticle(ParticleTypes.CLOUD, pos.getX() + (double) (rand.nextFloat()), pos.getY() + 0.4D + (double) (rand.nextFloat()), pos.getZ() + (double) (rand.nextFloat()), d0, d1, d2);
        }
        world.addParticle(ParticleTypes.SMOKE, (double) pos.getX() + 0.25D + rand.nextDouble() / 2.0D * (double) (rand.nextBoolean() ? 1 : -1), (double) pos.getY() + 0.4D, (double) pos.getZ() + 0.25D + rand.nextDouble() / 2.0D * (double) (rand.nextBoolean() ? 1 : -1), 0.0D, 0.005D, 0.0D);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        return this.canSurvive(stateIn, worldIn, currentPos) ? stateIn : Blocks.AIR.defaultBlockState();
    }


}
