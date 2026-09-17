/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ballsort;

import ballsort.games.GameLoader;

/**
 *
 * @author augusto.conto
 */
public class BallSort {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String gameFile = args.length > 0 ? args[0] : "games/level337.tubes";
        Board board = GameLoader.loadGame(gameFile);

        Solver solver = new Solver();
        solver.setStopOnFinal(true);
        
        solver.solve(board);
        System.out.println("finalCount:" + solver.getFinalCount() +
                " maxDepth:" + solver.getMaxDepth() +
                " visitCount:" + solver.getVisitCount());

    }
    
}
