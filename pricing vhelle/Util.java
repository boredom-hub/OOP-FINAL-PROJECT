import java.text.DecimalFormat;

public class Util {

    // 1234.5 -> ₱1,234.50
    public static String peso(double amount) {
        return new DecimalFormat("\u20B1#,##0.00").format(amount);
    }

    // 150.0 -> "150", 2.5 -> "2.5"
    public static String num(double value) {
        if (value == Math.floor(value) && Math.abs(value) < 1e15) {
            return String.valueOf((long) value);
        }
        return new DecimalFormat("0.####").format(value);
    }
}
