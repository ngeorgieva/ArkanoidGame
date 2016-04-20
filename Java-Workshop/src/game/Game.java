package game;

import display.Display;
import game.entities.Ball;
import game.entities.Brick;
import game.entities.Paddle;
import gfx.ImageLoader;
import gfx.Assets;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends JPanel implements Runnable {
    private Display display;
    public int width, height;
    public String title;
    public static Ball ball;
    private boolean isRunning = false;
    private Thread thread;
    private InputHandler inputHandler;
    private BufferStrategy bs;
    private Graphics g;
    private String message;
    private BufferedImage bckgrImage;
    private int points;
    public static boolean isLevelWon;

    public static Paddle paddle;
    private Brick bricks[];

    public static enum STATE {
        MENU,
        GAME,
        LEVEL_WON
    };
    public static STATE state;

    Menu menu;

    public Game(String title, int width, int height) {
        this.width = width;
        this.height = height;
        this.title = title;
        this.display = new Display(this.title, this.width, this.height);
        this.points = 0;
        this.menu = new Menu();
        state = STATE.MENU;
        this.inputHandler = new InputHandler(this.display);
        init();
        start();
    }

    @Override
    public void run() {
        int fps = 30;
        double timePerTick = 1_000_000_000.0 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();

        while (isRunning) {
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            lastTime = now;

            if (delta >= 1) {
                tick();
                delta--;
            }
            render();
        }

        stop();
    }

    public synchronized void start() {

        if (isRunning) {
            return;
        }

        isRunning = true;
        thread = new Thread(this);

        thread.start();
    }

    public synchronized void stop() {

        if (!isRunning) {
            return;
        }
        isRunning = false;

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void init() {
        this.bckgrImage = ImageLoader.loadImage("/textures/backgroundNew.png");
        ball = new Ball();
        LevelBuilder.buildLevel(LevelBuilder.currentLevel);
        this.bricks = LevelBuilder.bricks;

        Assets.init();

        paddle = new Paddle();
    }

    private void tick() {
        if (state == STATE.GAME) {
            paddle.tick();
            ball.move();
            this.checkForCollision();
        }
    }

    private void render() {

        this.bs = display.getCanvas().getBufferStrategy();

        if (bs == null) {
            display.getCanvas().createBufferStrategy(2);
            return;
        }

        g = bs.getDrawGraphics();

        g.clearRect(0, 0, this.width, this.height);

            if (isRunning) {
                g.drawImage(this.bckgrImage, 0, 0, this.width, this.height, null);

                if (state == STATE.GAME) {
                    paddle.render(g);
                    ball.render(g);

                    for (Brick brick : bricks) {
                        if (!brick.isDestroyed()) {
                            brick.render(g);
                        }
                    }

                    g.setFont(new Font("Arial", Font.BOLD, 15));
                    g.setColor(Color.WHITE);
                    g.drawString(String.format("SCORE %d", points), 5, 15);

                } else if (state == STATE.MENU) {
                    g.drawImage(this.bckgrImage, 0, 0, this.width, this.height, null);

                    Font font = new Font("Courier New", Font.BOLD, 35);
                    FontMetrics fm = this.getFontMetrics(font);

                    g.setColor(Color.orange);
                    g.setFont(font);
                    g.drawString(title,
                            (Constants.WIDTH - fm.stringWidth(title)) / 2,
                            Constants.HEIGHT / 4);

                    Menu.render(g);
                } else if (state == STATE.LEVEL_WON) {
                    this.getFinalScreen(g);
                }

            } else {
                this.getFinalScreen(g);
            }

        bs.show();
        g.dispose();
    }

    private void checkForCollision() {

        if (ball.getBoundingBox().getMaxY() > Constants.BOTTOM_EDGE) {
            message = "Game Over";
            this.isRunning = false;
        }

        for (int i = 0, j = 0; i < LevelBuilder.numberOfBricks; i++) {

            if (bricks[i].isDestroyed()) {
                j++;
            }

            if (j == LevelBuilder.numberOfBricks) {
                LevelBuilder.currentLevel++;
                System.out.println("Current level " + LevelBuilder.currentLevel);
                System.out.println("Number of levels" + Levels.numberOfLevels);
                if (LevelBuilder.currentLevel < Levels.numberOfLevels) {
                    message = "Level won!";
                    state = STATE.LEVEL_WON;
                } else {
                    message = "Victory";
                    this.isRunning = false;
                }

                //this.isRunning = false;
            }
        }

        if ((ball.getBoundingBox()).intersects(paddle.getBoundingBox())) {

            int paddleLPos = (int) paddle.getBoundingBox().getMinX();
            int ballLPos = (int) ball.getBoundingBox().getMinX();

            int first = paddleLPos + 8;
            int second = paddleLPos + 16;
            int third = paddleLPos + 24;
            int fourth = paddleLPos + 32;

            if (ballLPos < first) {
                ball.setXDir(-1);
                ball.setYDir(-1);
            }

            if (ballLPos >= first && ballLPos < second) {
                ball.setXDir(-1);
                ball.setYDir(-1 * ball.getYDir());
            }

            if (ballLPos >= second && ballLPos < third) {
                ball.setXDir(1);
                ball.setYDir(-1);
            }

            if (ballLPos >= third && ballLPos < fourth) {
                ball.setXDir(1);
                ball.setYDir(-1 * ball.getYDir());
            }

            if (ballLPos > fourth) {
                ball.setXDir(1);
                ball.setYDir(-1);
            }
        }

        for (int i = 0; i < LevelBuilder.numberOfBricks; i++) {

            if ((ball.getBoundingBox()).intersects(bricks[i].getBoundingBox())) {

                int ballLeft = (int) ball.getBoundingBox().getMinX();
                int ballHeight = (int) ball.getBoundingBox().getHeight();
                int ballWidth = (int) ball.getBoundingBox().getWidth();
                int ballTop = (int) ball.getBoundingBox().getMinY();

                Point pointRight = new Point(ballLeft + ballWidth + 1, ballTop);
                Point pointLeft = new Point(ballLeft - 1, ballTop);
                Point pointTop = new Point(ballLeft, ballTop - 1);
                Point pointBottom = new Point(ballLeft, ballTop + ballHeight + 1);

                if (!bricks[i].isDestroyed()) {
                    if (bricks[i].getBoundingBox().contains(pointRight)) {
                        ball.setXDir(-1);
                    } else if (bricks[i].getBoundingBox().contains(pointLeft)) {
                        ball.setXDir(1);
                    }

                    if (bricks[i].getBoundingBox().contains(pointTop)) {
                        ball.setYDir(1);
                    } else if (bricks[i].getBoundingBox().contains(pointBottom)) {
                        ball.setYDir(-1);
                    }

                    bricks[i].setDestroyed(true);
                    points += 10;
                }
            }
        }
    }

    private void getFinalScreen(Graphics g) {
        g.drawImage(this.bckgrImage, 0, 0, this.width, this.height, null);

        Font font = new Font("Verdana", Font.BOLD, 18);
        FontMetrics metr = this.getFontMetrics(font);

        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(message,
                (Constants.WIDTH - metr.stringWidth(message)) / 2,
                Constants.WIDTH / 2);
        String pointsMessage = String.format("Points: %d", points);
        g.drawString(String.format(pointsMessage),
                (Constants.WIDTH - metr.stringWidth(pointsMessage)) / 2,
                Constants.WIDTH / 2 + 30);

        if (state == STATE.LEVEL_WON) {
            g.drawString("Press Space to continue",
                    (Constants.WIDTH - metr.stringWidth("Press Space to continue")) / 2,
                    Constants.WIDTH / 2 + 60);
        }
    }
}
