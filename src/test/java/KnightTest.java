import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

class KnightTest {
    private Figure figure;
    private Chessboard board;

    @BeforeEach
    void setUp() {
        figure = new Knight();
        board = new Chessboard(8);
    }

    @Test
    void shouldCalculateEightAttacksFromCenter() {
        List<Position> attacks = figure.calculateAttack(new Position(4, 4), board);

        assertThat(attacks).hasSize(8);
    }

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

    @Test
    void shouldStopAtObstaclesAndNotContainThem() {
        board.setFieldType(5, 1, FieldType.OBSTACLE);
        board.setFieldType(5, 3, FieldType.OBSTACLE);

        List<Position> attacks = figure.calculateAttack(new Position(6, 1), board);

        assertThat(attacks)
                .hasSize(6)
                .doesNotContain(new Position(5, 1), new Position(5, 3));
    }

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

    @Test
    void shouldNotJumpOnOtherFigures() {
        board.setFieldType(2, 4, FieldType.MIRROR_SLASH);
        board.setFieldType(4, 2, FieldType.MIRROR_VERTICAL);

        board.getField(1, 6).setFigure(new Knight());
        board.getField(3, 7).setFigure(new Knight());

        List<Position> attacksFrom16 = figure.calculateAttack(new Position(1, 6), board);
        List<Position> attacksFrom37 = figure.calculateAttack(new Position(3, 7), board);

        assertThat(attacksFrom16).doesNotContain(new Position(3, 7));
        assertThat(attacksFrom37).doesNotContain(new Position(1, 6));
    }

    @Test
    void shouldPreventInfiniteLoopsWithMirrors() {
        board.setFieldType(3, 3, FieldType.MIRROR_SLASH);
        board.setFieldType(5, 5, FieldType.MIRROR_SLASH);
        board.setFieldType(3, 5, FieldType.MIRROR_BACKSLASH);
        board.setFieldType(5, 3, FieldType.MIRROR_BACKSLASH);

        assertTimeoutPreemptively(java.time.Duration.ofSeconds(1), () -> {
            List<Position> attacks = figure.calculateAttack(new Position(4, 4), board);
            assertThat(attacks).isNotNull();
        }, "Wykryto nieskończoną pętlę w lustrach!");
    }
}