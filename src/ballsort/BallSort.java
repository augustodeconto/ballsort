/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ballsort;

import ballsort.compact.BoardState;
import ballsort.compact.ColorTable;
import ballsort.convolutional.ConvolutionalSolverState;
import ballsort.games.GameLoader;

/**
 *
 * @author augusto.conto
 */
public class BallSort {

    /**
     * @param args the command line arguments: [gameFile] [representation: "conv"|"convolutional"|"comp"|"compact"]
     */
    public static void main(String[] args) {
        String gameFile = args.length > 0 ? args[0] : "games/level337.tubes";
        String repr = args.length > 1 ? args[1] : "conv";
        Board board = GameLoader.loadGame(gameFile);

        GamePosition initial = "comp".equals(repr) || "compact".equals(repr)
                ? BoardState.initialFrom(board, ColorTable.fromBoard(board))
                : ConvolutionalSolverState.initialFrom(board);

        Solver solver = new Solver();
        solver.setStopOnFinal(true);

        solver.solve(initial);
        System.out.println("finalCount:" + solver.getFinalCount() +
                " maxDepth:" + solver.getMaxDepth() +
                " visitCount:" + solver.getVisitCount());

    }
    
}
