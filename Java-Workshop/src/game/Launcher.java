package game;


import javax.swing.*;

public class Launcher {
    public static void main(String[] args) {
        Game game = new Game("Arkanoid!", 300, 400);
        game.start();
    }
}