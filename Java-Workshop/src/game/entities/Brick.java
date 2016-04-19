package game.entities;

import game.Constants;
import gfx.Assets;
import java.awt.*;

public class Brick extends GameObject{

    private boolean destroyed;

    public Brick(int x, int y) {

        this.x = x;
        this.y = y;
        this.boundingBox = new Rectangle(this.x, this.y, Constants.BRICK_WIDTH, Constants.BRICK_HEIGHT);
        destroyed = false;
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