import java.util.ArrayList;
import java.util.List;

record ChessBoardPosition(Figure figure, Enum Color) {
};

class Chessboard {
//    List<ChessBoardPosition> boardPositions = new ArrayList<ChessBoardPosition>();
ChessBoardPosition[][] table = new ChessBoardPosition[8][8];

}