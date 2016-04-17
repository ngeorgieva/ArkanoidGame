package game;

import gfx.Assets;

import java.awt.*;

public class Paddle {
    private int x, y;
    private int velocity;
    private int width, height;
    private int health;

    private Rectangle boundingBox;

    public static boolean goingLeft;
    public static boolean goingRight;

    public Paddle() {
        this.x = 110;
        this.y = 380;
        this.width = 30;
        this.height = 87;
        this.health = 50;
        this.velocity = 2;
        this.boundingBox = new Rectangle(this.width, this.height);

        this.goingLeft = false;
        this.goingRight = false;
    }

    public int getHealth() {
        return this.health;
    }

    //Checks if the paddle intersects with something
    public boolean Intersects(Rectangle r) {
        if(this.boundingBox.contains(r) || r.contains(this.boundingBox)) {
            return true;
        }
        return false;
    }

    //Update the movement of the paddle
    public void tick() {

        if (x <= 0) {
            x = 0;
        }
        if (x >= Constants.WIDTH - 87) {
            x = Constants.WIDTH - 87;
        }
        if(goingLeft) {
            this.x -= this.velocity;
        }
        if(goingRight) {
            this.x += this.velocity;
        }

        //Update the bounding box's position
        this.boundingBox.setBounds(this.x, this.y, this.width, this.height);
    }

    //Draws the paddle
    public void render(Graphics g) {

        g.drawImage(Assets.paddle, this.x, this.y, null);
    }
}
