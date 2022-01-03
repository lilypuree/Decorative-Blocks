package lilypuree.decorative_blocks.blocks.state;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class ModBlockProperties {
    public static final EnumProperty<SupportFaceShape> HORIZONTAL_SHAPE = EnumProperty.create("horizontal", SupportFaceShape.class);
    public static final EnumProperty<SupportFaceShape> VERTICAL_SHAPE = EnumProperty.create("vertical", SupportFaceShape.class);
    public static final BooleanProperty POST = BooleanProperty.create("post");
    public static final DirectionProperty NORTH_EAST = DirectionProperty.create("north_east", Direction.NORTH, Direction.EAST);
}
