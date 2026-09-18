/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ballsort.convolutional;

import ballsort.Tube;
import java.util.Objects;

/**
 *
 * @author augusto.conto
 */
public class TubeTransition {
    private Tube   from;
    private Tube   to;
    private int    toPosition;
    private String color;

    public TubeTransition(Tube from, Tube to, int toPosition, String color) {
        this.from = from;
        this.to = to;
        this.toPosition = toPosition;
        this.color = color;
    }

    public Tube getFrom() {
        return from;
    }

    public void setFrom(Tube from) {
        this.from = from;
    }

    public Tube getTo() {
        return to;
    }

    public void setTo(Tube to) {
        this.to = to;
    }

    public int getToPosition() {
        return toPosition;
    }

    public void setToPosition(int toPosition) {
        this.toPosition = toPosition;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "<" + from + ", " + to + ", " + toPosition + ", " + color + ">";
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.from);
        hash = 23 * hash + Objects.hashCode(this.to);
        hash = 23 * hash + this.toPosition;
        hash = 23 * hash + Objects.hashCode(this.color);
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
        final TubeTransition other = (TubeTransition) obj;
        if (this.toPosition != other.toPosition) {
            return false;
        }
        if (!Objects.equals(this.color, other.color)) {
            return false;
        }
        if (!Objects.equals(this.from, other.from)) {
            return false;
        }
        if (!Objects.equals(this.to, other.to)) {
            return false;
        }
        return true;
    }
    
    
}
