import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;

public class SquareProtocol {
    public String processInput(String input) {
        String toReturn = "";
        try {
            JSONObject message = parseJSONStringIntoJSONObject(input);

            String process = (String) message.get("process");

            switch(process) {
                case "square":
                    toReturn = squareInput(message);
                    break;
                default:
                    ArrayList<String> keys = new ArrayList<>();
                    ArrayList<Object> values = new ArrayList<>();
                    keys.add("success");
                    values.add(false);

                    toReturn = packageIntoJSONString(keys, values);
                    break;
            }
        } catch (Exception e) {
            ArrayList<String> keys = new ArrayList<>();
            ArrayList<Object> values = new ArrayList<>();
            keys.add("success");
            values.add(false);

            toReturn = packageIntoJSONString(keys, values);

            System.out.printf("Something went wrong when processing input.");
        }

        return toReturn;
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

    private String squareInput(JSONObject message) {
        String inputLine = (String) message.get("number");

        System.out.println("Attempting to square " + inputLine + "...");
        int number;

        try {
            number = Integer.parseInt(inputLine);
        } catch (Exception e) {
            return "Wrong input!";
        }

        int square = number * number;
        System.out.println("Square: " + square);

        JSONObject returnMessage = new JSONObject();
        returnMessage.put("success", true);
        returnMessage.put("answer", "Square of " + number + " is " + square);

        ArrayList<String> keys = new ArrayList<>();
        ArrayList<Object> values = new ArrayList<>();
        keys.add("success");
        values.add(true);
        keys.add("answer");
        values.add("Square of " + number + " is " + square);

        return packageIntoJSONString(keys, values);
    }
}
