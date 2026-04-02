import java.util.List;

record Position(int col, int row) {
}

public interface Figure {
    List<Position> calculateAttack(Position position, Chessboard board);
}
