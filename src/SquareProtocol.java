public class SquareProtocol {
    public String processInput(String input) {
        return squareInput(input);
    }

    private String squareInput(String inputLine) {
        int number;

        try {
            number = Integer.parseInt(inputLine);
        } catch (Exception e) {
            return "Wrong input!";
        }

        int square = number * number;

        return "Square of " + number + " is " + square;
    }
}
