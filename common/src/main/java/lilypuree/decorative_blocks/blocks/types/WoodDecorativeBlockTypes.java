package lilypuree.decorative_blocks.blocks.types;

import lilypuree.decorative_blocks.blocks.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.WoodType;

import java.util.function.BiFunction;

public enum WoodDecorativeBlockTypes {
    BEAM("beam", BeamBlock::new),
    PALISADE("palisade", PalisadeBlock::new),
    SEAT("seat", SeatBlock::new),
    SUPPORT("support", SupportBlock::new);

    private final String name;
    private final BiFunction<WoodType, BlockBehaviour.Properties, IWoodenBlock> constructor;

    private WoodDecorativeBlockTypes(String name, BiFunction<WoodType, BlockBehaviour.Properties, IWoodenBlock> constructor) {
        this.name = name;
        this.constructor = constructor;
    }

    public String toString() {
        return this.getName();
    }

    public String getName() {
        return this.name;
    }

    public IWoodenBlock create(WoodType woodType, BlockBehaviour.Properties properties) {
        return constructor.apply(woodType, properties);
    }

}
