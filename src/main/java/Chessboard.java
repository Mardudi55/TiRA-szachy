import java.util.ArrayList;
import java.util.List;

enum Color {
    WHITE, BLACK
}

record ChessBoardPosition(Figure figure, Color color) {
};

public class Chessboard {
    private final int size;
    private final Field[][] table;

    public Chessboard(int size) {
        this.size = size;
        this.table = new Field[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                table[i][j] = new Field(FieldType.EMPTY);
            }
        }
    }

    public int getSize() {
        return size;
    }

    public Field getField(int row, int col) {
        if (isWithinBounds(row, col)) {
            return table[row][col];
        }
        return null;
    }

    public void setFieldType(int row, int col, FieldType type) {
        if (isWithinBounds(row, col)) {
            table[row][col].setType(type);
        }
    }

    public boolean isWithinBounds(int row, int col) {
        return row >= 0 && row < size && col >= 0 && col < size;
    }
}