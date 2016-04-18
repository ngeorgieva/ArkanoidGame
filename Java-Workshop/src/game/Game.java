package game;

import display.Display;
import gfx.ImageLoader;
import gfx.Assets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends JPanel implements Runnable {
    private Display display;
    public int width, height;
    public String title;
    public static Ball ball;
    private boolean running = false;
    private Thread thread;
    //private boolean ingame = true;
    private InputHandler inputHandler;
    private BufferStrategy bs;
    private Graphics g;
    private String message = "Game Over";
    private BufferedImage bckgrImage;
    private int points;
    //private SpriteSheet sh;

    //Paddle
    public static Paddle paddle;
    private Brick bricks[];

    public Game(String title, int width, int height) {
        this.width = width;
        this.height = height;
        this.title = title;
        this.points = 0;
    }

    //Initializes all the graphics and it will get
    //everything ready for our game
    private void init() {
        //Initializing a new display.Display object
        this.display = new Display(this.title, this.width, this.height);
        this.bckgrImage = ImageLoader.loadImage("/textures/test2.png");
        //this.sh = new SpriteSheet(ImageLoader.loadImage("/textures/test.gif"));
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

        //Setting the currentState to gameState because we do not have
        //any more states set up
        //       StateManager.setState(gameState);

        paddle = new Paddle();
    }


    //The method that will update all the variables
    private void tick() {

        paddle.tick();
        ball.move();
        this.checkForCollision();
    }

//    private void drawObjects(Graphics2D g2d) {
//        for (int i = 0; i < Constants.N_OF_BRICKS; i++) {
//            if (!bricks[i].isDestroyed()) {
//                g2d.drawImage(bricks[i].getImage(), bricks[i].getX(),
//                        bricks[i].getY(), bricks[i].getWidth(),
//                        bricks[i].getHeight(), this);
//            }
//        }
//    }

    //The method that will draw everything on the canvas
    private void render() {
        //Setting the bufferStrategy to be the one used in our canvas
        //Gets the number of buffers that the canvas should use.
        this.bs = display.getCanvas().getBufferStrategy();
        //If our bufferStrategy doesn't know how many buffers to use
        //we create some manually
        if (bs == null) {
            //Create 2 buffers
            display.getCanvas().createBufferStrategy(2);
            //returns out of the method to prevent errors
            return;
        }
//        Graphics2D g2d = (Graphics2D) g;
//        if (ingame) {
//            drawObjects(g2d);
//        } else {
//
//        }


        //Instantiates the graphics related to the bufferStrategy
        g = bs.getDrawGraphics();
        //Clear the screen at every frame
        g.clearRect(0, 0, this.width, this.height);
        //Beginning of drawing things on the screen

        g.drawImage(this.bckgrImage, 0, 0, this.width, this.height, null);

        paddle.render(g);
        ball.render(g);

        for (Brick brick : bricks) {
            if (!brick.isDestroyed()) {
                brick.render(g);
            }
        }
        //g.setColor(Color.red);

        //End of drawing objects

        //Enables the buffer
        bs.show();
        //Shows everything stored in the Graphics object
        g.dispose();
    }

    private void gameFinished(Graphics g) {

        Font font = new Font("Verdana", Font.BOLD, 18);
        FontMetrics metr = this.getFontMetrics(font);
        g.drawString(message,
                (Constants.WIDTH - metr.stringWidth(message)) / 2,
                Constants.WIDTH / 2);

    }

    //Implementing the interface's method
    @Override
    public void run() {
        init();

        //Sets the frames per seconds
        int fps = 30;
        //1 000 000 000 nanoseconds in a second. Thus we measure time in nanoseconds
        //to be more specific. Maximum allowed time to run the tick() and render() methods
        double timePerTick = 1_000_000_000.0 / fps;
        //How much time we have until we need to call our tick() and render() methods
        double delta = 0;
        //The current time in nanoseconds
        long now;
        //Returns the amount of time in nanoseconds that our computer runs.
        long lastTime = System.nanoTime();
        long timer = 0;
        int ticks = 0;

        while (running) {
            //Sets the now variable to the current time in nanoseconds
            now = System.nanoTime();
            //Amount of time passed divided by the max amount of time allowed.
            delta += (now - lastTime) / timePerTick;
            //Adding to the timer the time passed
            timer += now - lastTime;
            //Setting the lastTime with the values of now time after we have calculated the delta
            lastTime = now;

            //If enough time has passed we need to tick() and render() to achieve 60 fps
            if (delta >= 1) {
                tick();
                render();
                //Reset the delta
                ticks++;
                delta--;
            }

            if (timer >= 1_000_000_000) {
                System.out.println("Ticks and Frames: " + ticks);
                ticks = 0;
                timer = 0;
            }
        }

        //Calls the stop method to ensure everything has been stopped
        stop();
    }

    //Creating a start method for the Thread to start our game
    //Synchronized is used because our method is working with threads
    //so we ensure ourselves that nothing will go bad
    public synchronized void start() {
        //If the game is running exit the method
        //This is done in order to prevent the game to initialize
        //more than enough threads
        if (running) {
            return;
        }
        //Setting the while-game-loop to run

        running = true;
        //Initialize the thread that will work with "this" class (game.Game)
        thread = new Thread(this);
        //The start method will call start the new thread and it will call
        //the run method in our class
        thread.start();
    }

    //Creating a stop method for the Thread to stop our game
    public synchronized void stop() {
        //If the game is not running exit the method
        //This is done to prevent the game from stopping a
        //non-existing thread and cause errors
        if (!running) {
            return;
        }
        running = false;

        //The join method stops the current method from executing and it
        //must be surrounded in try-catch in order to work
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void checkForCollision() {

        if (ball.getBoundingBox().getMaxY() > Constants.BOTTOM_EDGE) {
            this.stop();
        }

        for (int i = 0, j = 0; i < Constants.N_OF_BRICKS; i++) {

            if (bricks[i].isDestroyed()) {
                j++;
            }

            if (j == Constants.N_OF_BRICKS) {

                message = "Victory";
                this.stop();
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
                ball.setXDir(0);
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
}
