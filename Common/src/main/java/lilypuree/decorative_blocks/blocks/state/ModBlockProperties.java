package lilypuree.decorative_blocks.blocks.state;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class ModBlockProperties {
    public static final EnumProperty<SupportFaceShape> HORIZONTAL_SHAPE = EnumProperty.create("horizontal", SupportFaceShape.class);
    public static final EnumProperty<SupportFaceShape> VERTICAL_SHAPE = EnumProperty.create("vertical", SupportFaceShape.class);
    public static final BooleanProperty POST = BooleanProperty.create("post");
    public static final BooleanProperty NARROW = BooleanProperty.create("narrow");

    public static final IntegerProperty LEVEL_GRASS = IntegerProperty.create("level", 1, 3);
    public static final EnumProperty<NetShape> NET_SHAPE = EnumProperty.create("shape", NetShape.class);
}
