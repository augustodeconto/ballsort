package ballsort;

import java.util.List;

public interface SolverState {
    boolean isValid();
    boolean isFinal();
    double entropy();
    int sizeHint();
    List<Move> neighbors();
    void printBoard();
}
