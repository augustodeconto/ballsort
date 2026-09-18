package ballsort;

import java.util.List;

public interface GamePosition {
    boolean isValid();

    boolean isFinal();

    double entropy();

    int sizeHint();

    List<GameTransition> sucessors();

    void printBoard();
}
