package tian.pusen;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Administrator on 2018/11/26.
 */
public class Calculator {
    private Stack<BigDecimal> operands = new Stack<BigDecimal>();
    // To support undo.
    // history: store all input token except "undo".. "clear" will clear the list
    private List<String> history = new ArrayList<String>();
    // popedOperands: store operands pop elements while do add/sub/mul/div/sqrt
    private Stack<BigDecimal> popedOperands = new Stack<BigDecimal>();
//    // lineOperations: store the current inputLine token.
//    lineOperations = null;

    public void execute(String inputLine) {
        // parse input
        List<String>  lineOperations = parseInputLine(inputLine);
        // calculate
        for (String token : lineOperations) {
            history.add(token);
            calculate(token);
        }
    }

    public List<String> parseInputLine(String inputLine) {
        List<String> lineOperations = new ArrayList<String>();

        int position = 0;  // the position of the token at inputLine
        int operandsSize = operands.size();
        int operandNeed = 0;
        StringTokenizer stringTokenizer = new StringTokenizer(inputLine);
        // this execute operations contains operands and operator
        while (stringTokenizer.hasMoreElements()) {
            String token = stringTokenizer.nextToken();

            int index = inputLine.indexOf(token);
            position += index;
            inputLine = inputLine.substring(index);

            // ignore the input token which is neither operand nor operator
            if (ArithmeticUtils.isNumeric(token)) {
                lineOperations.add(token);
                ++operandsSize;
            } else {
                Operator operator = Operator.getOperator(token);
                if (null == operator) {
                    String message = "Input " + token + " (position: " + position + "): not support";
                    throw new CalculatorException(message);
                } else {
                    operandNeed += 1;
                    switch (operator) {
                        case PLUS:
                        case MINUS:
                        case MILTP:
                        case DIVIDE:
                            if (operandNeed + 1 > operandsSize) {
                                String message = "operator " + token + " (position: " + position + "): insucient parameters";
                                throw new CalculatorException(message);
                            }
                            break;
                        case SQRT:
                            if (operandNeed > operandsSize) {
                                String message = "operator " + token + " (position: " + position + "): insucient parameters";
                                throw new CalculatorException(message);
                            }
                            break;
                        case CLEAR:
                            operandNeed = 0;
                            break;
                        case UNDO:
                            break;
                    }
                    lineOperations.add(token);
                }
            }
        }
        return lineOperations;
    }

    public void calculate(String token) {
        if (ArithmeticUtils.isNumeric(token)) {
            operands.push(ArithmeticUtils.convert2BigDecimal(token));
        } else {
            Operator operator = Operator.getOperator(token);
            switch (operator) {
                case PLUS:
                    add();
                    break;
                case MINUS:
                    sub();
                    break;
                case MILTP:
                    mul();
                    break;
                case DIVIDE:
                    div();
                    break;
                case SQRT:
                    sqrt();
                    break;
                case CLEAR:
                    clear();
                    break;
                case UNDO:
                    undo();
                    break;
            }
        }
    }

    private void add() {
        BigDecimal operand2 = operands.pop();
        BigDecimal operand1 = operands.pop();
        BigDecimal add_result = ArithmeticUtils.add(operand1.toPlainString(), operand2.toPlainString());
        operands.push(add_result);
        // support undo
        popedOperands.push(operand2);
        popedOperands.push(operand1);
    }

    private void sub() {
        BigDecimal operand2 = operands.pop();
        BigDecimal operand1 = operands.pop();
        BigDecimal sub_result = ArithmeticUtils.sub(operand1.toPlainString(), operand2.toPlainString());
        operands.push(sub_result);
        // support undo
        popedOperands.push(operand2);
        popedOperands.push(operand1);
    }

