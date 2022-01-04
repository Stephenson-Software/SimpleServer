package SimpleServer.client;

import SimpleServer.Message;

import java.util.Scanner;

/**
 * @author Daniel Stephenson
 */
public class ClientApp {

    public static void main(String[] args) {
        String hostname = "Walter";
        int number = 2000;
        Client client = new Client(hostname, number);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String toSend = scanner.nextLine();

            if (toSend.equals("done")) {
                break;
            }

            sendSquareRequest(toSend, client);
        }

        client.disconnect();
    }

    private static void sendSquareRequest(String number, Client client) {
        Message message = new Message();
        message.put("process", "square");
        message.put("number", "" + number);

        // send request
        client.sendStringToServer(message.toString());

        // receive message
        Message receivedMessage = new Message();
        receivedMessage.fromString(client.getStringFromServer());

        // print answer
        String success = (String) receivedMessage.get("success");

        if (success.equals("true")) {
            System.out.println(receivedMessage.get("answer"));
        }
        else {
            System.out.println("Reason for failure: " + receivedMessage.get("reason"));
        }
    }
}