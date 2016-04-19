package game.entities;

import java.awt.*;

public abstract class GameObject {

    protected int x, y;
    protected Rectangle boundingBox;

    protected GameObject() {
    }

    public Rectangle getBoundingBox() {
        return this.boundingBox;
    }

    public abstract void render(Graphics g);
}
