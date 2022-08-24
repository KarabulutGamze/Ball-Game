import java.io.IOException;
import java.net.Socket;

public class Client {


    public static void main(String[] args) throws IOException {
        // write your code here
        System.out.println("welcome client");
        System.out.println(args[0]);
        System.out.println(args[1]);
        float Y = Float.parseFloat(args[0]);
        float angle = Float.parseFloat(args[1]);
        Socket socket = new Socket("localhost", 4321);
        System.out.println("Client connected");
        new clientThread(socket,Y,angle).start();

    }
}