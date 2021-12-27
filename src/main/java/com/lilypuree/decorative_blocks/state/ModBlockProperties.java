package com.lilypuree.decorative_blocks.state;

import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;

public class ModBlockProperties {
    public static final EnumProperty<SupportFaceShape> HORIZONTAL_SHAPE = EnumProperty.create("horizontal", SupportFaceShape.class);
    public static final EnumProperty<SupportFaceShape> VERTICAL_SHAPE = EnumProperty.create("vertical", SupportFaceShape.class);
    public static final BooleanProperty POST = BooleanProperty.create("post");
}
