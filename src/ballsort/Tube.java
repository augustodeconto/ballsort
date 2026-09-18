/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ballsort;

import ballsort.convolutional.TubeNode;
import ballsort.convolutional.TubeTransition;
import graphs.Graph;
import graphs.LinkedGraph;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author augusto.conto
 */
public class Tube {
    private final String[] initialBalls;
    private final String name;
    
    public Tube(int capacity, String name) {
        initialBalls = new String[capacity];
        this.name = name;
    }
    
    public Tube(String[] balls, String name) {
        this.initialBalls = balls;
        this.name = name;
    }
    
    public int getCapacity() {
        return initialBalls.length;
    }

    public String[] getInitialBalls() {
        return initialBalls;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
    
    public Graph<TubeNode> createModel(List<Tube> allTubes) {
        Graph<TubeNode> g = new LinkedGraph<>();
        
        TubeNode emptyNode = new TubeNode(0, this, 0, "");
        g.addVertex(emptyNode);
        g.setVertexFinal(emptyNode, true);
        
        for (String color : getAllColors(allTubes)) {
            TubeNode under = emptyNode;
            for (int pos = 1; pos <= this.getCapacity(); pos++) {
                TubeNode node = new TubeNode(0, this, pos, color);
                addConnectedNode(g, under, node, allTubes);
                
                if (pos == this.getCapacity())
                    g.setVertexFinal(node, true);
                under = node;
            }
        }
        
        TubeNode ground = emptyNode;
        int groundLevel = 0;
        String prevColor = null;
        for (int k = 1; k <= this.getCapacity(); k++) {
            String color = initialBalls[k-1];
            if (color == null) {
                break;
            }
            if (prevColor != null && !color.equals(prevColor)) {
                groundLevel = k;
            }
            
            TubeNode node = new TubeNode(groundLevel, this, k, color);
            // if there is a node n adjacent to initialNode
            if (!g.hasVertex(node) ||
                !g.getAdjacentVertex(node).contains(ground)) {
                addConnectedNode(g, ground, node, allTubes);
                TubeNode under = node;
                for (int j = k+1; j <= this.getCapacity(); j++) {
                    TubeNode node2 = new TubeNode(groundLevel, this, j, color);
                    addConnectedNode(g, under, node2, allTubes);
                    under = node2;
                }
            }
            ground     = node;
            prevColor = color;
        }
        g.setInitialVertex(ground);
        return g;
    }
    
    private List<TubeTransition> getBallInTransictions(TubeNode fromNode, TubeNode toNode, List<Tube> allTubes) {
        List<TubeTransition> tList = new ArrayList<>();
        
        for (Tube tube : allTubes) {
            if (tube == this)
                continue;
            tList.add(new TubeTransition(tube, this, toNode.getPos(), toNode.getColor()));
        }
        return tList;
    }

    private List<TubeTransition> getBallOutTransictions(TubeNode fromNode, TubeNode toNode, List<Tube> allTubes) {
        List<TubeTransition> tList = new ArrayList<>();
        
        for (Tube tube : allTubes) {
            if (tube == this)
                continue;
            int initial = 1;
//            if (fromNode.getGround() == 0)
//                initial = 2;// fromNode.getPos();
            for (int k = initial; k <= tube.getCapacity(); k++) {
                TubeTransition t = new TubeTransition(this, tube, k, fromNode.getColor());
                tList.add(t);
            }
        }
        return tList;
    }

    private Set<String> getAllColors(List<Tube> allTubes) {
        Set<String> allColors = new HashSet<>();
        for (Tube tube : allTubes) 
            for (String color : tube.getInitialBalls())
                if (color != null)
                    allColors.add(color);
        return allColors;
    }

    private void addConnectedNode(Graph<TubeNode> g, TubeNode under, TubeNode node, List<Tube> allTubes) {
        g.addVertex(node);

        List<TubeTransition> outTransitions = getBallOutTransictions(node, under, allTubes);
        outTransitions.forEach((t) -> {
            g.addEdge(node, under, t);
        });

        if (node.getGround() == under.getGround()) {
            List<TubeTransition> inTransitions = getBallInTransictions(under, node, allTubes);
            inTransitions.forEach((t) -> {
                g.addEdge(under, node, t);
            });
        }
    }
    
    public String[] getBalls(TubeNode state) {
        String[] balls;
        balls = Arrays.copyOf(initialBalls, initialBalls.length);
        
        int i = state.getGround();
        if (i <= 0) i = 1;
        for (; i <= state.getPos(); i++) {
            balls[i-1] = state.getColor();
        }
        for (; i <= getCapacity(); i++) {
            balls[i-1] = "";
        }
        return balls;
    }
}
