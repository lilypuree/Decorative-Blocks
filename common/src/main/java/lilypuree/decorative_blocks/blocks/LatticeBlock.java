package lilypuree.decorative_blocks.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LatticeBlock extends TrapDoorBlock {

    private static final double d0 = 3D;
    private static final double d1 = 16D - d0;
    protected static final VoxelShape EAST_OPEN_AABB = Block.box(0.0D, 0.0D, 0.0D, d0, 16.0D, 16.0D);
    protected static final VoxelShape WEST_OPEN_AABB = Block.box(d1, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape SOUTH_OPEN_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, d0);
    protected static final VoxelShape NORTH_OPEN_AABB = Block.box(0.0D, 0.0D, d1, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape BOTTOM_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, d0, 16.0D);
    protected static final VoxelShape TOP_AABB = Block.box(0.0D, d1, 0.0D, 16.0D, 16.0D, 16.0D);

    public LatticeBlock(Properties properties) {
        super(BlockSetType.OAK, properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        if (!state.getValue(OPEN)) {
            return state.getValue(HALF) == Half.TOP ? TOP_AABB : BOTTOM_AABB;
        } else {
            switch (state.getValue(FACING)) {
                case NORTH:
                default:
                    return NORTH_OPEN_AABB;
                case SOUTH:
                    return SOUTH_OPEN_AABB;
                case WEST:
                    return WEST_OPEN_AABB;
                case EAST:
                    return EAST_OPEN_AABB;
            }
        }
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        state = state.cycle(OPEN);
        level.setBlock(pos, state, 2);
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        this.playSound(player, level, pos, state.getValue(OPEN));
        return InteractionResult.SUCCESS;
    }
}
