package lilypuree.decorative_blocks.blocks.types;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public interface IWoodType {
    String toString();

    String namespace();

    Block getLog();

    Block getStrippedLog();

    Block getSlab();

    Block getFence();

    Block getPlanks();

    default BlockBehaviour.Properties getProperties() {
        BlockBehaviour.Properties properties = BlockBehaviour.Properties.of().mapColor(getMapColor()).sound(getSoundType());
        if (isFlammable()) {
            return properties.ignitedByLava().instrument(NoteBlockInstrument.BASS);
        } else {
            return properties;
        }
    }

    default MapColor getMapColor() {
        return MapColor.WOOD;
    }

    default SoundType getSoundType() {
        return SoundType.WOOD;
    }

    boolean isAvailable();

    boolean isFlammable();
}
