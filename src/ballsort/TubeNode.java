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
public class TubeNode {
    private final  Tube tube;
    private int    pos;
    private String color;
    private int    ground;

    public TubeNode(Tube parent) {
        this.tube = parent;
    }

    public TubeNode(int ground, Tube parent, int pos, String color) {
        this.tube   = parent;
        this.pos    = pos;
        this.color  = color;
        this.ground = ground;
    }
    
    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Tube getTube() {
        return tube;
    }

    public int getGround() {
        return ground;
    }

    public void setGround(int ground) {
        this.ground = ground;
    }

    @Override
    public String toString() {
        return "<" + ground + ", " + pos + ", " + color + ">";
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.tube);
        hash = 23 * hash + this.pos;
        hash = 23 * hash + Objects.hashCode(this.color);
        hash = 23 * hash + this.ground;
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
        final TubeNode other = (TubeNode) obj;
        if (this.pos != other.pos) {
            return false;
        }
        if (this.ground != other.ground) {
            return false;
        }
        if (!Objects.equals(this.color, other.color)) {
            return false;
        }
        if (!Objects.equals(this.tube, other.tube)) {
            return false;
        }
        return true;
    }


    
}
