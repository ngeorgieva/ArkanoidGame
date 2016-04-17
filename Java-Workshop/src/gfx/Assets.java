package gfx;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Assets {

    private static final int paddleWidth = 87;
    private static final int paddleHeight = 30;

    public static BufferedImage paddle;
    public static BufferedImage brick;

    //Loads every resource needed for the game
    public static void init() {
        SpriteSheet paddlePic = new SpriteSheet(ImageLoader.loadImage("/textures/test.gif"));
        brick = ImageLoader.loadImage("/textures/blueBrick2.png");

        paddle = paddlePic.crop(0, 0, paddleWidth, paddleHeight);
    //    player2 = sheet.crop(width, 0, width, height);
    }
}
