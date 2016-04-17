package game;

import javax.swing.ImageIcon;

public class Brick extends Sprite {

    private boolean destroyed;

    public Brick(int x, int y) {

        this.x = x;
        this.y = y;

        ImageIcon ii = new ImageIcon("images/blueBrick2.png");
        image = ii.getImage();

        imageWidth = image.getWidth(null);
        imageHeight = image.getHeight(null);

        destroyed = false;
    }

    public boolean isDestroyed() {

        return destroyed;
    }

    public void setDestroyed(boolean val) {

        destroyed = val;
    }
}