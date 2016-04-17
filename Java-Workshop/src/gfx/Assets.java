package gfx;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Assets {

    private static final int width = 87, height = 30;

    public static BufferedImage player1;
    //Loads every resource needed for the game
    public static void init() {
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/paddle.gif"));

        player1 = sheet.crop(0, 0, width, height);
    //    player2 = sheet.crop(width, 0, width, height);
    }
}
