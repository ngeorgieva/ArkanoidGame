package game;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import gfx.Assets;

import java.awt.*;

public class Paddle {
    private int x, y;
    private int velocity;
    private int width, height;

    private Rectangle boundingBox;

    public static boolean goingLeft;
    public static boolean goingRight;

    public Paddle() {
        this.x = Constants.INIT_PADDLE_X;
        this.y = Constants.INIT_PADDLE_Y;
        this.width = Constants.PADDLE_WIDTH;
        this.height = Constants.PADDLE_HEIGHT;
        this.velocity = 3;
        this.boundingBox = new Rectangle(this.x, this.y, this.width, this.height);

        this.goingLeft = false;
        this.goingRight = false;
    }


    public boolean Intersects(Rectangle r) {
        if(this.boundingBox.contains(r) || r.contains(this.boundingBox)) {
            return true;
        }
        return false;
    }

    public void tick() {

        this.boundingBox.setBounds(this.x, this.y, this.width, this.height);

        if(goingLeft) {
            this.x -= this.velocity;
            if (x <= 0) {
                x = 0;
            }
            if (x >= Constants.WIDTH - this.width) {
                x = Constants.WIDTH - this.width;
            }
        }
        if(goingRight) {
            this.x += this.velocity;
            if (x <= 0) {
                x = 0;
            }
            if (x >= Constants.WIDTH - this.width) {
                x = Constants.WIDTH - this.width;
            }
        }
    }

    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    public void render(Graphics g) {

        g.drawImage(Assets.paddle, this.x, this.y, null);
    }
}
