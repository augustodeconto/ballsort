package ballsort;

public final class GameTransition {
    public final GamePosition nextPosition;
    public final String label;

    public GameTransition(GamePosition nextPosition, String label) {
        this.nextPosition = nextPosition;
        this.label = label;
    }
}
