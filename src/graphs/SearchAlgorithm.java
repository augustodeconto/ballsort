/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 *
 * @author augusto.conto
 */
public class SearchAlgorithm {
    
    private Stack<Object> path = new Stack<>();
    private Graph graph = null;
    private HashSet<Object> visited;
    private boolean stopOnFinal = false;
    private int visitCount = 0;
    private int maxDepth = 0;
    private int finalCount = 0;

    public List<Object> getPath() {
        return path;
    }
    
    public int getDepth() {
        return path.size();
    }
    
    public void DFS(Graph graph) {
        this.graph = graph;
        
        Object startVertex = graph.getInitialVertex();
        
        this.path    = new Stack<>();
        this.visited = new HashSet<>();
        this.visitCount = 0;
        this.maxDepth   = 0;
        this.finalCount = 0;
        DFSVisit(startVertex);
        
    }

    private void DFSVisit(Object vertex) {
        // vertex input
        
        Set<AdjacentEdge> adjEdges = graph.getAdjacentEdges(vertex);
        boolean isVisited;
        boolean isFinal = graph.isFinalVertex(vertex);
        
        visitCount++;
        if (getDepth() > maxDepth) maxDepth = getDepth();
        if (isFinal) finalCount++;
                
        path.push(vertex);
        isVisited = !visited.add(vertex); // add returns false if already contains
        
        onVertexEnter(vertex, isFinal, isVisited);
        
        if (isVisited) {
        } 
        else if (isFinal) {
            
        }
//        else if (isFinal && stopOnFinal) {
//            // do something
//        }
        else {
            for (AdjacentEdge adjEdge : adjEdges) {
                Object nextVertice = adjEdge.getVertice();

                DFSVisit(nextVertice);
            }
        }
        path.pop();
    }

    public void onVertexEnter(Object vertex, boolean isFinal, boolean visited) {
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
    
    
    
}
