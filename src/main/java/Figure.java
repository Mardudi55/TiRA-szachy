import java.util.List;

record Position(int x, int y) {
}

public interface Figure {
    List<Position> calculateAttack(Position position, Chessboard board);
}
