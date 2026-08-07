/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphs;

/**
 *
 * @author augusto.conto
 */
public class AdjacentEdge<V> {
    private final V      current;
    private final V      vertice;
    private final Object value;
    protected AdjacentEdge(V current, V edge, Object value) {
        this.current = current;
        this.vertice = edge;
        this.value   = value;
    }

    public V getCurrent() {
        return current;
    }

    public V getVertice() {
        return vertice;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value + " -> " + vertice;
    }
    
}
