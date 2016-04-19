package game;

public class Launcher {
    public static Game game;
    public static void main(String[] args) {
        game = new Game("Arkanoid!", Constants.WIDTH, Constants.HEIGHT);
        game.start();
    }
}