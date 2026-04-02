import java.util.List;

enum FieldType {
    EMPTY, OBSTACLE, MIRROR_VERTICAL, MIRROR_HORIZONTAL, MIRROR_SLASH, MIRROR_BACKSLASH
}


public class Field {
    private FieldType type;
    private Figure figure;

    public Field(FieldType type) {
        this.type = type;
    }

    public FieldType getType() { return type; }
    public void setType(FieldType type) { this.type = type; }

    public Figure getFigure() { return figure; }
    public void setFigure(Figure figure) { this.figure = figure; }
}

