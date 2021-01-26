package SimpleServer.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import SimpleServer.Message;

public class Client {

    public static void main(String[] args) throws IOException {

        String hostName = "Jeff-From-Surplus";
        int portNumber = 5382;

        try {

            Socket socket = new Socket(hostName, portNumber);

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            sendSquareRequest("3", out, in);

        } catch (UnknownHostException e) {
            System.out.println("Unknown host.");
        } catch (IOException e) {
            System.out.println("Something went wrong.");
        }

    }

    private static void sendSquareRequest(String number, PrintWriter out, BufferedReader in) {
        try {
            Message message = new Message();
            message.put("process", "square");
            message.put("number", "" + number);

            // send request
            out.println(message.toString());

            // receive message
            Message receivedMessage = new Message();
            receivedMessage.fromString(in.readLine());

            // print answer
            String success = (String) receivedMessage.get("success");

            if (success.equals("true")) {
                System.out.println(receivedMessage.get("answer"));
            }
            else {
                System.out.println("Reason for failure: " + receivedMessage.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
