package game.entities;

import game.Constants;
import gfx.Assets;

import java.awt.*;

public class Ball extends GameObject {

    private int xdir;
    private int ydir;

    public Ball() {
        this.x = Constants.INIT_BALL_X;
        this.y = Constants.INIT_BALL_Y;
        this.xdir = 1;
        this.ydir = -1;
        this.boundingBox = new Rectangle(this.x, this.y, Constants.BALL_WIDTH, Constants.BALL_HEIGHT);
    }

    public void setXDir(int x) { xdir = x; }

    public void setYDir(int y) {
        ydir = y;
    }

    public int getYDir() {
        return ydir;
    }

    public void moveFaster() {

        x += xdir;
        y += ydir;

        if (x == 0) {
            setXDir(2);
        }

        if (x == Constants.WIDTH - Constants.BALL_WIDTH) {
            setXDir(-2);
        }

        if (y == 0) {
            setYDir(2);
        }

        this.boundingBox.setBounds(this.x, this.y, Constants.BALL_WIDTH, Constants.BALL_HEIGHT);
    }

    public void move() {

        x += xdir;
        y += ydir;

        if (x == 0) {
            setXDir(1);
        }

        if (x == Constants.WIDTH - Constants.BALL_WIDTH) {
            setXDir(-1);
        }

        if (y == 0) {
            setYDir(1);
        }

        this.boundingBox.setBounds(this.x, this.y, Constants.BALL_WIDTH, Constants.BALL_HEIGHT);
    }

    public void render(Graphics g) {
        g.drawImage(Assets.ball, this.x, this.y, null);
    }
}
