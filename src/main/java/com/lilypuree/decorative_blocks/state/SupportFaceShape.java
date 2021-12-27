package com.lilypuree.decorative_blocks.state;

import net.minecraft.util.IStringSerializable;

public enum SupportFaceShape implements IStringSerializable {
    BIG("big"), SMALL("small"), HIDDEN("hidden");

    private final String name;

    private SupportFaceShape(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public String getString() {
        return this.name;
    }

    public boolean isHidden() {
        return this == HIDDEN;
    }

    public SupportFaceShape getSwitched() {
        switch (this) {
            case HIDDEN:
                return HIDDEN;
            case SMALL:
                return BIG;
            case BIG:
                return SMALL;
        }
        return BIG;
    }
}
