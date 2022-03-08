package lilypuree.decorative_blocks.fluid;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;

import java.util.function.Supplier;

public class ForgeThatchFluidBlock extends LiquidBlock {
    public ForgeThatchFluidBlock(Supplier<FlowingFluid> fluid, Properties properties) {
        super(fluid, properties);
    }

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
}
