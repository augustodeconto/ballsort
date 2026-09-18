package ballsort;

public final class Move {
    public final SolverState state;
    public final String label;

    public Move(SolverState state, String label) {
        this.state = state;
        this.label = label;
    }
}
