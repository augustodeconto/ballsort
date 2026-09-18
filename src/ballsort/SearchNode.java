/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ballsort;

import java.util.Objects;

/**
 *
 * @author augusto.conto
 */
public class SearchNode {
    public GamePosition vertex;
    public SearchNode       parent     = null;
    public String      label      = null;
    public double  entropy    = Double.POSITIVE_INFINITY;
    public boolean isFinal    = false;
    public int     visitCount = 0;
    static long    uidCounter;
    public final   long uid   = ++uidCounter;

    public SearchNode(GamePosition vertex) {
        this.vertex = vertex;
    }

    public SearchNode() {
    }

    public int getDepth() {
        if (parent == null)
            return 1;
        else
            return parent.getDepth()+1;
    }
    
    @Override
    public String toString() {
        return "--" + label + "-->" + vertex + ", entropy=" + entropy + ", isFinal=" + isFinal;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.vertex);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SearchNode other = (SearchNode) obj;
        if (!Objects.equals(this.vertex, other.vertex)) {
            return false;
        }
        return true;
    }

}

