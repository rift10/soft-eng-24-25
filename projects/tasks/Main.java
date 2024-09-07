
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Main {

    public static ArrayList<CustomRunnable> makeList(CustomRunnable... runs) {
        ArrayList<CustomRunnable> result = new ArrayList<>();
        result.addAll(Arrays.asList(runs));
        Collections.sort(result);
        return result;
    }


    public static void main(String[] args) {
        System.out.println("hello world");
        for (CustomRunnable r : (makeList(new RunnableTwo(), new RunnableThree(), new RunnableOne(), new RunnableThree()))) {
            r.run();
        }
    }
}