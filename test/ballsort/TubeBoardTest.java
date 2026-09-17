/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ballsort;

import ballsort.games.GameLoader;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author augusto.conto
 */
public class TubeBoardTest {
    
    public TubeBoardTest() {
    }

    /**
     * Test of defaultGame method, of class Board.
     */
    @Test
    public void testDefaultGame() {
        Board result = GameLoader.loadGame("games/level1.tubes");
    }
    
}
