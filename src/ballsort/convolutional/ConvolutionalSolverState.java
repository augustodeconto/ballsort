package ballsort.convolutional;

import ballsort.Board;
import ballsort.GameTransition;
import ballsort.GamePosition;
import ballsort.Tube;
import graphs.AdjacentEdge;
import graphs.ConvolutionalGraph;
import graphs.ConvolutionalVertex;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class ConvolutionalSolverState implements GamePosition {
    private final ConvolutionalVertex vertex;
    private final Board board;
    private final ConvolutionalGraph model;

    public ConvolutionalSolverState(ConvolutionalVertex vertex, Board board, ConvolutionalGraph model) {
        this.vertex = vertex;
        this.board = board;
        this.model = model;
    }

    public static ConvolutionalSolverState initialFrom(Board board) {
        ConvolutionalGraph model = buildModel(board);
        return new ConvolutionalSolverState((ConvolutionalVertex) model.getInitialVertex(), board, model);
    }

    private static ConvolutionalGraph buildModel(Board board) {
        ConvolutionalGraph model = new ConvolutionalGraph();
        List<Tube> tubes = board.getTubes();
        for (Tube t : tubes) {
            model.appendInnerGraph(t.createModel(tubes));
        }
        return model;
    }

    @Override
    public boolean isValid() {
        return board.isValidGame();
    }

    @Override
    public boolean isFinal() {
        return model.isFinalVertex(vertex);
    }

    @Override
    public int sizeHint() {
        return board.tubeCount();
    }

    @Override
    public void printBoard() {
        String[][] balls = new String[board.tubeCount()][];
        int maxSize = 0;
        for (int t = 0; t < board.tubeCount(); t++) {
            String[] b = board.getTube(t).getBalls((TubeNode) vertex.getInner(t));
            balls[t] = b;
            if (b.length > maxSize) {
                maxSize = b.length;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = maxSize - 1; i >= 0; i--) {
            sb.append("   ");
            for (int t = 0; t < board.tubeCount(); t++) {
                if (balls[t][i].equals("")) {
                    sb.append("  ");
                } else {
                    sb.append(balls[t][i]);
                }
                sb.append("   ");
            }
            if (i > 0) {
                sb.append("\n");
            }
        }
        System.out.println(sb.toString());
    }

    @Override
    public double entropy() {
        double disorder = 0.0;
        for (int b = 0; b < board.tubeCount(); b++) {
            String[] colors = board.getTube(b).getBalls((TubeNode) vertex.getInner(b));
            for (int i = 1; i < colors.length; i++) {
                if (!colors[i].equals(colors[i - 1])) {
                    disorder += 1.0;
                }
                if ("".equals(colors[i])) {
                    break;
                }
            }
        }
        return disorder;
    }

    @Override
    public List<GameTransition> sucessors() {
        Set<AdjacentEdge> adjacent = model.getAdjacentEdges(vertex);
        List<GameTransition> moves = new ArrayList<>(adjacent.size());
        for (AdjacentEdge edge : adjacent) {
            ConvolutionalVertex next = (ConvolutionalVertex) edge.getVertice();
            moves.add(new GameTransition(new ConvolutionalSolverState(next, board, model), edge.getValue().toString()));
        }
        return moves;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConvolutionalSolverState)) {
            return false;
        }
        return Objects.equals(vertex, ((ConvolutionalSolverState) o).vertex);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(vertex);
    }
}
