package game;

import game.entities.Ball;

import javax.swing.*;
import java.awt.*;

public class Menu extends JPanel {
    public static final int BUTTON_WIDTH = 150;
    public static final int BUTTON_HEIGHT = 30;
    public static final int buttonX = Constants.WIDTH / 2 - BUTTON_WIDTH / 2;
    public static Rectangle resumeButton = new Rectangle(buttonX, 150, BUTTON_WIDTH, BUTTON_HEIGHT);
    public static Rectangle newGameButton = new Rectangle(buttonX, 150 + BUTTON_HEIGHT + 10, BUTTON_WIDTH, BUTTON_HEIGHT);
    public static Rectangle quitButton = new Rectangle(buttonX, 150 + 2 * BUTTON_HEIGHT + 20, BUTTON_WIDTH, BUTTON_HEIGHT);

    public static void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        Font buttonFont = new Font("Arial", Font.BOLD,20);
        g.setFont(buttonFont);

        if (Ball.hasGameStarted) {
            g.setColor(Color.BLACK);
            g2d.fill(resumeButton);
            g.setColor(Color.WHITE);
            g.drawString("RESUME", resumeButton.x + 32, resumeButton.y + 22);
        }

        g.setColor(Color.BLACK);
        g2d.fill(newGameButton);
        g.setColor(Color.WHITE);
        g.drawString("NEW GAME", newGameButton.x + 19, newGameButton.y + 22);

        g.setColor(Color.BLACK);
        g2d.fill(quitButton);
        g.setColor(Color.WHITE);
        g.drawString("QUIT", quitButton.x + 50, quitButton.y + 22);
    }

}
