package net.berkeley.students.rebeccafogartythomas.day11;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.berkeley.students.rebeccafogartythomas.Day;
import net.berkeley.students.rebeccafogartythomas.Util;

public class DayEleven implements Day {
    private final Path path = Path.of("/workspaces/rift10/projects/advent-of-code/src/net/berkeley/students/rebeccafogartythomas/day11/Test.txt");
    // private final Path path = Path.of("/workspaces/rift10/projects/advent-of-code/src/net/berkeley/students/rebeccafogartythomas/day11/Output.txt");
    private List<Long> list = Util.intsToLongs(Util.parseLineToIntegerList(Util.readSingleLineToString(path), "\\s"));
    // private List<Long> list = Util.readFileToList(path).stream().map(x -> Long.valueOf(x)).toList();
    private Map<Long, List<Long>> memo = new HashMap<>();
    
    @Override
    public void run() {
        for (int i = 0; i < 75; i++) { 
            list = refreshStones(list);
        }
        Util.printListToFile(list);
        System.out.println(list.size());
    }

    private List<Long> refreshStones(List<Long> initial) {
        List<Long> result = new ArrayList<>();
        for (long i = 0; i < initial.size(); i++) {
            result.addAll(blink(initial.get((int) i)));
        }
        return result;
    }

    private List<Long> blink(long stone) {
        if (memo.containsKey(stone)) return memo.get(stone);
        List<Long> result = new ArrayList<>();
        if (stone == 0) {
            result.add(Long.valueOf(1));
        } else if (Long.toString(stone).length() % 2 == 0) {
            result.add(Long.valueOf(Long.toString(stone).substring(0, Long.toString(stone).length() / 2)));
            result.add(Long.valueOf(Long.toString(stone).substring(Long.toString(stone).length() / 2, Long.toString(stone).length())));
        } else {
            result.add(stone * 2024);
        }
        memo.put(stone, result);
        return result;
    }

    // private List<Long> blinkSingle(long stone) {
    //     List<Long> result = new ArrayList<>();
    //     if (stone == 0) {
    //         result.add(Long.valueOf(1));
    //     } else if (Long.toString(stone).length() % 2 == 0) {
    //         result.add(Long.valueOf(Long.toString(stone).substring(0, Long.toString(stone).length() / 2)));
    //         result.add(Long.valueOf(Long.toString(stone).substring(Long.toString(stone).length() / 2, Long.toString(stone).length())));
    //     } else {
    //         result.add(stone * 2024);
    //     }
    //     return result;
    // }

    @Override
    public int getDayNumber() {
        return 11;
    }
}
