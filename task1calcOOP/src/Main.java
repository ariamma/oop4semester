import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String string = in.nextLine();
        in.close();
        Parser parser = new Parser();
        parser.expression = string;
        parser.position = 0;
        while (string.charAt(parser.position) == ' ') {
            parser.position++;
        }
        Tree tree;
        try {
            tree = parser.S();
            Calculator calc = new Calculator();
            int result = calc.evaluate(tree);
            System.out.println(result);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
    }
}
