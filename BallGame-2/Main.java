import javax.swing.JFrame;
import java.io.IOException;

public class Main {
    protected static BallWorld bw;
    public static void main(String[] args) throws IOException {

 
        JFrame frame = new JFrame("A World of Balls");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(bw = new BallWorld(640, 480)); // BallWorld is a JPanel
        frame.pack();            // Preferred size of BallWorld
        frame.setVisible(true);  // Show it
        new Server().runServer();
        
    }
}