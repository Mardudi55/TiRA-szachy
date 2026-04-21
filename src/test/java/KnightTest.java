import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * K - skoczek
 * X - punkt ataku
 * # - przeszkoda
 * /, \, |, -, - lustra
 *
 */
class KnightTest {
    private Figure figure;
    private Chessboard board;

    @BeforeEach
    void setUp() {
        figure = new Knight();
        board = new Chessboard(8);
    }

    /**
     * (4,4)
     * . 0 1 2 3 4 5 6 7
     * 0 . . . . . . . .
     * 1 . . . . . . . .
     * 2 . . . X . X . .
     * 3 . . X . . . X .
     * 4 . . . . K . . .
     * 5 . . X . . . X .
     * 6 . . . X . X . .
     * 7 . . . . . . . .
     */
    @Test
    void shouldCalculateEightAttacksFromCenter() {
        List<Position> attacks = figure.calculateAttack(new Position(4, 4), board);

        assertThat(attacks).hasSize(8);
    }

    /**
     * (0,0)
     * . 0 1 2 3 4 5 6 7
     * 0 K . . . . . . .
     * 1 . . X . . . . .
     * 2 . X . . . . . .
     * 3 . . . . . . . .
     * 4 . . . . . . . .
     * 5 . . . . . . . .
     * 6 . . . . . . . .
     * 7 . . . . . . . .
     *
     * (0,3)
     * . 0 1 2 3 4 5 6 7
     * 0 . . . . . . . .
     * 1 . X . . . . . .
     * 2 . . X . . . . .
     * 3 K . . . . . . .
     * 4 . . X . . . . .
     * 5 . X . . . . . .
     * 6 . . . . . . . .
     * 7 . . . . . . . .
     *
     * (1,6)
     * . 0 1 2 3 4 5 6 7
     * 0 . . . . . . . .
     * 1 . . . . . . . .
     * 2 . . . . . . . .
     * 3 . . . . . . . .
     * 4 X . X . . . . .
     * 5 . X . X . . . .
     * 6 X K X . . . . .
     * 7 . X . X . . . .
     */
    @Test
    void shouldBounceOffBoardEdgesCorrectly() {
        assertThat(figure.calculateAttack(new Position(0, 0), board))
                .hasSize(2)
                .containsExactlyInAnyOrder(new Position(1, 2), new Position(2, 1));

        assertThat(figure.calculateAttack(new Position(0, 3), board))
                .hasSize(4)
                .contains(new Position(1, 1), new Position(2, 2), new Position(2, 4), new Position(1, 5));

        List<Position> attacksBottom = figure.calculateAttack(new Position(1, 6), board);
        assertThat(attacksBottom)
                .hasSize(8)
                .contains(new Position(0, 4), new Position(3, 5), new Position(1, 7));
    }

    /**
     * Wizualizacja: Przeszkody (#) na polach docelowych
     * . 0 1 2 3 4 5 6 7
     * 0 . . . . X . X .
     * 1 . . . . . # K X  <- K(6,1), Obstacle(5,1)
     * 2 . . . . X . X .
     * 3 . . . . . # . X  <- Obstacle(5,3)
     * 4 . . . . . . . .
     * 5 . . . . . . . .
     * 6 . . . . . . . .
     * 7 . . . . . . . .
     */
    @Test
    void shouldStopAtObstaclesAndNotContainThem() {
        board.setFieldType(5, 1, FieldType.OBSTACLE);
        board.setFieldType(5, 3, FieldType.OBSTACLE);

        List<Position> attacks = figure.calculateAttack(new Position(6, 1), board);

        assertThat(attacks)
                .hasSize(6)
                .doesNotContain(new Position(5, 1), new Position(5, 3))
                .contains(new Position(4, 0), new Position(6, 0),
                        new Position(4, 2), new Position(6, 0),
                        new Position(7, 1), new Position(7, 3));
    }

    /**
     * Wizualizacja: Złożona mechanika luster
     * . 0 1 2 3 4 5 6 7
     * 0 . . . . . . . .
     * 1 . . . . . . . #  <- Obstacle(7,1)
     * 2 . . . - . / . .  <- Mirror-(3,2), Mirror/(5,2)
     * 3 . . . . . X . .  <- Wynik(5,3) z odbicia
     * 4 . . X . K . . .  <- Wynik(2,4) z odbicia, K(4,4)
     * 5 . . X . . . / .  <- Wynik(2,5) z odbicia, Mirror/(6,5)
     * 6 . . . | . \ . .  <- Mirror|(3,6), Mirror\(5,6)
     * 7 . . . . . . . X  <- Wynik(7,7) z odbicia
     */
    @Test
    void shouldHandleComplexMirrorSetup() {
        board.setFieldType(6, 5, FieldType.MIRROR_SLASH);
        board.setFieldType(5, 6, FieldType.MIRROR_BACKSLASH);
        board.setFieldType(3, 6, FieldType.MIRROR_VERTICAL);
        board.setFieldType(3, 2, FieldType.MIRROR_HORIZONTAL);
        board.setFieldType(5, 2, FieldType.MIRROR_SLASH);
        board.setFieldType(7, 1, FieldType.OBSTACLE);

        List<Position> attacks = figure.calculateAttack(new Position(4, 4), board);

        assertThat(attacks).doesNotContain(
                new Position(6, 5), new Position(5, 6),
                new Position(3, 6), new Position(3, 2),
                new Position(7, 1)
        );

        assertThat(attacks).contains(
                new Position(7, 7), new Position(5, 3),
                new Position(2, 5), new Position(2, 4)
        );
    }

