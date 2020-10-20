public class SquareProtocol {
    public String processInput(String input) {
        return squareInput(input);
    }

    private String squareInput(String inputLine) {
        System.out.println("Attempting to square " + inputLine + "...");
        int number;

        try {
            number = Integer.parseInt(inputLine);
        } catch (Exception e) {
            return "Wrong input!";
        }

        int square = number * number;
        System.out.println("Square: " + square);
        return "Square of " + number + " is " + square;
    }
}