    private void mul() {
        BigDecimal operand2 = operands.pop();
        BigDecimal operand1 = operands.pop();
        BigDecimal mul_result = ArithmeticUtils.mul(operand1.toPlainString(), operand2.toPlainString());
        operands.push(mul_result);
        // support undo
        popedOperands.push(operand2);
        popedOperands.push(operand1);
    }

    private void div() {
        BigDecimal operand2 = operands.pop();
        BigDecimal operand1 = operands.pop();
        BigDecimal div_result = ArithmeticUtils.div(operand1.toPlainString(), operand2.toPlainString());
        operands.push(div_result);
        // support undo
        popedOperands.push(operand2);
        popedOperands.push(operand1);
    }

    private void sqrt() {
        BigDecimal operand = operands.pop();
        Double result = Math.sqrt(Double.parseDouble(operand.toPlainString()));
        BigDecimal sqrt_result = ArithmeticUtils.convert2BigDecimal(result.toString());
        operands.push(sqrt_result);
        // support undo
        popedOperands.push(operand);
    }

    private void clear() {
        operands.clear();
        // support undo
        popedOperands.clear();
    }

    /**
     * 1, find "undo" and the previous operation.from the history list
     * 2, 2.1 if the previous operation is operand, the operands pop
     *    2.2 if the previous operation is operator
     *      add/sub/mul/div  the operands pop--the result, push the popedOperands pop elements
     *      clear -- not support
     *      undo ---nothing do
     * 3, delete the "undo" and the previous operation.from the history list
     */
    private void undo() {
        //  The history list has already deleted the "undo" token exception the new line input.
        int undoPosition = history.indexOf(Operator.UNDO.getSyntax());
        // the operation before "undo"
        String last_operation = history.get(undoPosition - 1);
        // System.out.println("last_operation:"+ last_operation);
        if (ArithmeticUtils.isNumeric(last_operation)) {
            operands.pop();
        } else {
            Operator operator = Operator.getOperator(last_operation);
            switch (operator) {
                case PLUS:
                case MINUS:
                case MILTP:
                case DIVIDE:
                    BigDecimal operands2 = popedOperands.pop();
                    BigDecimal operands1 = popedOperands.pop();
                    operands.pop();
                    operands.push(operands2);
                    operands.push(operands1);
                    break;
                case SQRT:
                    BigDecimal sqrt = popedOperands.pop();
                    operands.pop();
                    operands.push(sqrt);
                    break;
                case CLEAR:
                    throw new CalculatorException("It is not supported to undo, after clear");
            }
        }
        history.remove(undoPosition );
        history.remove(undoPosition-1 );
    }

    public String getOperands() {
        StringBuffer stringBuffer = new StringBuffer("stack:");
        Iterator<BigDecimal> it = operands.iterator();
        while (it.hasNext()) {
            stringBuffer.append(ArithmeticUtils.format4Display(it.next()).toPlainString()).append(" ");
        }
        return stringBuffer.toString();
    }
//    public String getPopedOperands() {
//        StringBuffer stringBuffer = new StringBuffer("poped stack:[");
//        Iterator<BigDecimal> it = popedOperands.iterator();
//        while (it.hasNext()) {
//            stringBuffer.append(ArithmeticUtils.format4Display(it.next())).append(" ");
//        }
//        stringBuffer.append(']');
//        return stringBuffer.toString();
//    }
//    public String getHistory() {
//        StringBuffer stringBuffer = new StringBuffer("history :[");
//        Iterator<String> it = history.iterator();
//        while (it.hasNext()) {
//            stringBuffer.append(it.next()).append(" ");
//        }
//        stringBuffer.append(']');
//        return stringBuffer.toString();
//    }
//    public String getLineOperation() {
//        StringBuffer stringBuffer = new StringBuffer("lineOperation :[");
//        Iterator<String> it = lineOperations.iterator();
//        while (it.hasNext()) {
//            stringBuffer.append(it.next()).append(" ");
//        }
//        stringBuffer.append(']');
//        return stringBuffer.toString();
//    }
}