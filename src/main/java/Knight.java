import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

record JumpState(int x, int y, int dx, int dy) {
}

public class Knight implements Figure {

    @Override
    public List<Position> calculateAttack(Position position, Chessboard board) {
        List<Position> attacks = new ArrayList<>();
        int startX = position.col();
        int startY = position.row();

        int[][] startingVectors = {
                {1, 2}, {2, 1}, {2, -1}, {1, -2},
                {-1, -2}, {-2, -1}, {-2, 1}, {-1, 2}
        };

        for (int[] vector : startingVectors) {
            int currX = startX;
            int currY = startY;
            int dx = vector[0];
            int dy = vector[1];

            Set<JumpState> visitedStates = new HashSet<>();

            while (true) {
                JumpState state = new JumpState(currX, currY, dx, dy);
                if (!visitedStates.add(state)) {
                    break;
                }

                int nextX = currX + dx;
                int nextY = currY + dy;
                int N = board.getSize();

                while (nextX < 0 || nextX >= N) {
                    if (nextX < 0) {
                        nextX = -nextX;
                        dx = -dx;
                    } else if (nextX >= N) {
                        nextX = 2 * N - 2 - nextX;
                        dx = -dx;
                    }
                }
                while (nextY < 0 || nextY >= N) {
                    if (nextY < 0) {
                        nextY = -nextY;
                        dy = -dy;
                    } else if (nextY >= N) {
                        nextY = 2 * N - 2 - nextY;
                        dy = -dy;
                    }
                }

                int jumpcounter = 0;
                currX = nextX;
                currY = nextY;
                Field landedField = board.getField(currX, currY);

                if (landedField.getType() == FieldType.MIRROR_SLASH) {
                    if (jumpcounter>0) {
                        currX+=dx;
                        currY+=dy;
                    }
                    int temp = dx;
                    dx = -dy;
                    dy = -temp;
                    jumpcounter++;
                    continue;
                } else if (landedField.getType() == FieldType.MIRROR_BACKSLASH) {
                    int temp = dx;
                    dx = dy;
                    dy = temp;
                    jumpcounter++;
                    continue;
                } else if (landedField.getType() == FieldType.MIRROR_HORIZONTAL) {
                    dx = -dx;
                    jumpcounter++;
                    continue;
                } else if (landedField.getType() == FieldType.MIRROR_VERTICAL) {
                    dy = -dy;
                    jumpcounter++;
                    continue;
                }

                if (landedField.getType() == FieldType.OBSTACLE) {
                    break;
                }

                Position attackSquare = new Position(currX, currY);
                if (!attacks.contains(attackSquare)) {
                    attacks.add(attackSquare);
                }
                break;
            }
        }
        return attacks;
    }
}