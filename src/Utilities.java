import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;

public class Utilities {

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
