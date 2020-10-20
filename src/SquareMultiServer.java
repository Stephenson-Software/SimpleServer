import java.io.IOException;
import java.net.ServerSocket;

public class SquareMultiServer {

    public static void main(String[] args) throws IOException {

        int portNumber = 5382;

        boolean listening = true;

        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);

            while (listening) {
                System.out.println("Listening...");
                new SquareMultiServerThread(serverSocket.accept()).start();
                System.out.println("New connection!");
            }

        } catch(Exception e) {
            System.out.println("Error: Could not listen on port " + portNumber);
        }

    }

}
