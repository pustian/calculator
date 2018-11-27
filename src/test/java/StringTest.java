import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/11/26.
 */
public class StringTest {
    public static void main(String[] args) {
        String input= "a b c a b";
        List<String> list = new ArrayList<String>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("a");
        list.add("b");
        int position = 0;
        for(String string :list) {
            int index = input.indexOf(string);
            position += index;
            System.out.println("string "+ string+" index:"+index +" position"+ position );
            input = input.substring(index);
            System.out.println("input"+input);
        }
    }
}
