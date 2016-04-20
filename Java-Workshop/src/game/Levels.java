package game;

import java.util.ArrayList;
import java.util.Arrays;

public class Levels {
    public static ArrayList<String[]> brickPattern;
    public static int numberOfLevels = 0;

    public static String[] level1 = {
            "      ",
            "      ",
            "      ",
            "    X ",
            "      ",
    };
    public static String[] level2 = {
            "XXXXXX",
            "X XX X",
            "X XX X",
            "XXXXXX",
            "X XX X",
            "X XX X",
            "XXXXXX"
    };
    public static String[] level3 = {
            "X XXXX",
            "XX X X",
            "XXX XX",
            "XX XXX",
            "X XX X",
    };
    public static String[] level4 = {
            "XXXXXX",
            "XXXXXX",
            "XXXXXX",
            "XXXXXX",
            "XXXXXX",
    };

    public static void initLevels() {
        brickPattern = new ArrayList<String[]>(Arrays.asList(level1, level2));
        numberOfLevels = brickPattern.size();
    }



    public static int getNumberOfBricks(int currentLevel) {
        int numberOfBricks = 0;
        String[] level = Levels.brickPattern.get(currentLevel);
        for (String row : level) {
            for (int i = 0; i < row.length(); i++) {
                if (row.charAt(i) == 'X') {
                    numberOfBricks++;
                }
            }
        }
        return numberOfBricks;
    }
}
