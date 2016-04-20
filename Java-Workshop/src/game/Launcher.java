package game;

import java.util.ArrayList;

public class Launcher {
    public static Game game;

    public static void main(String[] args) {
        Levels.initLevels();
        game = new Game(" ARKANOID!", Constants.WIDTH, Constants.HEIGHT);
    }
}