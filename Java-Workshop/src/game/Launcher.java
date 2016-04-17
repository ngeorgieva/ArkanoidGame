package game;

import display.KeyboardExample;

import javax.swing.*;

public class Launcher {
    public static void main(String[] args) {
        Game game = new Game("Arkanoid!", 300, 400);
        //KeyboardExample keyboardExample = new KeyboardExample();
        //game.add(keyboardExample);
        game.start();
    }
}
//    public static void main(String[] args) {
//        JFrame frame = new JFrame("Mini Tennis");
//        KeyboardExample keyboardExample = new KeyboardExample();
//        frame.add(keyboardExample);
//        frame.setVisible(true);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    }