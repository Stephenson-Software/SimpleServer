import java.io.IOException;
import java.net.ServerSocket;

public class MultiServer {

    public static void main(String[] args) throws IOException {

        int portNumber = 5382;

        boolean listening = true;

        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);

            while (listening) {
                System.out.println("Listening for a new connection...");
                new MSThread(serverSocket.accept()).start();
                System.out.println("A new connection has been made!");
            }

        } catch(Exception e) {
            System.out.println("ERROR: Was not able to listen on port " + portNumber);
        }

    }

}
