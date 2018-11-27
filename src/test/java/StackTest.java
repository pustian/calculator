import java.util.Iterator;
import java.util.Stack;

/**
 * Created by Administrator on 2018/11/26.
 */
public class StackTest {
    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<Integer>();
        stack.push(1);
        stack.push(2);
        System.out.println(getStack(stack));
        stack.pop();
        System.out.println(getStack(stack));

        stack.push(3);
        stack.push(4);
        stack.clear();
        System.out.println(getStack(stack));

    }
    public static String getStack(Stack<Integer> stack) {
        StringBuffer stringBuffer = new StringBuffer("stack:");
        Iterator<Integer> it = stack.iterator();
        while (it.hasNext()) {
            stringBuffer.append(it.next()).append(" ");
        }
        stringBuffer.append('\n');
        return stringBuffer.toString();
    }

}
