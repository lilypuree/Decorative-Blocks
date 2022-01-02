package lilypuree.decorative_blocks.blocks.state;

import net.minecraft.util.StringRepresentable;

public enum SupportFaceShape implements StringRepresentable {
    BIG("big"), SMALL("small"), HIDDEN("hidden");

    private final String name;

    private SupportFaceShape(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public String getSerializedName() {
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
