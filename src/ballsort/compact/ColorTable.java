package ballsort.compact;

import ballsort.Board;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public final class ColorTable {
    private final String[] idToColor;
    private final Map<String, Byte> colorToId;

    private ColorTable(String[] idToColor, Map<String, Byte> colorToId) {
        this.idToColor = idToColor;
        this.colorToId = colorToId;
    }

    public static ColorTable fromBoard(Board board) {
        TreeSet<String> distinct = new TreeSet<>();
        for (int t = 0; t < board.tubeCount(); t++) {
            for (String color : board.getTube(t).getInitialBalls()) {
                if (color != null && !color.isEmpty()) {
                    distinct.add(color);
                }
            }
        }
        if (distinct.size() > 127) {
            throw new IllegalStateException("Too many distinct colors for byte id: " + distinct.size());
        }

        String[] idToColor = new String[distinct.size() + 1];
        idToColor[0] = "";
        Map<String, Byte> colorToId = new HashMap<>();
        byte id = 1;
        for (String color : distinct) {
            idToColor[id] = color;
            colorToId.put(color, id);
            id++;
        }
        return new ColorTable(idToColor, colorToId);
    }

    public byte idFor(String color) {
        if (color == null || color.isEmpty()) {
            return 0;
        }
        Byte id = colorToId.get(color);
        if (id == null) {
            throw new IllegalArgumentException("Unknown color: " + color);
        }
        return id;
    }

    public String colorFor(byte id) {
        return idToColor[id];
    }

    public int colorCount() {
        return idToColor.length - 1;
    }
}
