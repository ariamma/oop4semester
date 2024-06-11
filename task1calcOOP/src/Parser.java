class ParseException extends Exception {
    public ParseException(String message) {
        super(message);
    }
}

class Node {
    public String value;
    Node left;
    Node right;

    public Node(String data) {
        this.value = data;
        this.left = null;
        this.right = null;
    }
    public Node(char data) {
        this.value = String.valueOf(data);
        this.left = null;
        this.right = null;
    }
}

class Tree{
    Node root;

    public Tree() {
        this.root = null;
    }

    public Tree(String data) {
        this.root = new Node(data);
    }
    public Tree(char data) {
        this.root = new Node(data);
    }
    public Tree(char data, Tree left, Tree right) {
        this.root = new Node(data);
        this.root.left = left.root;
        this.root.right = right.root;
    }
    public Tree(String data, Tree left, Tree right) {
        this.root = new Node(data);
        this.root.left = left.root;
        this.root.right = right.root;
    }
}

class Parser {
    String expression;
    int position;
    public Tree S() throws ParseException {
        Tree Left = M();
        while (position < expression.length()) {
            char operation = expression.charAt(position);
            if (operation == '+' || operation == '-') {
                position++;
                skipSpaces();
                if (position >= expression.length()) {
                    throw new ParseException("Empty operand");
                }
                Tree Right = M();
                Left = new Tree(operation, Left, Right);
            } else {
                break;
            }
        }
        return Left;
    }

    private Tree M() throws ParseException {
        Tree Left = P();
        while (position < expression.length() && (expression.charAt(position) == '*' || expression.charAt(position) == '/')) {
            char operation = expression.charAt(position);
            position++;
            skipSpaces();
            if (position >= expression.length()) {
                throw new ParseException("Empty operand");
            }
            Tree Right = P();
            Left = new Tree(operation, Left, Right);
        }
        return Left;
    }

    private Tree P() throws ParseException {
        if (expression.charAt(position) == '(') {
            position++;
            skipSpaces();
            Tree tree = S();
            if (expression.charAt(position) != ')') {
                throw new ParseException("Missing closing parenthesis");
            }
            position++;
            skipSpaces();
            return tree;
        } else if (Character.isDigit(expression.charAt(position))) {
            StringBuilder sb = new StringBuilder();
            while (position < expression.length() && Character.isDigit(expression.charAt(position))) {
                sb.append(expression.charAt(position++));
            }
            return new Tree(sb.toString());
        } else {
            throw new ParseException("Unexpected character: " + expression.charAt(position));
        }
    }
    private void skipSpaces() {
        while (position < expression.length() && expression.charAt(position) == ' ') {
            position++;
        }
    }
    public Tree parse(String input) {
        position = 0;
        expression = input;
        try {
            return S();
        } catch (ParseException e) {
            return null;
        }
    }
}