package projects.recursion;

import java.util.Arrays;
import java.util.List;

public class CommonSubsequence {

    public static String naiveSubsequence(String one, String two) {
        if (one.isEmpty() || two.isEmpty()) return new String();
        if (one.equals(two)) return one;
        // String oneEdited = one;
        // String twoEdited = two;
        String oneResult = new String();
        String twoResult = new String();

        System.out.println("one: " + one);
        for (String s : toStringList(one)) {
            if (two.contains(s)) {
                oneResult = oneResult.concat(s);
                // twoEdited = twoEdited.replace(s, "");
            }
        }
        System.out.println("two: " + two);
        for (String s : toStringList(two)) {
            if (one.contains(s)) {
                twoResult = twoResult.concat(s);
                // oneEdited = oneEdited.replace(s, "");
            }
        }
        // System.out.println("edited:   one: " + oneEdited + "   two: " + twoEdited);
        System.out.println("output:   one: " + oneResult + "   two: " + twoResult);
        // return naiveSubsequence(oneResult, twoResult);
        return new String();
    }
    
    public static void main(String[] args) {
        System.out.println(naiveSubsequence("foobar", "greedy fear"));
    }

    public static String findSubstring(String one, String two) {
        int maxLength = (Math.min(one.length(), two.length()));
        // loop over possible lengths
        for (int i = 2; i < maxLength; i++) {
            // loop over possible starting indices
            for (int j = 0; j < maxLength; j++) {
                if (two.contains(one.substring(j, j + i))) return one.substring(j, j + i);
            }
        }
        return new String();
    }

    public static List<String> toStringList(String string) {
        return Arrays.asList(string.split("(?<!^)"));
    }

}
