package net.berkeley.students.rebeccafogartythomas.day11;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import net.berkeley.students.rebeccafogartythomas.Day;
import net.berkeley.students.rebeccafogartythomas.Util;

public class DayEleven implements Day {
    private final Path path = Path.of("/workspaces/rift10/projects/advent-of-code/src/net/berkeley/students/rebeccafogartythomas/day11/Input.txt");
    private List<Long> list = Util.intsToLongs(Util.parseLineToIntegerList(Util.readSingleLineToString(path), "\\s"));
    
    @Override
    public void run() {
        // System.out.println(list);

        for (int i = 0; i < 25; i++) { 
            list = blink(list);
            // System.out.println(list);
        }

        System.out.println(list.size());
    }

    public List<Long> blink(List<Long> prevList) {
        List<Long> result = new ArrayList<>();
        for (Long stone : prevList) {
            if (stone == 0) {
                result.add(Long.valueOf(1));
            } else if (stone.toString().length() % 2 == 0) {
                // System.out.println(stone.toString().substring(0, stone.toString().length() / 2));
                // System.out.println(stone.toString().substring(stone.toString().length() / 2, stone.toString().length()));
                result.add(Long.valueOf(stone.toString().substring(0, stone.toString().length() / 2)));
                result.add(Long.valueOf(stone.toString().substring(stone.toString().length() / 2, stone.toString().length())));
            } else {
                result.add(stone * 2024);
            }
        }
        return result;
    }

    @Override
    public int getDayNumber() {
        return 11;
    }
}
