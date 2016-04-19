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
    private String message = "Game Over";
    private BufferedImage bckgrImage;
    private int points;

    public static Paddle paddle;
    private Brick bricks[];

    public Game(String title, int width, int height) {
        this.width = width;
        this.height = height;
        this.title = title;
        this.points = 0;
    }

    @Override
    public void run() {
        init();

        int fps = 30;
        double timePerTick = 1_000_000_000.0 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;
        int ticks = 0;

        while (isRunning) {
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            timer += now - lastTime;
            lastTime = now;

            if (delta >= 1) {
                tick();
                render();
                ticks++;
                delta--;
            }

            if (timer >= 1_000_000_000) {
                ticks = 0;
                timer = 0;
            }
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

    private void init() {
        this.display = new Display(this.title, this.width, this.height);
        this.bckgrImage = ImageLoader.loadImage("/textures/backgroundNew.png");
        ball = new Ball();
        this.bricks = new Brick[Constants.N_OF_BRICKS];
        int k = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                bricks[k] = new Brick(j * 41 + 25, i * 11 + 50);
                k++;
            }
        }

        this.inputHandler = new InputHandler(this.display);
        Assets.init();

        paddle = new Paddle();
    }

    private void tick() {

        paddle.tick();
        ball.move();
        this.checkForCollision();
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

            paddle.render(g);
            ball.render(g);

            for (Brick brick : bricks) {
                if (!brick.isDestroyed()) {
                    brick.render(g);
                }
            }
        } else {
            this.getFinalScreen(g);
        }

        bs.show();
        g.dispose();
    }

    private void checkForCollision() {

        if (ball.getBoundingBox().getMaxY() > Constants.BOTTOM_EDGE) {
            this.isRunning = false;
        }

        for (int i = 0, j = 0; i < Constants.N_OF_BRICKS; i++) {

            if (bricks[i].isDestroyed()) {
                j++;
            }

            if (j == Constants.N_OF_BRICKS) {

                message = "Victory";
                this.isRunning = false;
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

        for (int i = 0; i < Constants.N_OF_BRICKS; i++) {

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

        Font font = new Font("Verdana", Font.BOLD, 18);
        FontMetrics metr = this.getFontMetrics(font);

        g.setColor(Color.BLACK);
        g.setFont(font);
        g.drawString(message,
                (Constants.WIDTH - metr.stringWidth(message)) / 2,
                Constants.WIDTH / 2);
        String pointsMessage = String.format("Points: %d", points);
        g.drawString(String.format(pointsMessage),
                (Constants.WIDTH - metr.stringWidth(pointsMessage)) / 2,
                Constants.WIDTH / 2 + 30);
    }
}
