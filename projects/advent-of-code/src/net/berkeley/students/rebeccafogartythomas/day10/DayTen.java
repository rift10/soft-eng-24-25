package net.berkeley.students.rebeccafogartythomas.day10;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import net.berkeley.students.rebeccafogartythomas.Day;
import net.berkeley.students.rebeccafogartythomas.Util;

public class DayTen implements Day {
    private final Path path = Path.of("/workspaces/rift10/projects/advent-of-code/src/net/berkeley/students/rebeccafogartythomas/day10/SmallTest.txt");
    private final List<List<Integer>> list = Util.readFileToIntList(path, "");
    private int total;

    @Override
    public void run() {
        System.out.println(list);

        // for (int i = 0; i < list.size(); i++) {
        //     for (int j = 0; j < list.get(i).size(); j++) {
        //         if (list.get(i).get(j) == 0) {
        //             total += findTrailheads(list.get(i), j);
        //         }
        //     }
        // }

        // TODO you lowkey misunderstood this ahah

        System.out.println(findTrailheads(list.get(2), 2));

        System.out.println(total);
    }

    private List<List<Integer>> findNextNumber(int nextNum, int i, int j) {
        List<List<Integer>> result = new ArrayList<>();
        if (i > 0)                      if (checkNorth(nextNum, i, j)) result.add(List.of(i - 1, j));
        if (i < list.size() - 1)        if (checkSouth(nextNum, i, j)) result.add(List.of(i + 1, j));
        if (j > 0)                      if (checkWest(nextNum, i, j)) result.add(List.of(i, j - 1));
        if (j < list.get(i).size() - 1) if (checkEast(nextNum, i, j)) result.add(List.of(i, j + 1));
        return result;
    }

    private int findTrailheads(List<Integer> line, int spotIndex) {
        int result = 0;
        switch (line.get(spotIndex)) {
            case 9 -> {
                return 0;
            } case 8 -> {
                result += findNextNumber(9, list.indexOf(line), spotIndex).size();
            } default -> {
                if (list.indexOf(line) > 0)                result += findTrailheads(list.get(list.indexOf(line) - 1), spotIndex);
                if (list.indexOf(line) < list.size() - 1)  result += findTrailheads(list.get(list.indexOf(line) + 1), spotIndex);
                if (spotIndex > 0)                         result += findTrailheads(list.get(list.indexOf(line)), spotIndex - 1);
                if (spotIndex < line.size() - 1)           result += findTrailheads(list.get(list.indexOf(line)), spotIndex + 1);
            }
        }
        return result;
    }

    private boolean checkNorth(int num, int i, int j) {
        return list.get(i - 1).get(j) == num;
    }

    private boolean checkSouth(int num, int i, int j) {
        return list.get(i + 1).get(j) == num;
    }

    private boolean checkWest(int num, int i, int j) {
        return list.get(i).get(j - 1) == num;
    }

    private boolean checkEast(int num, int i, int j) {
        return list.get(i).get(j + 1) == num;
    }

    @Override
    public int getDayNumber() {
        return 10;
    }
} 
