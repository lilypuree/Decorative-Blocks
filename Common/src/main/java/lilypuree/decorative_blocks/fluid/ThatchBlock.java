package lilypuree.decorative_blocks.fluid;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public interface ThatchBlock {
    default void playSoundIfMoving(Player player, Level level, BlockPos pos) {
        boolean isPlayerMoving = player.getDeltaMovement().length() > 0.1;
        if (level.random.nextFloat() < 0.1F && isPlayerMoving) {
            level.playSound(player, pos, SoundEvents.GRASS_HIT, SoundSource.BLOCKS, 0.8f, 1.5f);
        }
    }
}
