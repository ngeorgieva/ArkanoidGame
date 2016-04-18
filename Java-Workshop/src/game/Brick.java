package game;

import gfx.Assets;
import java.awt.*;

public class Brick {

    private int x, y;
    private int width, height;
    private boolean destroyed;
    private Rectangle boundingBox;

    public Brick(int x, int y) {

        this.x = x;
        this.y = y;
        this.width = Constants.BRICK_WIDTH;
        this.height = Constants.BRICK_HEIGHT;


        this.boundingBox = new Rectangle(this.x, this.y, this.width, this.height);
        destroyed = false;
    }

    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    public boolean isDestroyed() {

        return destroyed;
    }

    public void setDestroyed(boolean val) {

        this.destroyed = val;
    }

    public void render(Graphics g) {

        g.drawImage(Assets.brick, this.x, this.y, null);
    }
}