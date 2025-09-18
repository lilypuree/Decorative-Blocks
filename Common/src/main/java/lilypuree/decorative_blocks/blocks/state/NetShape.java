package lilypuree.decorative_blocks.blocks.state;

import net.minecraft.util.StringRepresentable;

public enum NetShape implements StringRepresentable {
    EMPTY("empty"), POINTED("pointed"), SQUARE("square");
    private final String name;

    NetShape(String name) {
        this.name = name;
    }


    @Override
    public String getSerializedName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