    /**
     * Wizualizacja: Inna figura (F) blokuje pole
     * . 0 1 2 3 4 5 6 7
     * 0 . . . . . . . .
     * 1 . . . . . . . .
     * 2 . . X . . . . .  <- X(2,2)
     * 3 . . . . | . . .  <- Mirror|(4,3)
     * 4 . . / . . . . .  <- Mirror/(2,4)
     * 5 . . . . . . . .
     * 6 . K . . . . . .  <- K(1,6)
     * 7 . . . F . . . .  <- F(3,7) - pole zajęte
     */
    @Test
    void shouldNotJumpOnOtherFigures() {
        board.setFieldType(2, 4, FieldType.MIRROR_SLASH);
        board.setFieldType(4, 3, FieldType.MIRROR_VERTICAL);

        board.setFigure(1, 6, new Knight());
        board.setFigure(3, 7, new Knight());

        List<Position> attacksFrom16 = figure.calculateAttack(new Position(1, 6), board);
        List<Position> attacksFrom37 = figure.calculateAttack(new Position(3, 7), board);

        assertThat(attacksFrom37).contains(new Position(2, 5));
        assertThat(attacksFrom16).doesNotContain(new Position(3, 7))
                .contains(new Position(2, 2));
    }

    /**
     * 1. Start (4, 4) + wektor (1, 2)   -> ląduje na (5, 6)
     * 2. Na (5, 6) lustro '-' odwraca Y -> wektor (1, -2)  -> ląduje na (6, 4)
     * 3. Na (6, 4) lustro '|' odwraca X -> wektor (-1, -2) -> ląduje na (5, 2)
     * 4. Na (5, 2) lustro '-' odwraca Y -> wektor (-1, 2)  -> ląduje na (4, 4)
     * 5. Na (4, 4) lustro '|' odwraca X -> wraca wektor (1, 2).
     * . 0 1 2 3 4 5 6 7
     * 0 . . . . . . . .
     * 1 . . . . . . . .
     * 2 . . . . . - . .  <- Lustro Poziome (5,2)
     * 3 . . . . . . . .
     * 4 . . . . K . | .  <- Start K(4,4) jest też Lustrem Pionowym! Lustro Pionowe (6,4)
     * 5 . . . . . . . .
     * 6 . . . . . - . .  <- Lustro Poziome (5,6)
     * 7 . . . . . . . .
     */
    @Test
    void shouldPreventInfiniteLoopsWithMirrors() {
        board.setFieldType(4, 4, FieldType.MIRROR_VERTICAL);
        board.setFieldType(5, 6, FieldType.MIRROR_HORIZONTAL);
        board.setFieldType(6, 4, FieldType.MIRROR_VERTICAL);
        board.setFieldType(5, 2, FieldType.MIRROR_HORIZONTAL);

        assertTimeoutPreemptively(java.time.Duration.ofSeconds(1), () -> {
            List<Position> attacks = figure.calculateAttack(new Position(4, 4), board);
            assertThat(attacks).isNotNull();
        }, "Wykryto nieskończoną pętlę w lustrach!");
    }

    @Test
    void IsOutOfTheBoard(){
        board.setFigure(8,8,new Knight());
        Field spot=board.getField(8,8);
        assertNull(spot);
    }

//    @Test
//    void IsTheSamePlacedFigure(){
//        board.setFigure(5,6,new Knight());
//        board.setFieldType(5,6,FieldType.MIRROR_VERTICAL);
//        assertFalse(board.getField(5,6).equals(FieldType.MIRROR_VERTICAL));
//    }

    @Test
    void HandleNegativeCoordinates(){
        board.setFigure(-2,-1,new Knight());
        board.setFieldType(-5, 2, FieldType.OBSTACLE);

        assertNull(board.getField(-2, -1));
        assertNull(board.getField(-5, 2));
    }
    // --- SKOCZKA ---

    @Test
    void shouldNotOverwriteExistingKnightWithAnotherKnight() {
        Figure knight1 = new Knight();
        Figure knight2 = new Knight();
        board.setFigure(3, 3, knight1);
        board.setFigure(3, 3, knight2);

        assertSame(knight1, board.getField(3, 3).getFigure());
    }

