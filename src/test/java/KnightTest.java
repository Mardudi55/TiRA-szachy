import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KnightTest {
    private Figure figure;
    Chessboard board;

    @BeforeEach
    void BoardSetUp() {
        figure = new Knight();
        board = new Chessboard(8);
    }

    @Test
    void IsWorking() {
        assertEquals(8, figure.calculateAttack(new Position(4, 4), board).toArray().length);
    }

    @Test
    void IsBouncing() {

        List<Position> attacks = figure.calculateAttack(new Position(0, 0), board);
        assertTrue(attacks.contains(new Position(1, 2)));
        assertTrue(attacks.contains(new Position(2, 1)));

        attacks = figure.calculateAttack(new Position(0, 3), board);
        assertTrue(attacks.contains(new Position(1, 1)));
        assertTrue(attacks.contains(new Position(2, 2)));
        assertTrue(attacks.contains(new Position(2, 4)));
        assertTrue(attacks.contains(new Position(1, 5)));

        attacks = figure.calculateAttack(new Position(3, 0), board);
        assertTrue(attacks.contains(new Position(1, 1)));
        assertTrue(attacks.contains(new Position(2, 2)));
        assertTrue(attacks.contains(new Position(4, 2)));
        assertTrue(attacks.contains(new Position(5, 1)));

        attacks = figure.calculateAttack(new Position(1, 6), board);
        assertTrue(attacks.contains(new Position(0, 4)));
        assertTrue(attacks.contains(new Position(2, 4)));
        assertTrue(attacks.contains(new Position(3, 5)));
        assertTrue(attacks.contains(new Position(3, 7)));

        assertTrue(attacks.contains(new Position(2, 6)));
        assertTrue(attacks.contains(new Position(0, 6)));
        assertTrue(attacks.contains(new Position(1, 5)));
        assertTrue(attacks.contains(new Position(1, 7)));


        attacks = figure.calculateAttack(new Position(7, 1), board);
        assertTrue(attacks.contains(new Position(5, 0)));
        assertTrue(attacks.contains(new Position(5, 2)));
        assertTrue(attacks.contains(new Position(6, 3)));

        assertTrue(attacks.contains(new Position(6, 1)));
    }

    @Test
    void IsThereObstacle() {
        board.setFieldType(1, 5, FieldType.OBSTACLE);
        board.setFieldType(3, 5, FieldType.OBSTACLE);

        List<Position> attacks = figure.calculateAttack(new Position(1, 6), board);

        assertFalse(attacks.contains(new Position(1, 5)));
        assertFalse(attacks.contains(new Position(3, 5)));
        assertEquals(6, attacks.toArray().length);
    }

    @Test
    void IsMirrorWorking(){
        board.setFieldType(6,5 , FieldType.MIRROR_SLASH);
        board.setFieldType(5,6 , FieldType.MIRROR_BACKSLASH);
        board.setFieldType(3,6 , FieldType.MIRROR_VERTICAL);
        board.setFieldType(3,2 , FieldType.MIRROR_HORIZONTAL);

        List<Position> attacks = figure.calculateAttack(new Position(4,4 ), board);

        IO.println(attacks.toString());
        assertFalse(attacks.contains(new Position(6,5)));
        assertTrue(attacks.contains(new Position(5,3)));

    }
}
