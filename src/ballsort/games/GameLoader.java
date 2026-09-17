package ballsort.games;

import ballsort.Board;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public final class GameLoader {
    private GameLoader() {}

    public static Board loadGame(String filePath) {
        Board board = new Board();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                parseLine(board, filePath, lineNumber, line);
            }
        } catch (IOException e) {
            throw new GameLoadException("Could not read game file: " + filePath, e);
        }

        return board;
    }

    private static void parseLine(Board board, String filePath, int lineNumber, String rawLine) {
        String line = rawLine.trim();

        if (line.isEmpty() || line.startsWith("#")) {
            return;
        }
        if (line.startsWith("id:") || line.startsWith("name:")) {
            return;
        }
        if (line.equals("empty")) {
            board.addEmptyTube();
            return;
        }
        if (line.startsWith("tube:")) {
            String[] tokens = line.substring("tube:".length()).trim().split("\\s+");
            String[] balls = new String[tokens.length];
            for (int i = 0; i < tokens.length; i++) {
                balls[i] = tokens[i].equals("_") ? "" : tokens[i];
            }
            board.addTube(balls);
            return;
        }

        throw new GameLoadException(
                "Malformed line " + lineNumber + " in " + filePath + ": \"" + rawLine + "\"");
    }
}
