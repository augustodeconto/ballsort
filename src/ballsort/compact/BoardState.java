package ballsort.compact;

import ballsort.Board;
import ballsort.GameTransition;
import ballsort.GamePosition;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class BoardState implements GamePosition {
    private final byte[] cells;
    private final int tubeCount;
    private final int capacity;
    private final int hash;
    private final ColorTable table;

    private BoardState(byte[] cells, int tubeCount, int capacity, ColorTable table) {
        this.cells = cells;
        this.tubeCount = tubeCount;
        this.capacity = capacity;
        this.table = table;
        this.hash = Arrays.hashCode(cells);
    }

    public static BoardState initialFrom(Board board, ColorTable table) {
        int tubeCount = board.tubeCount();
        int capacity = board.getTube(0).getCapacity();
        byte[] cells = new byte[tubeCount * capacity];

        for (int t = 0; t < tubeCount; t++) {
            String[] balls = board.getTube(t).getInitialBalls();
            for (int pos = 0; pos < capacity; pos++) {
                cells[t * capacity + pos] = table.idFor(balls[pos]);
            }
        }
        return new BoardState(cells, tubeCount, capacity, table);
    }

    public byte get(int tube, int pos) {
        return cells[tube * capacity + pos];
    }

    public int tubeCount() {
        return tubeCount;
    }

    public int capacity() {
        return capacity;
    }

    public int topPosition(int tube) {
        for (int pos = capacity - 1; pos >= 0; pos--) {
            if (cells[tube * capacity + pos] != 0) {
                return pos;
            }
        }
        return -1;
    }

    public byte topColor(int tube) {
        int pos = topPosition(tube);
        return pos < 0 ? 0 : cells[tube * capacity + pos];
    }

    @Override
    public boolean isValid() {
        int[] counts = new int[table.colorCount() + 1];
        for (byte c : cells) {
            if (c != 0) {
                counts[c]++;
            }
        }
        for (int id = 1; id <= table.colorCount(); id++) {
            if (counts[id] != capacity) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isFinal() {
        for (int t = 0; t < tubeCount; t++) {
            int top = topPosition(t);
            if (top < 0) {
                continue;
            }
            if (top != capacity - 1) {
                return false;
            }
            byte color = cells[t * capacity];
            for (int pos = 1; pos < capacity; pos++) {
                if (cells[t * capacity + pos] != color) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public double entropy() {
        double disorder = 0.0;
        for (int t = 0; t < tubeCount; t++) {
            for (int pos = 1; pos < capacity; pos++) {
                byte cur = cells[t * capacity + pos];
                if (cur != cells[t * capacity + pos - 1]) {
                    disorder += 1.0;
                }
                if (cur == 0) {
                    break;
                }
            }
        }
        return disorder;
    }

    @Override
    public int sizeHint() {
        return tubeCount;
    }

    @Override
    public void printBoard() {
        System.out.println(toString(table));
    }

    /**
     * All states reachable by moving exactly one ball from the top of some
     * tube onto the top of another (destination empty or same top color,
     * with room to spare), paired with a human-readable move label.
     */
    @Override
    public List<GameTransition> sucessors() {
        int[] topPos = new int[tubeCount];
        byte[] topColor = new byte[tubeCount];
        for (int t = 0; t < tubeCount; t++) {
            topPos[t] = topPosition(t);
            topColor[t] = topPos[t] < 0 ? 0 : cells[t * capacity + topPos[t]];
        }

        List<GameTransition> result = new ArrayList<>();
        for (int i = 0; i < tubeCount; i++) {
            if (topPos[i] < 0) {
                continue;
            }
            byte color = topColor[i];
            for (int j = 0; j < tubeCount; j++) {
                if (i == j || topPos[j] >= capacity - 1) {
                    continue;
                }
                if (topPos[j] >= 0 && topColor[j] != color) {
                    continue;
                }

                byte[] newCells = cells.clone();
                newCells[i * capacity + topPos[i]] = 0;
                newCells[j * capacity + (topPos[j] + 1)] = color;
                BoardState next = new BoardState(newCells, tubeCount, capacity, table);

                String label = "<T" + (i + 1) + ", T" + (j + 1) + ", " + (topPos[j] + 2) + ", "
                        + table.colorFor(color) + ">";
                result.add(new GameTransition(next, label));
            }
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BoardState)) {
            return false;
        }
        BoardState other = (BoardState) o;
        return hash == other.hash && Arrays.equals(cells, other.cells);
    }

    @Override
    public int hashCode() {
        return hash;
    }

    public String toString(ColorTable table) {
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < tubeCount; t++) {
            sb.append("T").append(t + 1).append(": ");
            for (int pos = 0; pos < capacity; pos++) {
                String color = table.colorFor(get(t, pos));
                sb.append(color.isEmpty() ? "_" : color).append(' ');
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
