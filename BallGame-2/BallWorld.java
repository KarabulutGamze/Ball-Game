import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class BallWorld extends JPanel {
    private static final int UPDATE_RATE = 30;    // Frames per second (fps)

    //private Ball ball;         // A single bouncing Ball's instance
    Ball receivedBall;
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

        // Init the Container Box to fill the screen
        box = new ContainerBox(0, 0, canvasWidth, canvasHeight, Color.BLACK, Color.WHITE);

        // Init the custom drawing panel for drawing the game
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
        //gameStart();
    }

    public void gameStart(){
        gameThread gmthr = new gameThread(this,UPDATE_RATE,receivedBall,box);
        gmthr.start();

    }
    
    public void gameUpdate() {
        receivedBall.moveOneStepWithCollisionDetection(box);
    }

    /** The custom drawing panel for the bouncing ball (inner class). */
    class DrawCanvas extends JPanel {
        /** Custom drawing codes */
        @Override
        public void paintComponent(Graphics g) {
            box.draw(g);
            if (receivedBall == null) return;
            super.paintComponent(g);    // Paint background
            // Draw the box and the ball
            box.draw(g);
            receivedBall.draw(g);

        }

        /** Called back to get the preferred size of the component. */
        @Override
        public Dimension getPreferredSize() {
            return (new Dimension(canvasWidth, canvasHeight));
        }
    }
    public void createReceivedBall (sendObject ballInfo){
        receivedBall = new Ball(20, ballInfo.y, 20, 10, ballInfo.moveAngle, Color.BLUE); //40 is double of radius
        receivedBall.speedX = - receivedBall.speedX; //to get correct direction of movement
        gameStart();
    }
}
