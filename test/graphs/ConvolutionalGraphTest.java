/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphs;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import org.junit.Test;

/**
 *
 * @author augusto.conto
 */
public class ConvolutionalGraphTest {
    
    public ConvolutionalGraphTest() {
    }

    @Test
    public void firstTests() {
        Graph<Integer> g1 = new LinkedGraph<>();
        g1.addVertex(11);
        g1.addVertex(12);
        g1.addVertex(13);
        g1.setInitialVertex(11);
        g1.addEdge(11, 13, "a");
        g1.addEdge(13, 13, "a");
        g1.addEdge(11, 12, "b");
        g1.addEdge(11, 12, "d");
        
        Graph<Integer> g2 = new LinkedGraph<>();
        g2.addVertex(21);
        g2.addVertex(22);
        g2.addVertex(23);
        g2.setInitialVertex(21);
        g2.addEdge(21, 22, "a");
        g2.addEdge(21, 22, "c");
        g2.addEdge(22, 22, "b");
        
        ConvolutionalGraph g = new ConvolutionalGraph();
        g.appendInnerGraph(g1);
        g.appendInnerGraph(g2);
        
        Queue<ConvolutionalVertex> vQueue = new LinkedList<>();
        vQueue.add(g.getInitialVertex());
        
        while (!vQueue.isEmpty()) {
            ConvolutionalVertex v;
            v = vQueue.poll();
            System.out.println(v.toString());
            
            Set<AdjacentEdge> aes = g.getAdjacentEdges(v);
            for (AdjacentEdge as : aes) {
                System.out.println("   " + as);
                vQueue.add((ConvolutionalVertex)as.getVertice());
            }
        }
        
    }
}
