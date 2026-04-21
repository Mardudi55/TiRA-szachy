enum Color {
    WHITE, BLACK
}

record ChessBoardPosition(Figure figure, Color color) {
}

public class Chessboard {
    private final int size;
    private final Field[][] table;

    public Chessboard(int size) {
        this.size = Math.abs(size);
        this.table = new Field[this.size][this.size];

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                table[y][x] = new Field(FieldType.EMPTY);
            }
        }
    }

    public int getSize() {
        return size;
    }

    public Field getField(int x, int y) {
        if (isWithinBounds(x, y)) {
            return table[y][x];
        }
        if (x < 0 || y < 0 || x >= size || y >= size) return null;
        return null;
    }

    public void setFieldType(int x, int y, FieldType type) {
        // Sprawdzamy czy w ogóle jesteśmy na planszy
        if (isWithinBounds(x, y)) {
            Field field = table[y][x];
            // Możemy postawić lustro/przeszkodę TYLKO gdy pole jest puste
            // I nie ma na nim żadnej figury
            if (field.getType() == FieldType.EMPTY && field.getFigure() == null) {
                field.setType(type);
            }
        }
    }

    public void setFigure(int x, int y, Figure figure) {
        // Sprawdzamy czy w ogóle jesteśmy na planszy
        if (isWithinBounds(x, y)) {
            Field field = table[y][x];
            // Możemy postawić figurę TYLKO gdy na polu nie ma innej figury
            // I pole nie jest przeszkodą/lustrem (czyli jest EMPTY)
            if (field.getFigure() == null && field.getType() == FieldType.EMPTY) {
                field.setFigure(figure);
            }
        }
    }

    public boolean isWithinBounds(int x, int y) {
        return x >= 0 && x < size && y >= 0 && y < size;
    }
}