import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KnightTest {
    private final Figure figure = new Knight();

    @Test
    void IsWorking(){
        IO.print(figure.calculateAttack(4, 4));
        IO.print(figure.calculateAttack(4,4).toArray().length);
    }

}