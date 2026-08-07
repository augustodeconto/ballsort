/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphs;

import java.util.Arrays;

/**
 *
 * @author augusto.conto
 */
public class ConvolutionalVertex {
    private Object[] v;
    
    
    public ConvolutionalVertex(int dim) {
        v = new Object[dim];
    }
    
    public int getDimension() {
        return v.length;
    }

    protected void setInner(int position, Object value) {
        v[position] = value;
    }
    
    public Object getInner(int position) {
        return v[position];
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        if (v[0] != null) { 
            sb.append(v[0].toString());
        } else {
            sb.append("_");
        }
        for (int i = 1; i < v.length; i++) {
            sb.append(", ");
            if (v[i] != null) { 
                sb.append(v[i].toString());
            } else {
                sb.append("_");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Arrays.deepHashCode(this.v);
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
        final ConvolutionalVertex other = (ConvolutionalVertex) obj;
        if (!Arrays.deepEquals(this.v, other.v)) {
            return false;
        }
        return true;
    }

    
}
