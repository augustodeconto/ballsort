/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author augusto.conto
 */
public class ConvolutionalGraph extends Graph<ConvolutionalVertex> {

    ArrayList<Graph> graphs = new ArrayList<>();
    public void appendInnerGraph(Graph g) {
        graphs.add(g);
    }
    
    public int getInnerDimension() {
        return graphs.size();
    }
    
    @Override
    public Set<ConvolutionalVertex> getVertices() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addVertex(ConvolutionalVertex vertex) {
        // intentionally not supported
        throw new UnsupportedOperationException("Not supported. Modify inner graphs.");
    }

    @Override
    public boolean removeVertex(Object vertex) {
        // intentionally not supported
        throw new UnsupportedOperationException("Not supported. Modify inner graphs.");
    }

    @Override
    public boolean hasVertex(Object oVertex) {
        boolean hasVertex = true;
        if (oVertex instanceof ConvolutionalVertex) {
            ConvolutionalVertex vertex = (ConvolutionalVertex)oVertex;
            if (vertex.getDimension() != getInnerDimension())
                return false;
//            for (int i = 0; i < getInnerDimension(); i++)
//                if (!graphs[i].hasVertex(vertex.v[i])) {
//                    hasVertex = false;
//                    break;
//                }
        }
        
        // this is hard to implement
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int vertexCount() {
        int p;
        if (graphs.size() > 0) {
            p = 1;
            for (Graph g : graphs)
                p *= g.vertexCount();
        }
        else {
            p = 0;
        }
        return p;
    }

    @Override
    public ConvolutionalVertex getInitialVertex() {
        ConvolutionalVertex v = new ConvolutionalVertex(graphs.size());
        for (int i = 0; i < graphs.size(); i++)
            v.setInner(i, graphs.get(i).getInitialVertex());
        return v;
    }

    @Override
    public void setInitialVertex(ConvolutionalVertex vertex) {
        // intentionally not supported
        throw new UnsupportedOperationException("Not supported. Modify inner graphs.");
    }

    @Override
    public Set<ConvolutionalVertex> getFinalVertices() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setVertexFinal(ConvolutionalVertex vertex, boolean isFinal) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isFinalVertex(ConvolutionalVertex vertex) {
        boolean isFinal = true;
        for (int i = 0; i < vertex.getDimension(); i++) {
            if (!graphs.get(i).isFinalVertex(vertex.getInner(i))) {
                isFinal = false;
                break;
            }
        }
        return isFinal;
    }

    @Override
    public void addEdge(ConvolutionalVertex from, ConvolutionalVertex to, Object value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean hasEdge(ConvolutionalVertex from, ConvolutionalVertex to) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Set<ConvolutionalVertex> getAdjacentVertex(ConvolutionalVertex from) {
        Set<ConvolutionalVertex> cv = new HashSet<>();
        
        Set<AdjacentEdge> av = this.getAdjacentEdges(from);
        av.forEach((ae) -> {
            cv.add((ConvolutionalVertex)ae.getVertice());
        });
        return cv;
    }

    ArrayList<Set<Object>> innerEdgeValues = null;

    private class ConvEdge {
        private final ConvolutionalVertex from;
        private final ConvolutionalVertex to;
        private final Object value;

        public ConvEdge(ConvolutionalVertex from, ConvolutionalVertex to, Object value) {
            this.from = from;
            this.to = to;
            this.value = value;
        }
        
        public ConvEdge(int dim, Object value) {
            this.from  = new ConvolutionalVertex(dim);
            this.to    = new ConvolutionalVertex(dim);
            this.value = value;
        }

        public ConvolutionalVertex getFrom() {
            return from;
        }

        public ConvolutionalVertex getTo() {
            return to;
        }

        public Object getValue() {
            return value;
        }

        @Override
        public String toString() {
            return from.toString() + " --" + value.toString() + "--> " + to.toString();
        }
        
    }

    @Override
    public Set<AdjacentEdge> getAdjacentEdges(ConvolutionalVertex from) {
        /*
        T1, T2 - Set of all edge values
        E1, E2 - Set of edges at current vertice
        
        T1  = [a, b]     E1  = [a, b]
        T2  = [a, b, c]  E2  = [a, c]
        updating 1 edges from 2
        T1' = [a, b, c]  E1' = [a, c]
        - a belongs to both E1 and E2, so shall remain in set
        - b is known buy T2, thus, as it doesn't belong to E2, has to drop
        - c is not known by T1, only by T2, so we shall add to E1'
        
        T1' =  T1 U T2
        E1' = (E1 ^ E2) U (T1\T2)^E1 U (T2\T1)^E2 
        where U-Union, ^-Intersection, \-Subtraction
        
        * T1' (that is the next T1) is the union of all known edge values
          V1' is what we have in common between V1 and V2, 'plus' 
              the edges values that only belongs to T1 that are also in V1, plus
              the edges values that only belongs to T2 that are also in V2
        */
        
        if (innerEdgeValues == null)
            innerEdgeValues = searchAllInnerEdges();

        // Tn is a set of edge values
        // Vn is a set of edges
        Set<Object>   T1 = new HashSet<>(); // is a set of values
        Set<ConvEdge> E1 = new HashSet<>(); 
        
        for (int i = 0; i < graphs.size(); i++) {
            Graph g = graphs.get(i);
            Object            innerVerticeFrom = from.getInner(i);
            Set<Object>       T2 = innerEdgeValues.get(i);
            Set<AdjacentEdge> E2 = g.getAdjacentEdges(innerVerticeFrom);
            
            //E1' = (E1 ^ E2) U (T1\T2)^E1 U (T2\T1)^E2 
            Set<ConvEdge> E1n = new HashSet<>();

            // E1' = (E1 ^ E2)
            for (AdjacentEdge e2 : E2) {
                Object innerVerticeTo = e2.getVertice();
                // searches if E1 contains e2
                ConvEdge e1 = null;
                for (ConvEdge e : E1) {
                    if (e.getValue().equals(e2.getValue())) {
                        e1 = e;
                        break;
                    }
                }
                // if e1 value is the same as e2
                if (e1 != null) {
                    // update ConvEdge with inner graph edge
                    e1.getFrom().setInner(i, innerVerticeFrom);
                    e1.getTo().setInner(  i, innerVerticeTo);
                    E1n.add(e1);
                    E1.remove(e1);
                }
            }
            
            // E1' = E1' U (T1\T2)^E1
            Set<Object> T12 = new HashSet<>(T1); // Tn = T1
            T12.removeAll(T2);                   // Tn = (T1\T2)
            for (ConvEdge e1 : E1) {
                if (T12.contains(e1.getValue())) {
                    e1.getFrom().setInner(i, innerVerticeFrom);
                    e1.getTo().setInner(i, innerVerticeFrom);
                    E1n.add(e1);
                }
            }

            // E1' = E1' U (T2\T1)^E2
            Set<Object> T21 = new HashSet<>(T2); // Tn = T2
            T21.removeAll(T1);                   // Tn = (T2\T1)
            for (AdjacentEdge e2 : E2) {
                if (T21.contains(e2.getValue())) {
                    Object innerVerticeTo = e2.getVertice();
                    ConvEdge ce = new ConvEdge(this.getInnerDimension(), e2.getValue());
                    for (int j = 0; j < i; j++) {
                        ce.getFrom().setInner(j, from.getInner(j));
                        ce.getTo().setInner(j, from.getInner(j));
                    }
                    ce.getFrom().setInner(i, innerVerticeFrom);
                    ce.getTo().setInner(i, innerVerticeTo);
                    E1n.add(ce);
                }
            }
            
            E1 = E1n;
            //T1' =  T1 U T2
            T1.addAll(T2);
        }
        
        Set<AdjacentEdge> AE = new HashSet<>();
        for (ConvEdge ce : E1) {
            AE.add(new AdjacentEdge(from, ce.getTo(), ce.value));
        }
        
        return AE;
    }

    private ArrayList<Set<Object>> searchAllInnerEdges() {
        ArrayList<Set<Object>> values = new ArrayList<>();

        for (Graph g : graphs) {
            Set<Object> vertex = g.getVertices();
            Set<Object> graphValues = new HashSet<>();
            values.add(graphValues);
            for (Object fromVertice : vertex) {
                Set<AdjacentEdge> edges = g.getAdjacentEdges(fromVertice);
                for (AdjacentEdge edge : edges) {
                    graphValues.add(edge.getValue());
                }
            }
        }
        
        return values;
    }
    
}
