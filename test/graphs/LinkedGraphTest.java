/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphs;

import java.util.Set;
import static junit.framework.Assert.*;
import org.junit.Ignore;
import org.junit.Test;
/**
 *
 * @author augusto.conto
 */
public class LinkedGraphTest {
    
    public LinkedGraphTest() {
    }

    @Test
    public void testBasicVertex() {
        LinkedGraph<Integer> g = new LinkedGraph<>();
        
        assertEquals(g.vertexCount(), 0);
        
        g.addVertex(1);
        assertEquals(g.vertexCount(), 1);
        assertTrue(g.hasVertex(1));
        assertFalse(g.hasVertex(2));
        
        g.addVertex(2);
        assertEquals(g.vertexCount(), 2);
        assertTrue(g.hasVertex(1));
        assertTrue(g.hasVertex(2));
        
        g.removeVertex(1);
        assertEquals(g.vertexCount(), 1);
        assertFalse(g.hasVertex(1));
        assertTrue(g.hasVertex(2));
    }
    
    @Test
    public void testInitialVertex() {
        LinkedGraph<Integer> g = new LinkedGraph<>();

        assertNull(g.getInitialVertex());

        boolean hasException = false;
        try {
            g.setInitialVertex(1);
        } catch (IllegalArgumentException e) {
            hasException = true;
        }
        assertTrue(hasException);
        
        g.addVertex(1);
        assertNull(g.getInitialVertex());
        
        g.setInitialVertex(1);
        assertEquals(g.getInitialVertex(), new Integer(1));

        g.addVertex(2);
        assertEquals(g.getInitialVertex(), new Integer(1));

        // clears initial verte
        g.setInitialVertex(2);
        assertEquals(g.getInitialVertex(), new Integer(2));
        g.setInitialVertex(null);
        assertNull(g.getInitialVertex());

        g.setInitialVertex(1);
        g.removeVertex(1);
        assertNull(g.getInitialVertex());
    }

    @Test
    public void testFinalVertices() {
        LinkedGraph<Integer> g = new LinkedGraph<>();

        g.addVertex(1);
        g.addVertex(2);
        g.addVertex(3);
        assertFalse(g.isFinalVertex(1));
        assertFalse(g.isFinalVertex(2));
        assertFalse(g.isFinalVertex(3));
        assertEquals(g.getFinalVertices().size(), 0);
        
        g.setVertexFinal(1, true);
        assertTrue(g.isFinalVertex(1));
        assertFalse(g.isFinalVertex(2));
        assertFalse(g.isFinalVertex(3));
        assertEquals(g.getFinalVertices().size(), 1);
        
        g.setVertexFinal(1, true);
        assertTrue(g.isFinalVertex(1));
        assertFalse(g.isFinalVertex(2));
        assertFalse(g.isFinalVertex(3));
        assertEquals(g.getFinalVertices().size(), 1);

        g.setVertexFinal(2, true);
        assertTrue(g.isFinalVertex(1));
        assertTrue(g.isFinalVertex(2));
        assertFalse(g.isFinalVertex(3));
        assertEquals(g.getFinalVertices().size(), 2);
        
        g.setVertexFinal(1, false);
        assertFalse(g.isFinalVertex(1));
        assertTrue(g.isFinalVertex(2));
        assertFalse(g.isFinalVertex(3));
        assertEquals(g.getFinalVertices().size(), 1);
        
        boolean hasException = false;
        g.removeVertex(2);
        try {
            g.isFinalVertex(2);
        } catch (IllegalArgumentException e) {
            hasException = true;
        }
        assertTrue(hasException);

        hasException = false;
        try {
            g.setVertexFinal(4, true);
        } catch (IllegalArgumentException e) {
            hasException = true;
        }
        assertTrue(hasException);
        
    }

    @Test
    public void testGetVertices() {
        LinkedGraph<Integer> g = new LinkedGraph<>();
        g.addVertex(1);
        g.addVertex(2);
        g.addVertex(3);
        
        assertEquals(g.getVertices().size(), 3);
        for (Integer v: g.getVertices())
            assertTrue(g.hasVertex(v));
    }
    
    @Test @Ignore
    public void testRemoveVertex() {
        LinkedGraph<Integer> g = new LinkedGraph<>();
        g.addVertex(1);
        g.addVertex(2);
        g.addVertex(3);
        g.addEdge(1, 2);
        g.addEdge(2, 3);
        
        assertTrue(g.hasEdge(1, 2));
        assertTrue(g.hasEdge(2, 3));
        
        g.removeVertex(2);
        
        assertFalse(g.hasEdge(1, 2));
        assertFalse(g.hasEdge(2, 3));
        
        
        // test removeVertex with edges.
    }
    
    @Test
    public void testAddEdge() {
        LinkedGraph<Integer> g = new LinkedGraph<>();
        Set<Integer> v;
        
        g.addVertex(1);
        g.addVertex(2);
        g.addVertex(3);
        g.addVertex(4);
        
        g.addEdge(1, 2);
        g.addEdge(2, 3);
        
        g.setDirected(true);
        assertTrue(g.hasEdge(1, 2));
        assertFalse(g.hasEdge(2, 1));
        assertTrue(g.hasEdge(2, 3));
        assertFalse(g.hasEdge(3, 2));
        assertFalse(g.hasEdge(1, 3));
        assertFalse(g.hasEdge(3, 1));
        
        v = g.getAdjacentVertex(1);
        assertFalse(v.contains(1));
        assertTrue(v.contains(2));
        assertFalse(v.contains(3));
        v = g.getAdjacentVertex(2);
        assertFalse(v.contains(1));
        assertFalse(v.contains(2));
        assertTrue(v.contains(3));
        v = g.getAdjacentVertex(3);
        assertFalse(v.contains(1));
        assertFalse(v.contains(2));
        assertFalse(v.contains(3));
        
        g.setDirected(false);
        assertTrue(g.hasEdge(1, 2));
        assertTrue(g.hasEdge(2, 1));
        assertTrue(g.hasEdge(2, 3));
        assertTrue(g.hasEdge(3, 2));
        assertFalse(g.hasEdge(1, 3));
        assertFalse(g.hasEdge(3, 1));
        
        v = g.getAdjacentVertex(1);
        assertFalse(v.contains(1));
        assertTrue(v.contains(2));
        assertFalse(v.contains(3));
        v = g.getAdjacentVertex(2);
        assertTrue(v.contains(1));
        assertFalse(v.contains(2));
        assertTrue(v.contains(3));
        v = g.getAdjacentVertex(3);
        assertFalse(v.contains(1));
        assertTrue(v.contains(2));
        assertFalse(v.contains(3));
        
    }
    
}
