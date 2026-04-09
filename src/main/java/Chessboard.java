enum Color {
    WHITE, BLACK
}

record ChessBoardPosition(Figure figure, Color color) {
}

public class Chessboard {
    private final int size;
    private final Field[][] table;

    public Chessboard(int size) {
        this.size = size;
        this.table = new Field[size][size];

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
        return null;
    }

    public void setFieldType(int x, int y, FieldType type) {
        if (isWithinBounds(x, y)) {
            table[y][x].setType(type);
        }
    }

    public boolean isWithinBounds(int x, int y) {
        return x >= 0 && x < size && y >= 0 && y < size;
    }
}