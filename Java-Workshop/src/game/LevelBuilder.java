package game;

import game.entities.Brick;

import java.util.ArrayList;

public class LevelBuilder {
    public static int currentLevel = 0;
    public static int numberOfBricks;
    public static Brick[] bricks;

    private static final int leftEnd = (Constants.WIDTH - Constants.BRICKS_IN_A_ROW * (Constants.BRICK_WIDTH + 2)) / 2;
    private static final int topEnd = 50;
    private static final int brickWidth = Constants.BRICK_WIDTH;
    private static final int brickHeight = Constants.BRICK_HEIGHT;

   public static void buildLevel(int currentLevel) {
       numberOfBricks = Levels.getNumberOfBricks(currentLevel);
       bricks = new Brick[numberOfBricks];
       String[] level = Levels.brickPattern.get(currentLevel);

       int k = 0;
       for (int row = 0; row < level.length; row++) {
           for (int col = 0; col < Constants.BRICKS_IN_A_ROW; col++) {
               if (level[row].charAt(col) == 'X') {
                   bricks[k] = new Brick(leftEnd + col * (brickWidth + 1), topEnd + row * (brickHeight + 2));
                   k++;
               }
           }
       }
   }

}
