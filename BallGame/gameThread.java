import java.awt.*;
import java.io.IOException;
import java.net.UnknownHostException;


public class gameThread extends Thread {
    BallWorld bw;
    int updaterate;
    Ball ball;
    ContainerBox box;
    Client client;

    public gameThread(BallWorld bw , int updaterate, Ball ball, ContainerBox box) {   // constructor
        this.bw = bw;
        this.updaterate =updaterate;
        this.ball = ball;
        this.box = box;
    }
    public void run() {
        while (true) {
            // Execute one time-step for the game
            if (ball == null) {return;}
            bw.gameUpdate();
            // Refresh the display
            bw.repaint();

            // Delay and give other thread a chance
            try {
                if (ball.x == box.maxX - ball.radius){
                    System.out.println("hit right wall");
                    String YtoSend = String.valueOf(ball.y);
                    float angle = ball.getMoveAngle();
                    String sendAngle = String.valueOf(angle);
                    String[] arguments = new String[] {YtoSend,sendAngle};
                    ball.setColor(ball, Color.BLACK);
                    bw.repaint();
                    ball = null;
                    interrupt();
                    client.main(arguments);
                }
                Thread.sleep(1000 / updaterate);
            } catch (InterruptedException ex) {

            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}