    @Test
    void shouldNotPlaceKnightOnMirror() {
        board.setFieldType(7, 2, FieldType.MIRROR_HORIZONTAL);
        board.setFigure(7, 2, new Knight());

        assertNull(board.getField(7, 2).getFigure());
        assertEquals(FieldType.MIRROR_HORIZONTAL, board.getField(7, 2).getType());
    }

    @Test
    void shouldNotOverwriteObstacleWithKnight() {
        board.setFieldType(5, 5, FieldType.OBSTACLE);
        board.setFigure(5, 5, new Knight());

        assertNull(board.getField(5, 5).getFigure());
        assertEquals(FieldType.OBSTACLE, board.getField(5, 5).getType());
    }

    // --- PRZESZKODY ---

    @Test
    void shouldNotOverwriteObstacleWithObstacle() {
        board.setFieldType(7, 0, FieldType.OBSTACLE);
        board.setFieldType(7, 0, FieldType.OBSTACLE);

        assertEquals(FieldType.OBSTACLE, board.getField(7, 0).getType());
    }

    @Test
    void shouldNotOverwriteKnightWithObstacle() {
        board.setFigure(4, 4, new Knight());
        board.setFieldType(4, 4, FieldType.OBSTACLE);

        assertEquals(FieldType.EMPTY, board.getField(4, 4).getType());
        assertNotNull(board.getField(4, 4).getFigure());
    }

    @Test
    void shouldNotOverwriteMirrorWithObstacle() {
        board.setFieldType(7, 1, FieldType.MIRROR_SLASH);
        board.setFieldType(7, 1, FieldType.OBSTACLE);

        assertEquals(FieldType.MIRROR_SLASH, board.getField(7, 1).getType());
    }

    // --- LUSTRA ---

    @Test
    void shouldNotOverwriteMirrorWithAnotherMirror() {
        board.setFieldType(1, 1, FieldType.MIRROR_SLASH);
        board.setFieldType(1, 1, FieldType.MIRROR_HORIZONTAL);

        assertEquals(FieldType.MIRROR_SLASH, board.getField(1, 1).getType());
    }

    @Test
    void shouldNotHandleMirrorOnKnight() {
        board.setFigure(6, 6, new Knight());
        board.setFieldType(6, 6, FieldType.MIRROR_BACKSLASH);

        assertEquals(FieldType.EMPTY, board.getField(6, 6).getType());
        assertNotNull(board.getField(6, 6).getFigure());
    }

    @Test
    void shouldNotOverwriteObstacleWithMirror() {
        board.setFieldType(2, 2, FieldType.OBSTACLE);
        board.setFieldType(2, 2, FieldType.MIRROR_VERTICAL);

        assertEquals(FieldType.OBSTACLE, board.getField(2, 2).getType());
    }

    @Test
    void shouldNotOverwriteMirrorSlash() {
        board.setFieldType(0, 0, FieldType.MIRROR_SLASH);

        board.setFieldType(0, 0, FieldType.MIRROR_BACKSLASH);
        board.setFieldType(0, 0, FieldType.MIRROR_VERTICAL);
        board.setFieldType(0, 0, FieldType.MIRROR_HORIZONTAL);

        assertEquals(FieldType.MIRROR_SLASH, board.getField(0, 0).getType());
    }

    @Test
    void shouldNotOverwriteMirrorBackslash() {
        board.setFieldType(1, 0, FieldType.MIRROR_BACKSLASH);

        board.setFieldType(1, 0, FieldType.MIRROR_SLASH);
        board.setFieldType(1, 0, FieldType.MIRROR_VERTICAL);
        board.setFieldType(1, 0, FieldType.MIRROR_HORIZONTAL);

        assertEquals(FieldType.MIRROR_BACKSLASH, board.getField(1, 0).getType());
    }

    @Test
    void shouldNotOverwriteMirrorVertical() {
        board.setFieldType(2, 0, FieldType.MIRROR_VERTICAL);

        board.setFieldType(2, 0, FieldType.MIRROR_SLASH);
        board.setFieldType(2, 0, FieldType.MIRROR_BACKSLASH);
        board.setFieldType(2, 0, FieldType.MIRROR_HORIZONTAL);

        assertEquals(FieldType.MIRROR_VERTICAL, board.getField(2, 0).getType());
    }

    @Test
    void shouldNotOverwriteMirrorHorizontal() {
        board.setFieldType(3, 0, FieldType.MIRROR_HORIZONTAL);

        board.setFieldType(3, 0, FieldType.MIRROR_SLASH);
        board.setFieldType(3, 0, FieldType.MIRROR_BACKSLASH);
        board.setFieldType(3, 0, FieldType.MIRROR_VERTICAL);

        assertEquals(FieldType.MIRROR_HORIZONTAL, board.getField(3, 0).getType());
    }
}