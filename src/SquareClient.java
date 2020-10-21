import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class SquareClient {

    public static void main(String[] args) throws IOException {

        String hostName = "Jeff-From-Surplus";
        int portNumber = 5382;

        try {

            Socket socket = new Socket(hostName, portNumber);

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            sendSquareRequest("10", out, in);

        } catch (UnknownHostException e) {
            System.out.println("Host unknown");
        } catch (IOException e) {
            System.out.println("Something went wrong with the I/O connection.");
        }

    }

    private static void sendSquareRequest(String number, PrintWriter out, BufferedReader in) {
        try {
            ArrayList<String> keys = new ArrayList<>();
            ArrayList<Object> values = new ArrayList<>();
            keys.add("process");
            values.add("square");
            keys.add("number");
            values.add(number);

            // send request
            out.println(packageIntoJSONString(keys, values));

            // receive message
            JSONObject receivedMessage = parseJSONStringIntoJSONObject(in.readLine());

            // print answer
            if (receivedMessage.get("success").equals(true)) {
                System.out.println(receivedMessage.get("answer"));
            }
            else {
                System.out.println("Request was unsuccessful!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static String packageIntoJSONString(ArrayList<String> keys, ArrayList<Object> values) {
        assert(keys.size() == values.size());
        JSONObject message = new JSONObject();
        for (int i = 0; i < keys.size(); i++) {
            message.put(keys.get(i), values.get(i));
        }
        return message.toJSONString();
    }

    private static JSONObject parseJSONStringIntoJSONObject(String jsonString) {
        JSONParser parser = new JSONParser();
        try {
            return (JSONObject) parser.parse(jsonString);
        } catch(Exception e) {
            System.out.printf("Something went wrong with parsing the jsonString " + jsonString + "!");
        }
        return null;
    }

}
