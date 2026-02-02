package lilypuree.decorative_blocks.fluid;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import org.jetbrains.annotations.Nullable;

public class ForgeThatchFluidBlock extends LiquidBlock implements ThatchBlock {
    public ForgeThatchFluidBlock(FlowingFluid fluid, Properties properties) {
        super(fluid, properties);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn instanceof Player) {
            Player player = (Player) entityIn;
            boolean isPlayerMoving = player.getDeltaMovement().length() > 0.1;
            if (worldIn.random.nextFloat() < 0.1F && isPlayerMoving) {
                worldIn.playSound(player, pos, SoundEvents.GRASS_HIT, SoundSource.BLOCKS, 0.8f, 1.5f);
            }
        }
    }

    @Override
    public ItemStack pickupBlock(@Nullable Player pPlayer, LevelAccessor pLevel, BlockPos pPos, BlockState pState) {
        return ItemStack.EMPTY;
    }
}
