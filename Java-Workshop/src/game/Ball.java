package game;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import gfx.Assets;

import javax.swing.ImageIcon;
import java.awt.*;

public class Ball {

    private int x, y;
    private int velocity;
    private int width, height;
    private int radius;
    private Rectangle boundingBox;
    private int xdir;
    private int ydir;

    public Ball() {
        this.x = Constants.INIT_BALL_X;
        this.y = Constants.INIT_BALL_Y;
        this.xdir = 1;
        this.ydir = -1;
        this.velocity = 2;
        this.radius = Constants.BALL_RADIUS;
        this.width = this.radius * 2;
        this.height = this.radius * 2;
        this.boundingBox = new Rectangle(this.width, this.height);
    }

    public void move() {

        x += xdir;
        y += ydir;

        if (x == 0) {
            setXDir(1);
        }

        if (x == Constants.WIDTH - this.width) {
            setXDir(-1);
        }

        if (y == 0) {
            setYDir(1);
        }
    }
//
//    private void resetState() {
//
//        x = Constants.INIT_BALL_X;
//        y = Constants.INIT_BALL_Y;
//    }

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
