public class Calculator {
    public int evaluate(Tree tree) throws ParseException {
        if (tree.root == null) {
            throw new ParseException("Invalid input: Empty tree");
        }
        return evaluateNode(tree.root);
    }

    private int evaluateNode(Node node) throws ParseException {
        if (node == null) {
            throw new ParseException("Invalid input: Empty node");
        }
        if (isNumericString(node.value)) {
            return Integer.parseInt(node.value);
        } else {
            int leftValue = evaluateNode(node.left);
            int rightValue = evaluateNode(node.right);
            return calculate(leftValue, rightValue, node.value);
        }
    }

    private int calculate(int left, int right, String operator) throws ParseException {
        return switch (operator) {
            case "+" -> left + right;
            case "-" -> left - right;
            case "*" -> left * right;
            case "/" -> {
                if (right == 0) {
                    throw new ParseException("Division by zero");
                }
                yield left / right;
            }
            default -> throw new ParseException("Invalid operator: " + operator);
        };
    }

    private boolean isNumericString(String string) {
        return string.chars().allMatch(Character::isDigit);
    }
}