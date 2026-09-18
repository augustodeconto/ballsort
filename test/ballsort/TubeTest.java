/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ballsort;

import ballsort.convolutional.TubeNode;
import graphs.ConvolutionalGraph;
import graphs.ConvolutionalVertex;
import graphs.Graph;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author augusto.conto
 */
public class TubeTest {
    
    public TubeTest() {
    }


    @Test
    public void testGetCapacity() {
        Tube tubes[] = new Tube[3];
        tubes[0] = new Tube(new String[]{ "A", "L", "A"}, "T1");
        tubes[1] = new Tube(new String[]{ "L", "A", "L"}, "T2");
        tubes[2] = new Tube(3, "T3");
     
        ArrayList<Tube> allTubes = new ArrayList<>();
        for (Tube t:tubes) allTubes.add(t);

        
        ArrayList<Graph<TubeNode>> tubeGraphs = new ArrayList<>(tubes.length);
        ConvolutionalGraph ballSortModel = new ConvolutionalGraph();
        
        for (Tube t : tubes) {
            Graph<TubeNode> tubeGraph = t.createModel(allTubes);
            tubeGraphs.add(tubeGraph);
            ballSortModel.appendInnerGraph(tubeGraph);
            System.out.println("Tube: " + t.getName());
            tubeGraph.printGraph();
        }
        
        //tubeGraphs.get(0).printGraph();
        
        ConvolutionalVertex v = ballSortModel.getInitialVertex();
        for (int i = 0; i < tubeGraphs.size(); i++)
            tubeGraphs.get(i).printVertice((TubeNode)v.getInner(i));
        //ballSortModel.getAdjacentEdges(v);
        //ballSortModel.printVertice(v);
        
        
        ballSortModel.printGraph();
    }
    
}
