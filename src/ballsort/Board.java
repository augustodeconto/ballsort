/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ballsort;

import graphs.ConvolutionalGraph;
import graphs.ConvolutionalVertex;
import graphs.Graph;
import java.util.ArrayList;
import java.util.HashMap;

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
    
    public void printBoard(ConvolutionalVertex vertex) {
        String[][] balls = new String[tubeCount()][];
        int maxSize = 0;
        for (int t = 0; t < tubes.size(); t++) {
            String[] b = tubes.get(t).getBalls((TubeNode)vertex.getInner(t));
            balls[t] = b;
            if (b.length > maxSize) maxSize = b.length;
        }
        
        StringBuilder sb = new StringBuilder();
        for (int i = maxSize-1; i >= 0; i--) {
            sb.append("   ");
            for (int t = 0; t < tubes.size(); t++) {
                if (balls[t][i].equals(""))
                    sb.append("  ");
                else
                    sb.append(balls[t][i]);
                sb.append("   ");
            }
            if (i > 0) sb.append("\n");
        }
        System.out.println(sb.toString());
    }
    
    public static Board defaultGame(int id) {
        Board board = null;
        
        switch (id) {
            case (1): {
                board = new Board();
                board.addTube(new String[]{ "A", "L", "A"});
                board.addTube(new String[]{ "L", "A", "L"});
                board.addEmptyTube();
                break;
            }
            case (3): {
                // level 3
                board = new Board();
                board.addTube(new String[]{ "Az", "La", "Vm", "Az"});
                board.addTube(new String[]{ "La", "La", "Vm", "Az"});
                board.addTube(new String[]{ "Vm", "Az", "La", "Vm"});
                board.addEmptyTube();
                board.addEmptyTube();
                break;
            }
            case (21): {
                // level 21
                board = new Board();
                board.addTube(new String[]{ "La", "Vm", "Vm", "Az"});
                board.addTube(new String[]{ "Rc", "Vd", "Vm", "La"});
                board.addTube(new String[]{ "Az", "Az", "Vd", "Vm"});
                board.addTube(new String[]{ "Vd", "La", "Rc", "Az"});
                board.addTube(new String[]{ "La", "Rc", "Vd", "Rc"});
                board.addEmptyTube();
                board.addEmptyTube();
                break;
            }
            case (51): {
                // level 51
                board = new Board();
                board.addTube(new String[]{ "Az", "Rs", "La", "Vc"});
                board.addTube(new String[]{ "Rs", "Vm", "Vc", "Cn"});
                board.addTube(new String[]{ "Vm", "Cn", "Rs", "Cn"});
                board.addTube(new String[]{ "La", "Az", "Cz", "Vc"});
                board.addTube(new String[]{ "La", "Cz", "La", "Vm"});
                board.addTube(new String[]{ "Cz", "Cz", "Az", "Vm"});
                board.addTube(new String[]{ "Cn", "Rs", "Vc", "Az"});
                board.addEmptyTube();
                board.addEmptyTube();
                break;
            }
            case (106): {
                // level 106
                board = new Board();
                board.addTube(new String[]{ "Rs", "Vc", "Vm", "Rx"});
                board.addTube(new String[]{ "Az", "Az", "Ve", "Rs"});
                board.addTube(new String[]{ "Cz", "Cn", "Cn", "Az"});
                board.addTube(new String[]{ "Ve", "Vc", "Ve", "Rs"});
                board.addTube(new String[]{ "La", "La", "Rx", "Cz"});
                board.addTube(new String[]{ "Ve", "La", "Vm", "Rs"});
                board.addTube(new String[]{ "Cz", "Cz", "Vc", "Vm"});
                board.addTube(new String[]{ "Vc", "Vm", "Rx", "La"});
                board.addTube(new String[]{ "Cn", "Cn", "Az", "Rx"});
                board.addEmptyTube();
                board.addEmptyTube();
                break;
            }
           case (108): {
                // level 108
                board = new Board();
                board.addTube(new String[]{ "Vc", "Vm", "Vv", "Cn"});
                board.addTube(new String[]{ "Rs", "Vv", "Cn", "Cn"});
                board.addTube(new String[]{ "Rx", "Vv", "Vm", "Vm"});
                board.addTube(new String[]{ "Cz", "La", "Rs", "Az"});
                board.addTube(new String[]{ "Rx", "Cz", "Vm", "Rs"});
                board.addTube(new String[]{ "Az", "Rx", "Cz", "Vc"});
                board.addTube(new String[]{ "Az", "Vv", "Rx", "La"});
                board.addTube(new String[]{ "Vc", "La", "Cz", "La"});
                board.addTube(new String[]{ "Cn", "Az", "Vc", "Rs"});
                board.addEmptyTube();
                board.addEmptyTube();
                break;
            }
           case (111): {
                // level 111
                board = new Board();
                //board.addTube(new String[]{ "", "", "", ""});
                board.addTube(new String[]{ "Am", "Vd", "La", "Az"});
                board.addTube(new String[]{ "Vc", "Vv", "Rs", "Vd"});
                board.addTube(new String[]{ "Am", "Rx", "Cn", "Vm"});
                board.addTube(new String[]{ "Vd", "Cn", "La", "Rx"});
                board.addTube(new String[]{ "Vc", "Cz", "Rs", "Az"});
                board.addTube(new String[]{ "Vd", "Am", "Ma", "Vc"});
                board.addTube(new String[]{ "Ma", "Cn", "Rs", "Vm"});
                board.addTube(new String[]{ "La", "Az", "Vc", "Cz"});
                board.addTube(new String[]{ "Am", "Cz", "Vv", "Vv"});
                board.addTube(new String[]{ "Cn", "Vv", "Rs", "Ma"});
                board.addTube(new String[]{ "Ma", "Rx", "Vm", "Cz"});
                board.addTube(new String[]{ "Vm", "Az", "Rx", "La"});
                board.addEmptyTube();
                board.addEmptyTube();
                break;
            }
           case (117): {
                // level 117
                board = new Board();
                //board.addTube(new String[]{ "", "", "", ""});
                board.addTube(new String[]{ "Vc", "Vd", "Rx", "Cn"});
                board.addTube(new String[]{ "Rs", "Cz", "Vc", "Az"});
                board.addTube(new String[]{ "Cn", "Az", "Cn", "Az"});
                board.addTube(new String[]{ "Vd", "Rx", "La", "Vm"});
                board.addTube(new String[]{ "Cn", "Vm", "La", "Vc"});
                board.addTube(new String[]{ "Vd", "La", "Am", "Vm"});
                board.addTube(new String[]{ "Rx", "Vm", "Vv", "Vv"});
                board.addTube(new String[]{ "Vc", "Vv", "Cz", "Vv"});
                board.addTube(new String[]{ "Am", "Ma", "Az", "Am"});
                board.addTube(new String[]{ "Rs", "La", "Rx", "Ma"});
                board.addTube(new String[]{ "Cz", "Am", "Ma", "Cz"});
                board.addTube(new String[]{ "Rs", "Vd", "Ma", "Rs"});
                board.addEmptyTube();
                board.addEmptyTube();
                break;
            }
           case (129): {
                // level 129
                board = new Board();
                //board.addTube(new String[]{ "", "", "", ""});
                board.addTube(new String[]{ "Rs", "Az", "Vm", "Rx"});
                board.addTube(new String[]{ "Cz", "Az", "Cn", "La"});
                board.addTube(new String[]{ "Vc", "Rc", "La", "Vc"});
                board.addTube(new String[]{ "La", "Cn", "Ve", "Cz"});
                board.addTube(new String[]{ "Az", "Ma", "Cz", "Rs"});
                board.addTube(new String[]{ "Ma", "Rc", "Rc", "Rs"});
                board.addTube(new String[]{ "Ma", "Vc", "Ve", "La"});
                board.addTube(new String[]{ "Vm", "Rx", "Rc", "Am"});
                board.addTube(new String[]{ "Ma", "Az", "Ve", "Rx"});
                board.addTube(new String[]{ "Am", "Vm", "Cn", "Cz"});
                board.addTube(new String[]{ "Am", "Ve", "Vm", "Rs"});
                board.addTube(new String[]{ "Rx", "Cn", "Am", "Vc"});
                board.addEmptyTube();
                board.addEmptyTube();
                break;
            }
           case (135): {
                // level 135
                board = new Board();
                //board.addTube(new String[]{ "", "", "", ""});
                board.addTube(new String[]{ "Vd", "Cn", "Rx", "Vc"});
                board.addTube(new String[]{ "Mr", "Rc", "Cz", "Am"});
                board.addTube(new String[]{ "La", "Mr", "Mr", "Rc"});
                board.addTube(new String[]{ "Rx", "Vm", "Cn", "Vm"});
                board.addTube(new String[]{ "Cz", "Mr", "Am", "Rc"});
                board.addTube(new String[]{ "La", "La", "Cz", "Rs"});
                board.addTube(new String[]{ "Vc", "Vd", "Az", "Vd"});
                
                board.addTube(new String[]{ "Rx", "Rs", "Cz", "Vc"});
                board.addTube(new String[]{ "Rs", "Vc", "Vm", "La"});
                board.addTube(new String[]{ "Cn", "Rc", "Cn", "Am"});
                board.addTube(new String[]{ "Rx", "Az", "Vd", "Vm"});
                board.addTube(new String[]{ "Am", "Rs", "Az", "Az"});
                board.addEmptyTube();
                board.addEmptyTube();
                break;
            }
           case (159): {
                // level 159
                board = new Board();
                //board.addTube(new String[]{ "", "", "", ""});
                board.addTube(new String[]{ "11", "03", "10", "09"});
                board.addTube(new String[]{ "10", "11", "08", "01"});
                board.addTube(new String[]{ "06", "12", "07", "03"});
                board.addTube(new String[]{ "06", "07", "07", "10"});
                board.addTube(new String[]{ "07", "09", "01", "02"});
                board.addTube(new String[]{ "05", "02", "04", "11"});
                board.addTube(new String[]{ "12", "09", "02", "12"});
                board.addTube(new String[]{ "03", "09", "08", "10"});
                board.addTube(new String[]{ "04", "05", "01", "06"});
                board.addTube(new String[]{ "05", "08", "08", "03"});
                board.addTube(new String[]{ "01", "11", "04", "06"});
                board.addTube(new String[]{ "05", "12", "02", "04"});
                board.addEmptyTube();
                board.addEmptyTube();
                break;
            }
           case (160): {
                // level 159 partialy solved
                board = new Board();
                //board.addTube(new String[]{ "", "", "", ""});
                board.addTube(new String[]{ "11", "03", "03", "03"});
                board.addTube(new String[]{ "10", "11", "08", "01"});
                board.addTube(new String[]{ "06", "12", "07", "03"});
                board.addTube(new String[]{ "06", "06", "06", ""});
                board.addTube(new String[]{ "07", "07", "07", ""});
                board.addTube(new String[]{ "05", "02", "04", "11"});
                board.addTube(new String[]{ "10", "10", "10", ""});
                board.addEmptyTube();
                board.addTube(new String[]{ "04", "05", "01", "01"});
                board.addTube(new String[]{ "05", "08", "08", "08"});
                board.addTube(new String[]{ "01", "11", "04", "04"});
                board.addTube(new String[]{ "05", "12", "12", "12"});
                board.addTube(new String[]{ "02", "02", "02", ""});
                board.addTube(new String[]{ "09", "09", "09", "09"});
                break;
            }
           case (337): {
                // level 159
                board = new Board();
                //board.addTube(new String[]{ "", "", "", ""});
                board.addTube(new String[]{ "04", "01", "02", "11"});
                board.addTube(new String[]{ "12", "11", "09", "09"});
                board.addTube(new String[]{ "02", "07", "11", "01"});
                board.addTube(new String[]{ "12", "12", "06", "11"});
                board.addTube(new String[]{ "02", "02", "03", "08"});
                board.addTube(new String[]{ "06", "06", "08", "08"});
                board.addTube(new String[]{ "01", "03", "05", "07"});
                board.addTube(new String[]{ "09", "01", "05", "10"});
                board.addTube(new String[]{ "05", "07", "03", "10"});
                board.addTube(new String[]{ "05", "03", "04", "04"});
                board.addTube(new String[]{ "12", "07", "10", "09"});
                board.addTube(new String[]{ "06", "08", "04", "10"});
                board.addEmptyTube();
                board.addEmptyTube();
                break;
            }
        }
        
        return board;
    }
    
 
    public ConvolutionalGraph getModel() {
        ConvolutionalGraph ballSortModel = new ConvolutionalGraph();
        
        for (Tube t : tubes) {
            Graph<TubeNode> tubeModel = t.createModel(tubes);
            ballSortModel.appendInnerGraph(tubeModel);
        }
        return ballSortModel;
    }

    boolean isValidGame() {
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
