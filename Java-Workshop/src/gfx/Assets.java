package gfx;

import game.Constants;

import java.awt.image.BufferedImage;

public class Assets {

    public static BufferedImage paddle;
    public static BufferedImage brick;
    public static BufferedImage ball;

    //Loads every resource needed for the game
    public static void init() {
        SpriteSheet paddlePic = new SpriteSheet(ImageLoader.loadImage("/textures/paddle.png"));
        brick = ImageLoader.loadImage("/textures/brick.png");
        ball = ImageLoader.loadImage("/textures/ball.gif");
        paddle = paddlePic.crop(0, 0, Constants.PADDLE_WIDTH, Constants.PADDLE_HEIGHT);
    }
}
