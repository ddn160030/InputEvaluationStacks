import java.util.StringTokenizer;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class FA2024_EvaluationExpressionWithGenericStack_NguyenD {
    // Stack to store numbers
    private GenericStack<Integer> operandStack;
    // Stack to store operators (+, -, *, /, parentheses)
    private GenericStack<Character> operatorStack;

    // Constructor - initializes both stacks
    public FA2024_EvaluationExpressionWithGenericStack_NguyenD() {
        operandStack = new GenericStack<Integer>();
        operatorStack = new GenericStack<Character>();
    }

    /**
     * Checks if a string token can be converted to a number
     */
    private boolean isNumber(String token) {
        try {
            Integer.parseInt(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Processes one operator by taking two numbers from operandStack,
     * performing the operation, and pushing the result back
     */
    private void processOperator() {
        char operator = operatorStack.pop();

        // Need at least 2 numbers to perform operation
        if (operandStack.getSize() < 2) {
            throw new IllegalStateException("Not enough operands");
        }

        // Pop numbers in reverse order for subtraction and division
        int number2 = operandStack.pop();
        int number1 = operandStack.pop();
        int result;

        // Perform the arithmetic operation
        switch (operator) {
            case '+': result = number1 + number2; break;
            case '-': result = number1 - number2; break;
            case '*': result = number1 * number2; break;
            case '/': result = number1 / number2; break;
            default: throw new IllegalStateException("Unknown operator");
        }
        operandStack.push(result);
    }

    /**
     * Main method to evaluate a mathematical expression
     * Returns the final calculated result
     */
    public int evaluateExpression(String expression) {
        // Reset stacks for new expression
        operandStack = new GenericStack<Integer>();
        operatorStack = new GenericStack<Character>();

        // Split expression into tokens (numbers and operators)
        StringTokenizer tokenizer = new StringTokenizer(expression, "+-*/()", true);

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().trim();
            if (token.isEmpty()) continue;

            if (isNumber(token)) {
                // If token is a number, push to operand stack
                operandStack.push(Integer.parseInt(token));
            } else if (token.equals("(")) {
                // Open parenthesis goes directly to operator stack
                operatorStack.push('(');
            } else if (token.equals(")")) {
                // Process operators until matching parenthesis is found
                while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                    processOperator();
                }
                if (!operatorStack.isEmpty()) {
                    operatorStack.pop(); // Remove '('
                }
            } else {
                // Handle operators based on precedence
                char currentOperator = token.charAt(0);
                while (!operatorStack.isEmpty() && shouldProcessOperator(operatorStack.peek(), currentOperator)) {
                    processOperator();
                }
                operatorStack.push(currentOperator);
            }
        }

        // Process any remaining operators
        while (!operatorStack.isEmpty()) {
            processOperator();
        }

        if (operandStack.isEmpty()) {
            throw new IllegalStateException("Invalid expression");
        }

        return operandStack.pop();
    }

    /**
     * Determines if an operator should be processed based on precedence rules
     * Returns true if the stack operator should be processed before the current operator
     */
    private boolean shouldProcessOperator(char stackOperator, char currentOperator) {
        if (stackOperator == '(') return false;
        // Multiplication and division have higher precedence
        if ((currentOperator == '*' || currentOperator == '/') &&
                (stackOperator == '+' || stackOperator == '-')) return false;
        return true;
    }

    /**
     * Main program loop - handles user input and file processing
     */
    public static void main(String[] args) {
        FA2024_EvaluationExpressionWithGenericStack_NguyenD evaluator =
                new FA2024_EvaluationExpressionWithGenericStack_NguyenD();
        Scanner scanner = new Scanner(System.in);

        // Main menu loop
        while (true) {
            System.out.println("\nFA2024_EvaluationExpressionWithGenericStack_NguyenD.java");
            System.out.println("MENU TO SELECT INPUT - DUSTIN NGUYEN");
            System.out.println("------------------------------------------");
            System.out.println("1. Read one Expression from the keyboard");
            System.out.println("2. Read expressions from an input file");
            System.out.println("0. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline after number

            if (choice == 0) break;

            if (choice == 1) {
                // Handle keyboard input
                System.out.println("\nEnter your expression:");
                String expression = scanner.nextLine();
                System.out.println("\nEXPRESSION FROM THE KEYBOARD - DUSTIN NGUYEN");
                System.out.println("------------------------------------------------------------");
                try {
                    int result = evaluator.evaluateExpression(expression);
                    System.out.printf("%s = %d%n", expression, result);
                } catch (Exception e) {
                    System.out.println("Error evaluating expression: " + e.getMessage());
                }
            }
            else if (choice == 2) {
                // Handle file input
                System.out.println("Enter input file name:");
                String fileName = scanner.nextLine();

                try {
                    File file = new File("expression.txt");
                    Scanner fileScanner = new Scanner(file);
                    System.out.println("\nEXPRESSION FROM THE INPUT FILE - DUSTIN NGUYEN");
                    System.out.println("------------------------------------------------------------");

                    // Process each line in the file
                    while (fileScanner.hasNextLine()) {
                        String expression = fileScanner.nextLine().trim();
                        if (!expression.isEmpty()) {
                            try {
                                int result = evaluator.evaluateExpression(expression);
                                System.out.printf("%s = %d%n", expression, result);
                            } catch (Exception e) {
                                System.out.println("Error");
                            }
                        }
                    }
                    fileScanner.close();
                } catch (FileNotFoundException e) {
                    System.out.println("File not found!");
                }
            }
        }
        scanner.close();
    }
}