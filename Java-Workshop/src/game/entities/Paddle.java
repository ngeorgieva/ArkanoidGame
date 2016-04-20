package game.entities;
import game.Constants;
import gfx.Assets;

import java.awt.*;

public class Paddle extends GameObject {

    private int velocity;
    public static boolean goingLeft;
    public static boolean goingRight;
    public static int paddleX;

    public Paddle() {
        this.x = Constants.INIT_PADDLE_X;
        this.y = Constants.INIT_PADDLE_Y;
        this.velocity = 4;
        this.boundingBox = new Rectangle(this.x, this.y, Constants.PADDLE_WIDTH, Constants.PADDLE_HEIGHT);
        this.goingLeft = false;
        this.goingRight = false;
    }

    public void tick() {

        this.boundingBox.setBounds(this.x, this.y, Constants.PADDLE_WIDTH, Constants.PADDLE_HEIGHT);

        if(goingLeft) {
            this.x -= this.velocity;
            if (x <= 0) {
                x = 0;
            }
            //if (x >= Constants.WIDTH - Constants.PADDLE_WIDTH) {
            //    x = Constants.WIDTH - Constants.PADDLE_WIDTH;
            //}
        }
        if(goingRight) {
            this.x += this.velocity;
            //if (x <= 0) {
            //    x = 0;
            //}
            if (x >= Constants.WIDTH - Constants.PADDLE_WIDTH) {
                x = Constants.WIDTH - Constants.PADDLE_WIDTH;
            }
        }

        paddleX = this.x;
    }

    public void render(Graphics g) {

        g.drawImage(Assets.paddle, this.x, this.y, null);
    }
}
