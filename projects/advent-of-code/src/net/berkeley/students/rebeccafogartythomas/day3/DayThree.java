package net.berkeley.students.rebeccafogartythomas.day3;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.berkeley.students.rebeccafogartythomas.Day;
import net.berkeley.students.rebeccafogartythomas.Util;

public class DayThree implements Day {
    private final Path filePath = Path.of("/workspaces/rift10/projects/advent-of-code/src/net/berkeley/students/rebeccafogartythomas/day3/Input.txt");
    private final String input = Util.readFileToString(filePath);
    
    private final Pattern mulPattern = Pattern.compile("mul[(]\\d+,\\d+[)]");
    private final Matcher mulMatcher = mulPattern.matcher(input);

    private final Pattern doPattern = Pattern.compile("do[(][)]");
    private final Matcher doMatcher = doPattern.matcher(input);

    private final Pattern dontPattern = Pattern.compile("don't[(][)]");
    private final Matcher dontMatcher = dontPattern.matcher(input);

    private final List<Function> list = new ArrayList<>();
    private final List<List<String>> equations = new ArrayList<>();

    private int sum = 0;
    private boolean enabled = true;

    @Override
    public void run() {

        while (mulMatcher.find()) {
            list.add(new Function(mulMatcher.start(), mulMatcher.group()));
        }

        while (doMatcher.find()) {
            list.add(new Function(doMatcher.start(), doMatcher.group()));
        }

        while (dontMatcher.find()) {
            list.add(new Function(dontMatcher.start(), dontMatcher.group()));
        }

        Collections.sort(list, Comparator.comparing(Function::place));

        for (Function func : list) {
            if (func.function().substring(0, 1).equals("m")) { // if the first letter is m it must be a mul()
                String string = func.function().substring(4, func.function().length() - 1);
                List<String> multiplicands = Arrays.asList(string.split(","));
                if (enabled) equations.add(multiplicands);
            } else enabled = func.function().length() <= 5; // if the length is less than 5 the function must be do()
        }

        for (List<String> equation : equations) {
            sum += Integer.valueOf(equation.get(0)) * Integer.valueOf(equation.get(1));
        }

        System.out.println(sum);
    }

    @Override
    public int getDayNumber() {
        return 3;
    }
}
