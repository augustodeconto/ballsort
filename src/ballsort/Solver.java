/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ballsort;

import graphs.LinkedGraph;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author augusto.conto
 */
public class Solver {
    // config
    private boolean  stopOnFinal   = true;
    private int      maxIterations = 100000;
    private Strategy strategy      = Strategy.Entropy;
    private int      printStepHead = 10;
    private int      printStepBoard = 1000;

    public void setStopOnFinal(boolean stopOnFinal) {
        this.stopOnFinal = stopOnFinal;
    }

    public void setMaxIterations(int maxIterations) {
        this.maxIterations = maxIterations;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public void setPrintStepHead(int printStepHead) {
        this.printStepHead = printStepHead;
    }

    public void setPrintStepBoard(int printStepBoard) {
        this.printStepBoard = printStepBoard;
    }

    // counters
    private int visitCount = 0;
    private int maxDepth   = 0;
    private int finalCount = 0;

    // algorithm data structures
    private int                sizeHint;
    private LinkedList<State>  backlog;
    private LinkedList<State>  goldBacklog;
    private LinkedGraph<State> visited;

    public void solve(SolverState initialState) {
        backlog     = new LinkedList<>();
        goldBacklog = new LinkedList<>();
        visited     = new LinkedGraph<>();

        if (!initialState.isValid()) {
            System.out.println("Invalid game");
            return;
        }
        sizeHint = initialState.sizeHint();

        State root = new State(initialState);
        root.isFinal = initialState.isFinal();
        root.entropy = initialState.entropy();
        backlogAdd(root);

        int     iteration     = 0;
        boolean haltExecution = false;
        long    previousTime = System.currentTimeMillis();
        while (iteration++ < maxIterations &&
               hasBacklog() &&
               !haltExecution) {

            // select state
            State state = selectStateToVisit();

            previousTime = printSolveStatus(iteration, previousTime, state);

            state.visitCount++;
            if (visited.hasVertex(state)) {
                if ((iteration % printStepBoard) == 0)
                    System.out.println("Visited " + state.visitCount);
            }
            else {
                visited.addVertex(state);
                if (state.parent != null) {
                    visited.addEdge(state.parent, state, state.label);
                }

                if (state.isFinal) {
                    finalCount++;
                    if (stopOnFinal) {
                        haltExecution = true;
                    }
                    printPath(state);
                    System.out.println("FinalCount:" + finalCount);
                }
                else { // not final
                    StringBuilder sb = new StringBuilder();
                    List<Move> moves = state.vertex.neighbors();
                    for (Move move : moves) {
                        State analyzed = new State(move.state);
                        analyzed.parent = state;
                        analyzed.label = move.label;
                        analyzed.isFinal = move.state.isFinal();
                        analyzed.entropy = move.state.entropy();
                        visitCount++;
                        backlogAdd(analyzed);
//                        System.out.println(" " + analyzed);
                        sb.append("#").append(analyzed.uid).append("(").append((int)analyzed.entropy).append(") ");
                    }
                    if ((iteration % printStepBoard) == 0)
                        System.out.println("Append " + moves.size() + ":" + sb.toString());
                }
            }
        }

        if (iteration >= maxIterations)
            System.out.println("Maximum iterations reached " + iteration);
    }

    private long printSolveStatus(int iteration, long previousTime, State state) {
        //<editor-fold defaultstate="collapsed" desc="print">
        long    currentTime = System.currentTimeMillis();
        if (iteration < 100 || (iteration % printStepHead) == 0) {
            //#18 Score:34.0  Iter:5(66/62) 22ms
            double firstScore = 0, lastScore = 0;
            if (backlog.size() > 0) {
                firstScore = backlog.getFirst().entropy;
                lastScore = backlog.getLast().entropy;
            }

            System.out.println(
                    "" + iteration +
                    " Id:" + state.uid +
                    " Deph:" + state.getDepth() +
                    " Entropy:" + state.entropy +
                            "     Visited:" + visitCount +
                            " Backlog:" + backlog.size() +
                            "  [" + firstScore +
                            ", " + lastScore +
                            "]" +
                            "   " + (currentTime - previousTime) + "ms"
           );
            previousTime = currentTime;
        }
        if (iteration % printStepBoard == 0) {
            state.vertex.printBoard();
        }
        //System.out.println(state.vertex);
        //</editor-fold>
        return previousTime;
    }

    public enum Strategy {
        DFS,
        BFS,
        Entropy
    };

    private boolean hasBacklog() {
        return (!backlog.isEmpty() || !goldBacklog.isEmpty());
    }

    private int priorityMaxDepth = -1;

    private void backlogAdd(State state) {
        boolean isPriority = false;

        if (priorityMaxDepth <= 0) {
            priorityMaxDepth = sizeHint / 3;
            if (priorityMaxDepth < 1) priorityMaxDepth = 1;
            if (priorityMaxDepth > 5) priorityMaxDepth = 5;
        }
        isPriority = state.isFinal || state.getDepth() <= priorityMaxDepth;
        // another ruled to make state prioritary ...

        if (isPriority) {
            goldBacklog.addFirst(state);
        }
        else {
            if ((strategy.equals(Strategy.DFS)) ||
                (!backlog.isEmpty() &&
                  state.entropy <= backlog.peekFirst().entropy)) {
                backlog.addFirst(state);
            }
            else if ((strategy.equals(Strategy.BFS)) ||
                     (!backlog.isEmpty() &&
                       state.entropy >= backlog.peekLast().entropy)) {
                backlog.addLast(state);
            }
            else {
                int i;
                for (i = 0; i < backlog.size(); i++) {
                    if (!backlog.get(i).isFinal &&
                            state.entropy < backlog.get(i).entropy) {
                        break;
                    }
                }
                backlog.add(i, state);
            }
        }
    }

    private State selectStateToVisit() {
        if (!goldBacklog.isEmpty()) {
            return goldBacklog.poll();
        }

        // backlog is sorted in entropy level
        return backlog.pollFirst();
    }

    private void printPath(State finalState ){
        Stack<State> stack = new Stack<>();
        int moves;

        State state;
        state = finalState;
        do {
            stack.push(state);
            state = state.parent;
        } while (state != null);
        moves = stack.size() - 1;

        while (!stack.isEmpty()) {
            state = stack.pop();
            if (state.label == null) {
                System.out.println("#" + state.uid);
            }
            else {
                System.out.println("--" + state.label + "--> #" + state.uid);
            }
        }
        System.out.println("moves:" + moves);
    }

    public int getVisitCount() {
        return visitCount;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public int getFinalCount() {
        return finalCount;
    }

    public void onVertexEnter(Object vertex, boolean isFinal, boolean visited) {
        System.out.println(" visiting:" + vertex +
                   " final:" + isFinal +
                   " visited:" + visited);
    }



}
