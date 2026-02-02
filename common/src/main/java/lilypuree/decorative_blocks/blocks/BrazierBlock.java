package lilypuree.decorative_blocks.blocks;

import lilypuree.decorative_blocks.registration.DBTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BrazierBlock extends Block implements SimpleWaterloggedBlock {
    protected static final VoxelShape BRAZIER_SHAPE = Block.box(2D, 0.0D, 2D, 14D, 14D, 14D);
    protected static final VoxelShape BRAZIER_COLLISION_SHAPE = Block.box(2.5D, 0.0D, 2.5D, 13.5D, 13.5D, 13.5D);
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private final boolean isSoul;

    public BrazierBlock(Properties properties, boolean isSoul) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(LIT, Boolean.TRUE).setValue(WATERLOGGED, Boolean.FALSE));
        this.isSoul = isSoul;
    }

    @Override
    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
        if (!entityIn.fireImmune() && state.getValue(LIT) && entityIn instanceof LivingEntity) {
            if (entityIn.getY() >= state.getCollisionShape(worldIn, pos).max(Direction.Axis.Y) + pos.getY() - 0.1f) {
                entityIn.hurt(entityIn.damageSources().campfire(), 1.0F);
            }
        }
        super.entityInside(state, worldIn, pos, entityIn);
    }


    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        LevelAccessor iworld = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        boolean flag = iworld.getFluidState(blockpos).getType() == Fluids.WATER;
        return this.defaultBlockState().setValue(WATERLOGGED, flag).setValue(LIT, !flag);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.getValue(WATERLOGGED)) {
            worldIn.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        }
        return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (state.getValue(LIT)) {
            if (stack.getItem() instanceof ShovelItem) {
                level.playSound(null, pos, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.BLOCKS, 0.8F, 1.0F);

                level.setBlockAndUpdate(pos, state.setValue(LIT, Boolean.FALSE));
                return ItemInteractionResult.SUCCESS;
            }
        } else if (!state.getValue(WATERLOGGED)) {
            if (hit.getDirection() == Direction.UP && stack.getItem() == Items.FLINT_AND_STEEL || stack.getItem() == Items.FIRE_CHARGE) {

                SoundEvent sound = (stack.getItem() == Items.FIRE_CHARGE) ? SoundEvents.FIRECHARGE_USE : SoundEvents.FLINTANDSTEEL_USE;
                level.playSound(null, pos, sound, SoundSource.BLOCKS, 1.0F, level.random.nextFloat() * 0.4F + 0.8F);

                level.setBlockAndUpdate(pos, state.setValue(LIT, true));
                return ItemInteractionResult.SUCCESS;

            }
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hit);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return BRAZIER_SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return BRAZIER_COLLISION_SHAPE;
    }


    @Override
    public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, RandomSource rand) {
        if (stateIn.getValue(LIT)) {
            if (rand.nextInt(10) == 0) {
                worldIn.playLocalSound((double) ((float) pos.getX() + 0.5F), (double) ((float) pos.getY() + 0.5F), (double) ((float) pos.getZ() + 0.5F), SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS, 0.5F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.6F, false);
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
    public void onProjectileHit(Level worldIn, BlockState state, BlockHitResult hit, Projectile projectile) {
        BlockPos pos = hit.getBlockPos();
        if (!worldIn.isClientSide && projectile.isOnFire() && projectile.mayInteract(worldIn, pos) && !state.getValue(LIT) && !state.getValue(WATERLOGGED)) {
            worldIn.setBlock(pos, state.setValue(BlockStateProperties.LIT, Boolean.TRUE), 11);
        }
    }

    @Override
    public boolean placeLiquid(LevelAccessor worldIn, BlockPos pos, BlockState state, FluidState fluidStateIn) {
        if (!state.getValue(BlockStateProperties.WATERLOGGED) && fluidStateIn.getType() == Fluids.WATER) {
            boolean flag = state.getValue(LIT);
            if (flag) {
                worldIn.playSound((Player) null, pos, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
            
            worldIn.setBlock(pos, state.setValue(WATERLOGGED, Boolean.TRUE).setValue(LIT, false), 3);
            worldIn.scheduleTick(pos, fluidStateIn.getType(), fluidStateIn.getType().getTickDelay(worldIn));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT, WATERLOGGED);
    }

    @Override
    public boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
        return false;
    }

    public static boolean isLitBrazier(BlockState blockState) {
        return blockState.hasProperty(LIT) && blockState.is(DBTags.Blocks.BRAZIERS) && blockState.getValue(LIT);
    }
//    @Nullable
//    @Override
//    public BlockPathTypes getAiPathNodeType(BlockState state, BlockGetter world, BlockPos pos, @Nullable Mob entity) {
//        return BlockPathTypes.DAMAGE_FIRE;
//    }


}
