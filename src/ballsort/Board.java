/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ballsort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author augusto.conto
 */
public class Board {
    private final ArrayList<Tube> tubes = new ArrayList<>();

    public Tube addTube(Tube tube) {
        tubes.add(tube);
        return tube;
    }
    
    public Tube addTube(String[] balls) {

        Tube tube = new Tube(balls, String.format("T%d", (tubes.size()+1)));
        tubes.add(tube);
        return tube;
    }
    
    public Tube addEmptyTube() {
        int capacity = 0;
        
        for (Tube tube : tubes) {
            if (tube.getCapacity() > capacity) {
                capacity = tube.getCapacity();
            }
        }
        
        Tube tube = new Tube(capacity, String.format("T%d", (tubes.size()+1)));
        tubes.add(tube);
        return tube;
    }

    public Tube getTube(int i) {
        return tubes.get(i);
    }
    
    public int tubeCount() {
        return tubes.size();
    }

    public List<Tube> getTubes() {
        return Collections.unmodifiableList(tubes);
    }

    public boolean isValidGame() {
        boolean isValid = true;

        HashMap<String, Integer> ballCounter = new HashMap<>();
        for (int t = 0; t < tubeCount(); t++) {
            String[] b = tubes.get(t).getInitialBalls();
            for (int i = 0; i < b.length; i++) {
                if (b[i] != null && !b[i].equals("")) {
                    String color = b[i];
                    if (ballCounter.containsKey(color)) {
                        ballCounter.put(color, ballCounter.get(color) + 1);
                    }
                    else {
                        ballCounter.put(color, 1);
                    }
                }
            }
        }
        
        int expectedBallEachColor = tubes.get(0).getCapacity();
        
        StringBuilder sb = new StringBuilder();
        for (String color : ballCounter.keySet()) {
            if (ballCounter.get(color) != expectedBallEachColor) {
                isValid = false;
            }
            sb.append(color).append(" (").append(ballCounter.get(color)).append(")   ");
        }
        System.out.println(sb.toString());
        
        return isValid;
    }
}
