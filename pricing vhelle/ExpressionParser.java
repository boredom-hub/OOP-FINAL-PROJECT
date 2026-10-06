// lets the user type things like 1/2 or (1+2)*3 in the quantity box
public class ExpressionParser {

    private String text;
    private int pos;

    // returns NaN if the text isn't a valid expression
    public static double evaluate(String input) {
        if (input == null) {
            return Double.NaN;
        }
        ExpressionParser p = new ExpressionParser();
        p.text = input.replace(" ", "");
        p.pos = 0;
        if (p.text.isEmpty()) {
            return Double.NaN;
        }
        try {
            double result = p.expression();
            if (p.pos != p.text.length() || Double.isInfinite(result)) {
                return Double.NaN;
            }
            return result;
        } catch (RuntimeException e) {
            return Double.NaN;
        }
    }

    // handles + and -
    private double expression() {
        double value = term();
        while (pos < text.length() && (text.charAt(pos) == '+' || text.charAt(pos) == '-')) {
            char op = text.charAt(pos++);
            double next = term();
            if (op == '+') {
                value += next;
            } else {
                value -= next;
            }
        }
        return value;
    }

    // handles * and /
    private double term() {
        double value = factor();
        while (pos < text.length() && (text.charAt(pos) == '*' || text.charAt(pos) == '/')) {
            char op = text.charAt(pos++);
            double next = factor();
            if (op == '*') {
                value *= next;
            } else {
                value /= next;
            }
        }
        return value;
    }

    // numbers, brackets and minus/plus signs
    private double factor() {
        if (pos >= text.length()) {
            throw new IllegalArgumentException("ran out of text");
        }
        char c = text.charAt(pos);

        if (c == '-') {
            pos++;
            return -factor();
        }
        if (c == '+') {
            pos++;
            return factor();
        }
        if (c == '(') {
            pos++;
            double value = expression();
            if (pos >= text.length() || text.charAt(pos) != ')') {
                throw new IllegalArgumentException("missing )");
            }
            pos++;
            return value;
        }

        int start = pos;
        while (pos < text.length() && (Character.isDigit(text.charAt(pos)) || text.charAt(pos) == '.')) {
            pos++;
        }
        if (start == pos) {
            throw new IllegalArgumentException("expected a number");
        }
        return Double.parseDouble(text.substring(start, pos));
    }
}
