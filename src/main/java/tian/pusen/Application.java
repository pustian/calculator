package tian.pusen;

import java.util.Scanner;

/**
 * Created by Administrator on 2018/11/26.
 */
public class Application {
    public static void main(String[] args) {
        System.out.println("Calculator: Input numbers and operators separated by whitespace.");
        System.out.println("    Support operator: +, -, *, /, sqrt, undo, clear.");

        String readLine = null;
        Calculator calculator = new Calculator();
        do {
            Scanner scanner = new Scanner(System.in);

            readLine = scanner.nextLine();
            try{
                calculator.execute(readLine);
            }catch (CalculatorException e) {
                System.err.println(e.getMessage());
            }
            System.out.println(calculator.getOperands() );
//            System.out.println(calculator.getPopedOperands() );
//            System.out.println(calculator.getHistory());
//            System.out.println(calculator.getLineOperation());
        } while ( true);

    }
}
