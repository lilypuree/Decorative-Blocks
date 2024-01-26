package lilypuree.decorative_blocks.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ChandelierBlock extends Block {
    protected final VoxelShape CHANDELIER_SHAPE = Block.box(2D, 0.0D, 2D, 14D, 12D, 14D);
    private final boolean isSoul;

    public ChandelierBlock(Properties properties, boolean isSoul) {
        super(properties);
        this.isSoul = isSoul;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return CHANDELIER_SHAPE;
    }

    @Override
    public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, RandomSource rand) {
        double d0 = (double) pos.getX() + 0.5D;
        double d1 = (double) pos.getY() + 0.7D;
        double d2 = (double) pos.getZ() + 0.5D;

        double off1 = 0.1875;
        double off2 = 0.3125;
        double off3 = 0.0625;
        worldIn.addParticle(ParticleTypes.SMOKE, d0 - off1, d1, d2 - off2, 0.0D, 0.0D, 0.0D);
        worldIn.addParticle(ParticleTypes.SMOKE, d0 - off2 - off3, d1, d2 + off1 - off3, 0.0D, 0.0D, 0.0D);
        worldIn.addParticle(ParticleTypes.SMOKE, d0 + off1 - off3, d1, d2 + off2 + off3, 0.0D, 0.0D, 0.0D);
        worldIn.addParticle(ParticleTypes.SMOKE, d0 + off2, d1, d2 - off1, 0.0D, 0.0D, 0.0D);
        if (isSoul) {
            worldIn.addParticle(ParticleTypes.SOUL_FIRE_FLAME, d0 - off1, d1, d2 - off2, 0.0D, 0.0D, 0.0D);
            worldIn.addParticle(ParticleTypes.SOUL_FIRE_FLAME, d0 - off2 - off3, d1, d2 + off1 - off3, 0.0D, 0.0D, 0.0D);
            worldIn.addParticle(ParticleTypes.SOUL_FIRE_FLAME, d0 + off1 - off3, d1, d2 + off2 + off3, 0.0D, 0.0D, 0.0D);
            worldIn.addParticle(ParticleTypes.SOUL_FIRE_FLAME, d0 + off2, d1, d2 - off1, 0.0D, 0.0D, 0.0D);
        } else {
            worldIn.addParticle(ParticleTypes.FLAME, d0 - off1, d1, d2 - off2, 0.0D, 0.0D, 0.0D);
            worldIn.addParticle(ParticleTypes.FLAME, d0 - off2 - off3, d1, d2 + off1 - off3, 0.0D, 0.0D, 0.0D);
            worldIn.addParticle(ParticleTypes.FLAME, d0 + off1 - off3, d1, d2 + off2 + off3, 0.0D, 0.0D, 0.0D);
            worldIn.addParticle(ParticleTypes.FLAME, d0 + off2, d1, d2 - off1, 0.0D, 0.0D, 0.0D);
        }

    }
}
