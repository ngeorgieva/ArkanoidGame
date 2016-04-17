package game;

import gfx.Assets;

import javax.swing.ImageIcon;
import java.awt.*;

public class Ball extends Sprite {

    private int xdir;
    private int ydir;

    public Ball() {

        xdir = 1;
        ydir = -1;

        ImageIcon ii = new ImageIcon("Images/ball.gif");
        image = ii.getImage();

        imageWidth = image.getWidth(null);
        imageHeight = image.getHeight(null);

        resetState();
    }

    public void move() {

        x += xdir;
        y += ydir;

        if (x == 0) {
            setXDir(1);
        }

        if (x == Constants.WIDTH - imageWidth) {
            setXDir(-1);
        }

        if (y == 0) {
            setYDir(1);
        }
    }

    private void resetState() {

        x = Constants.INIT_BALL_X;
        y = Constants.INIT_BALL_Y;
    }

    public void setXDir(int x) {
        xdir = x;
    }

    public void setYDir(int y) {
        ydir = y;
    }

    public int getYDir() {
        return ydir;
    }

    public void render(Graphics g) {
        g.drawImage(Assets.ball, this.x, this.y, null);
    }
}
