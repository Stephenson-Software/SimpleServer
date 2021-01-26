package SimpleServer.client;

import SimpleServer.Message;

public class ClientApp {

    public static void main(String[] args) {
        Client client = new Client("hostname", 2000);
        sendSquareRequest("5", client);
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
