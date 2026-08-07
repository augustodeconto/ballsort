/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 *
 * @author augusto.conto
 */
public class LinkedGraph<V> extends Graph<V> {
    ConcurrentHashMap<V, LinkedVertex> vertices = new ConcurrentHashMap<>();
    V initialVertex = null;

    private class LinkedVertex {
        public V vertex;
        public boolean isFinal = false;
        // double linked
        public ArrayList<LinkedEdge> fromEdges = new ArrayList<>();
        public ArrayList<LinkedEdge> toEdges   = new ArrayList<>();
       
        LinkedVertex(V vertex) {this.vertex = vertex;};
    }
    private class LinkedEdge {
        public V from;
        public V to;
        public Object value;
        LinkedEdge(V from, V to, Object value) {
            this.from  = from;
            this.to    = to;
            this.value = value;
        };
    }

    @Override
    public Set<V> getVertices() {
        return new HashSet<>(Collections.list(vertices.keys()));
    }

    @Override
    public void addVertex(V vertex) {
        if (!hasVertex(vertex)) {
            LinkedVertex lVertex = new LinkedVertex(vertex);
            vertices.put(vertex, lVertex);
        }
    }

    @Override
    public boolean removeVertex(Object vertex) {
        if (hasVertex(vertex)) {
            if (vertex.equals(getInitialVertex()))
                setInitialVertex(null);
            vertices.remove((V)vertex);
            return true;
        }
        return false;
    }

    @Override
    public boolean hasVertex(Object vertex) {
        return vertices.containsKey(vertex);
    }

    private LinkedVertex getVertex(V vertex) {
        if (!hasVertex(vertex))
            throw new IllegalArgumentException(vertex.toString() + "is not vertex in graph."); 
        else
            return vertices.get(vertex);
    } 
            
    @Override
    public int vertexCount() {
        return vertices.size();
    }

    @Override
    public V getInitialVertex() {
        return initialVertex;
    }

    @Override
    public void setInitialVertex(V vertex) {
        if (vertex != null && !hasVertex(vertex))
            throw new IllegalArgumentException(vertex.toString() + "is not vertex in graph.");
        
        initialVertex = vertex;
    }

    @Override
    public Set<V> getFinalVertices() {
        HashSet<V> verticesSet = new HashSet<>();
        vertices.values().stream().filter((lv) -> (lv.isFinal)).forEachOrdered((lv) -> {
            verticesSet.add(lv.vertex);
        });
        return verticesSet;
    }

    @Override
    public void setVertexFinal(V vertex, boolean isFinal) {
        getVertex(vertex).isFinal = isFinal;
    }

    @Override
    public boolean isFinalVertex(V vertex) {
        return getVertex(vertex).isFinal;
    }

    @Override
    public void addEdge(V from, V to, Object value) {
        LinkedEdge edge = new LinkedEdge(from, to, value);
        LinkedVertex fromLV = getVertex(from);
        LinkedVertex toLV   = getVertex(to);
        
        fromLV.fromEdges.add(edge);
        toLV.toEdges.add(edge);
    }

    @Override
    public boolean hasEdge(V from, V to) {
        boolean hasEdge;
        
        LinkedVertex linkedVertex = getVertex(from);
        // searches in fromEdges list (downstream)
        hasEdge = linkedVertex.fromEdges.stream().anyMatch(le -> { return le.to.equals(to); });
        
        if (!hasEdge && !isDirected()) {
            // if it is not directed, searches upstream
            hasEdge = linkedVertex.toEdges.stream().anyMatch(le -> { return le.from.equals(to); });
        }
        return hasEdge;
    }

    @Override
    public Set<V> getAdjacentVertex(V from) {
        Set<V> vertex;
        
        LinkedVertex linkedVertex = getVertex(from);
        vertex = linkedVertex.fromEdges.stream().map(lv -> { return lv.to; }).collect(Collectors.toSet());
        
        if (!isDirected())
            vertex.addAll(linkedVertex.toEdges.stream().map(lv -> {return lv.from;}).collect(Collectors.toSet()));
        
        return vertex;
    }
    
    @Override
    public Set<AdjacentEdge> getAdjacentEdges(V from) {
        Set<AdjacentEdge> edges = new HashSet<>();
        
        
        LinkedVertex linkedVertex = getVertex(from);
        linkedVertex.fromEdges.forEach((le) -> {
            edges.add(new AdjacentEdge(from, le.to, le.value));
        });
        
        
        if (!isDirected())
            linkedVertex.toEdges.forEach((le) -> {
                edges.add(new AdjacentEdge(from, le.from, le.value));
        });
        
        return edges;
    }
    
            
}
