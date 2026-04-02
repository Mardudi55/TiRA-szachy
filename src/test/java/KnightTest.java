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
        board.setFieldType(5, 1, FieldType.OBSTACLE);
        board.setFieldType(5, 3, FieldType.OBSTACLE);

        List<Position> attacks = figure.calculateAttack(new Position(6, 1), board);

        assertFalse(attacks.contains(new Position(5, 1)));
        assertFalse(attacks.contains(new Position(5, 3)));
        assertEquals(6, attacks.toArray().length);
    }

    @Test
    void IsMirrorWorking(){
        board.setFieldType(6,5 , FieldType.MIRROR_SLASH);
        board.setFieldType(5,6 , FieldType.MIRROR_BACKSLASH);
        board.setFieldType(3,6 , FieldType.MIRROR_VERTICAL);
        board.setFieldType(3,2 , FieldType.MIRROR_HORIZONTAL);
        board.setFieldType(5,2 , FieldType.MIRROR_SLASH);
        board.setFieldType(7,1 , FieldType.OBSTACLE);

        List<Position> attacks = figure.calculateAttack(new Position(4,4 ), board);

        IO.println(attacks.toString());

        assertFalse(attacks.contains(new Position(6,5)));
        assertFalse(attacks.contains(new Position(5,6)));
        assertFalse(attacks.contains(new Position(3,6)));
        assertFalse(attacks.contains(new Position(3,2)));
        assertFalse(attacks.contains(new Position(7,1)));

        assertTrue(attacks.contains(new Position(7,7)));
        assertTrue(attacks.contains(new Position(5,3)));
        assertTrue(attacks.contains(new Position(2,5)));
        assertTrue(attacks.contains(new Position(2,4)));

    }
    @Test
    void IsMultipleKnightsCooperating(){
        List<Position> attacks1 = figure.calculateAttack(new Position(1,6 ), board);
        List<Position> attacks2 = figure.calculateAttack(new Position(3,7 ), board);
//        board.setFieldType(4,2 , FieldType.MIRROR_SLASH);
//        board.setFieldType(2,4 , FieldType.MIRROR_VERTICAL);

        IO.println(attacks1.toString());
        IO.println(attacks2.toString());
        assertFalse(attacks1.contains(new Position(3,7)));
        assertFalse(attacks2.contains(new Position(1,6)));




    }
}


