/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ballsort;

/**
 *
 * @author augusto.conto
 */
public class BallSort {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Board board = Board.defaultGame(337);
               
        Solver solver = new Solver();
        solver.setStopOnFinal(true);
        
        solver.solve(board);
        System.out.println("finalCount:" + solver.getFinalCount() +
                " maxDepth:" + solver.getMaxDepth() +
                " visitCount:" + solver.getVisitCount());

    }
    
}
