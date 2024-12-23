package net.berkeley.students.rebeccafogartythomas.day8;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.berkeley.students.rebeccafogartythomas.Day;
import net.berkeley.students.rebeccafogartythomas.Util;

public class DayEight implements Day {
    private final Path path = Path.of("/workspaces/rift10/projects/advent-of-code/src/net/berkeley/students/rebeccafogartythomas/day8/Test.txt");
    private final List<String> list = Util.readFileToList(path);
    private final Pattern antennaePattern = Pattern.compile("[^\\.\\t\\n\\r]");
    private final Matcher antennaeMatcher = antennaePattern.matcher(Util.readFileToString(path));
    private List<String> antennae = new ArrayList<>();
    private int antinodes = 0;

    @Override
    public void run() {
        while (antennaeMatcher.find()) { 
            antennae.add(antennaeMatcher.group());
        }
        antennae = antennae.stream().distinct().toList();
        System.out.println(antennae);

        for (String signal : antennae) {
            System.out.println(signal.charAt(0) + ", " + findAntennae(signal.charAt(0)));
            for (List<Integer> antenna : findAntennae(signal.charAt(0))) {
                // if (antenna.get(0) >= 0 && antenna.get(0) <= list.size() && antenna.get(1) >= 0 && antenna.get(0) <= list.size()) {

                // }
            }
        }

        System.out.println(antinodes);
    }

    private List<List<Integer>> findAntennae(char signal) {
        List<List<Integer>> result = new ArrayList<>();
        for (String string : list) {
            for (int i = 0; i < string.length(); i++) {
                if (string.charAt(i) == signal) {
                    result.add(List.of(list.indexOf(string), i));
                }
            }
        }
        return result;
    }

    // private int getMatchingA(List<List<Integer>> antennae) {
    //     for (List<Integer> antenna : antennae) {
            
    //     }
    // }

    // private int getAntennaeDist(List<Integer> antennaOne, List<Integer> antennaTwo) {
    //     int result = 0;
    //     for () {

    //     }
    //     return result;
    // }

    @Override
    public int getDayNumber() {
        return 8;
    }
}
