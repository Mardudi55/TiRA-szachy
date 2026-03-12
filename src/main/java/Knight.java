import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Knight implements Figure {

    @Override
    public List<Position> calculateAttack(int x, int y) {
        List<Position> attacks = new ArrayList<>();


        int[][] possibleMoves = {
                {x + 1, y + 2}, {x + 2, y + 1}, {x + 2, y - 1}, {x + 1, y - 2},
                {x - 1, y - 2}, {x - 2, y - 1}, {x - 2, y + 1}, {x - 1, y + 2}
        };

        for (int[] move : possibleMoves) {
            int newX = move[0];
            int newY = move[1];

            if (newX >= 0 && newX < 8 && newY >= 0 && newY < 8) {
                attacks.add(new Position(newX, newY));
            }
        }
        return attacks;
    }


}
