package SimpleServer.server;

public class Protocol {
    public String processInput(String input) {
        String toReturn = "";
        try {
            Message receivedMessage = new Message();
            receivedMessage.fromString(input);

            String process = (String) receivedMessage.get("process");

            switch(process) {
                case "square":
                    toReturn = squareInput(receivedMessage);
                    break;
                default:
                    Message message = new Message();
                    message.put("success", "false");
                    message.put("reason", "process not recognized");

                    toReturn = message.toString();
                    break;
            }
        } catch (Exception e) {
            Message message = new Message();
            message.put("success", "false");
            message.put("reason", "Something went wrong when processing input.");

            toReturn = message.toString();

            System.out.println("Something went wrong when processing input.");
        }

        return toReturn;
    }



    private String squareInput(Message receivedMessage) {
        String inputLine = (String) receivedMessage.get("number");

        System.out.println("Squaring " + inputLine);
        int number;

        try {
            number = Integer.parseInt(inputLine);
        } catch (Exception e) {
            Message message = new Message();
            message.put("success", "false");
            message.put("reason", "Wrong type of input!");

            return message.toString();
        }

        int square = number * number;
        System.out.println("Answer: " + square);


        Message message = new Message();
        message.put("success", "true");
        message.put("answer", "The square of that number is " + square);

        return message.toString();
    }
}
