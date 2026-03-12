import java.util.List;

record Position(int row, int col){}

public interface Figure {
    List<Position> calculateAttack(int x, int y);

}
