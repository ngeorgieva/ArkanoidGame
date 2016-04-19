package game;

public class Launcher {
    public static void main(String[] args) {
        Game game = new Game("Arkanoid!", Constants.WIDTH, Constants.HEIGHT);
        game.start();
    }
}