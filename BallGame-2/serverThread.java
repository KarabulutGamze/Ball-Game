import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class serverThread extends Thread {
    Socket socket;
    serverThread(Socket socket){
        this.socket = socket;
    }
    public void run(){
        try {
            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
            sendObject returnBall = (sendObject) is.readObject();
            System.out.println(returnBall.y);
            Main.bw.createReceivedBall(returnBall);
            socket.close();
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}