import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;


public class BallWorld extends JPanel {
    private static final int UPDATE_RATE = 30;    // Frames per second (fps)

    private Ball ball;         // A single bouncing Ball's instance
    private ContainerBox box;  // The container rectangular box

    private DrawCanvas canvas; // Custom canvas for drawing the box/ball
    private int canvasWidth;
    private int canvasHeight;

    /**
     * Constructor to create the UI components and init the game objects.
     * Set the drawing canvas to fill the screen (given its width and height).
     *
     * @param width : screen width
     * @param height : screen height
     */
    public BallWorld(int width, int height) {

        canvasWidth = width;
        canvasHeight = height;

        // Init the ball at a random location (inside the box) and moveAngle
        Random rand = new Random();
        int radius = 20;
        int x = rand.nextInt(canvasWidth - radius * 2 - 20) + radius + 10;
        int y = rand.nextInt(canvasHeight - radius * 2 - 20) + radius + 10;
        int speed = 10;
        int angleInDegree = rand.nextInt(360);
        ball = new Ball(x, y, radius, speed, angleInDegree, Color.BLUE);

        box = new ContainerBox(0, 0, canvasWidth, canvasHeight, Color.BLACK, Color.WHITE);

        canvas = new DrawCanvas();
        this.setLayout(new BorderLayout());
        this.add(canvas, BorderLayout.CENTER);

        // Handling window resize.
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Component c = (Component)e.getSource();
                Dimension dim = c.getSize();
                canvasWidth = dim.width;
                canvasHeight = dim.height;
                // Adjust the bounds of the container to fill the window
                box.set(0, 0, canvasWidth, canvasHeight);
            }
        });

        // Start the ball bouncing
        gameStart();
    }

    public void gameStart(){
        gameThread gmthr = new gameThread(this,UPDATE_RATE,ball,box);
        gmthr.start();

    }
    
    public void gameUpdate() {
        ball.moveOneStepWithCollisionDetection(box);

    }

    public void createReceivedBall(sendObject ballInfo) {
        ball = new Ball(box.maxX - 20, ballInfo.y, 20, 10, ballInfo.moveAngle, Color.BLUE); //40 is double of radius
        ball.speedX = - ball.speedX; //to get correct direction of movement
        System.out.println("return ball created");
        gameStart();
    }

    /** The custom drawing panel for the bouncing ball (inner class). */
    class DrawCanvas extends JPanel {
        /** Custom drawing codes */
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);    // Paint background
            // Draw the box and the ball
            box.draw(g);
            ball.draw(g);

        }

        /** Called back to get the preferred size of the component. */
        @Override
        public Dimension getPreferredSize() {
            return (new Dimension(canvasWidth, canvasHeight));
        }
    }
}