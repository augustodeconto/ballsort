/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphs;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 *
 * @author augusto.conto
 * @param <V>
 */
public abstract class Graph<V> {
    
    // Nodes
    public abstract Set<V> getVertices();
    public abstract void addVertex(V vertex);
    
    /**
     * Removes vertex and all edges linked to vertex
     * @param vertex vertex to be removed from graph
     * @return true if graph cointains vertex, false otherwise.
     */
    public abstract boolean removeVertex(Object vertex);
    public abstract boolean hasVertex(Object vertex);
    public abstract int vertexCount();
    
    // Initial and final
    public abstract V getInitialVertex();
    public abstract void setInitialVertex(V vertex);
    public abstract Set<V> getFinalVertices();
    public abstract void setVertexFinal(V vertex, boolean isFinal);
    public abstract boolean isFinalVertex(V vertex);
    
   
    
    // Edges
    //<editor-fold defaultstate="collapsed" desc="Edges Directed">
    private boolean isDirected = true;

    public boolean isDirected() {
        return isDirected;
    }

    public void setDirected(boolean isDirected) {
        this.isDirected = isDirected;
    }
    
    //</editor-fold>
    
    public abstract void addEdge(V from, V to, Object value);
    public          void addEdge(V from, V to) { addEdge(from, to, null); }
    public abstract boolean hasEdge(V from, V to);
    public abstract Set<V> getAdjacentVertex(V from);
    public abstract Set<AdjacentEdge> getAdjacentEdges(V from);
    
    public void printGraph() {
        Set<V> visited = new HashSet<>();
        
        Queue<V> queue = new LinkedList<>();
        
        if (getInitialVertex()!= null)
            queue.add(getInitialVertex());
        
        while (!queue.isEmpty()) {
            V v;
            v = queue.poll();
            
            if (visited.contains(v)) {
                continue;
            } 
            else {
                visited.add(v);
            }
            
            queue.addAll(getAdjacentVertex(v));
            System.out.println("queue: " + queue.size() + "  visited: " + visited.size());
            printVertice(v);
            
            if (isFinalVertex(v)) {
                System.out.println("final");
                break;
            }
        }
    }
    
    public void printVertice(V vertice) {
        System.out.println(vertice.toString());
        for (AdjacentEdge ae : this.getAdjacentEdges(vertice)) {
            if (this.isFinalVertex((V)ae.getVertice())) {
                System.out.println("  --" + ae.getValue() + "--> <" + ae.getVertice() + ">");
                
            }
            else {
                System.out.println("  --" + ae.getValue() + "--> " + ae.getVertice());
            }
      }
    }
}
