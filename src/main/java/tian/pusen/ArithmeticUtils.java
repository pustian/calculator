package tian.pusen;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/11/24.
 */
public final class ArithmeticUtils {
    public static boolean isNumeric(String string) {
        Pattern pattern = Pattern.compile("-?[0-9]+\\.?[0-9]*");
        Matcher isNum = pattern.matcher(string);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public static BigDecimal add(String v1, String v2) {
        int scale = 15;
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return new BigDecimal(b1.add(b2).toString()).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal sub(String v1, String v2) {
        int scale = 15;
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.subtract(b2).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal mul(String v1, String v2) {
        int scale = 15;
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.multiply(b2).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal div(String v1, String v2) {
        int scale = 15;
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.divide(b2).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal convert2BigDecimal(String string) {
        return new BigDecimal(string).setScale(15, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal format4Display(BigDecimal bigDecimal) {
        return new BigDecimal(bigDecimal.toPlainString()).setScale(10, BigDecimal.ROUND_HALF_UP);
    }

    public static void main(String[] args) {
        BigDecimal bigDecimal0 = new BigDecimal("1.12345678901234567").setScale(15, BigDecimal.ROUND_HALF_UP);
        System.out.println(bigDecimal0.toString());
        BigDecimal bigDecimal1 = new BigDecimal("1.12345").setScale(15, BigDecimal.ROUND_HALF_UP);
        System.out.println(bigDecimal1.toString());
        BigDecimal bigDecimal2 = new BigDecimal("1.12345").setScale(15, BigDecimal.ROUND_HALF_UP);
        System.out.println(new BigDecimal(bigDecimal2.toString()).setScale(10, BigDecimal.ROUND_HALF_UP).toString());
        System.out.println(new BigDecimal(add(bigDecimal1.toPlainString(), bigDecimal2.toPlainString()).toPlainString()));
    }
}
