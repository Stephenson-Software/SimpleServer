import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class SquareClient {

    public static void main(String[] args) throws IOException {

        String hostName = "Jeff-From-Surplus";
        int portNumber = 5382;

        try {

            Socket socket = new Socket(hostName, portNumber);

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

            String userInput;

            while ((userInput = stdIn.readLine()) != null) {

                out.println(userInput);
                System.out.println(in.readLine());

            }

        } catch (UnknownHostException e) {
            System.out.println("Host unknown");
        } catch (IOException e) {
            System.out.println("Something went wrong with the I/O connection.");
        }

    }

}
