package game;

import display.Display;
import game.entities.Ball;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class InputHandler implements KeyListener, MouseListener {

    public InputHandler(Display display) {
        display.getCanvas().addKeyListener(this);
        display.getCanvas().addMouseListener(this);
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int keyCode = e.getKeyCode();

        if (Game.state == Game.STATE.GAME) {
            if (keyCode == KeyEvent.VK_SPACE) {
                Ball.hasGameStarted = true;
            }
            if (keyCode == KeyEvent.VK_LEFT) {
                Game.paddle.goingLeft = true;
            }
            if (keyCode == KeyEvent.VK_RIGHT) {
                Game.paddle.goingRight = true;
            }
            if (keyCode == KeyEvent.VK_ESCAPE) {
                keyCode = -1;
                Game.state = Game.STATE.MENU;
            }
        }

        if(Game.state == Game.STATE.MENU && keyCode == KeyEvent.VK_ESCAPE) {
            keyCode = -1;
            Game.state = Game.STATE.GAME;
        }

        if (Game.state == Game.STATE.LEVEL_WON) {
            Launcher.game.init();
            Game.state = Game.STATE.GAME;
            Ball.hasGameStarted = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }


    @Override
    public void keyReleased(KeyEvent e) {

        int keyCode = e.getKeyCode();

        if (Game.state == Game.STATE.GAME) {
            if (keyCode == KeyEvent.VK_LEFT) {
                Game.paddle.goingLeft = false;
            }
            if (keyCode == KeyEvent.VK_RIGHT) {
                Game.paddle.goingRight = false;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (Game.state == Game.STATE.MENU) {
            int mouseX = e.getX();
            int mouseY = e.getY();

            if (mouseX >= Menu.resumeButton.x && mouseX <= Menu.resumeButton.x + Menu.BUTTON_WIDTH) {
                //Resume button
                if (mouseY >= Menu.resumeButton.y && mouseY <= Menu.resumeButton.y + Menu.BUTTON_HEIGHT && Ball.hasGameStarted) {
                    Game.state = Game.STATE.GAME;
                }
                //New Game button
                if (mouseY >= Menu.newGameButton.y && mouseY <= Menu.newGameButton.y + Menu.BUTTON_HEIGHT) {
                    LevelBuilder.currentLevel = 0;
                    Launcher.game.init();
                    Game.state = Game.STATE.GAME;
                    Ball.hasGameStarted = false;
                }
                //Quit button
                if (mouseY >= Menu.quitButton.y && mouseY <= Menu.quitButton.y + Menu.BUTTON_HEIGHT) {
                    System.exit(1);
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
