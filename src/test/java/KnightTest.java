import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KnightTest {
    private final Figure figure = new Knight();
    Chessboard board = new Chessboard(8);

    @Test
    void IsWorking() {
        IO.println(figure.calculateAttack(new Position(0, 0), board));
        assertEquals(8, figure.calculateAttack(new Position(1, 6), board).toArray().length);
    }

    @Test
    void IsBouncing() {

    }
}