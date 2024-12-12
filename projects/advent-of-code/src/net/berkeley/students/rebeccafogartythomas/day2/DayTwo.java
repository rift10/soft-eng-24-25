package net.berkeley.students.rebeccafogartythomas.day2;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import net.berkeley.students.rebeccafogartythomas.Day;
import net.berkeley.students.rebeccafogartythomas.Util;

public class DayTwo implements Day {
    private final Path filePath = Path.of("/workspaces/rift10/projects/advent-of-code/src/net/berkeley/students/rebeccafogartythomas/day2/Input.txt");
    private final List<String> file = Util.readFileToList(filePath);
    private final List<List<String>> list = new ArrayList<>();
    private final List<List<String>> unsafe = new ArrayList<>();
    private int safeReportCount;
    private int problemDampenedSafeReportCount;    

    @Override
    public void run() {
        for (int i = 0; i < file.size(); i++) {
            list.add(Arrays.asList(file.get(i).split("\\s+")));
        }

        for (List<String> level : list) {
            if (isSafe(level)) safeReportCount++;
            else unsafe.add(level);
        }

        problemDampenedSafeReportCount = safeReportCount;

        for (List<String> level : unsafe) {
            if (isSafeWithProblemDampener(level)) problemDampenedSafeReportCount++;
        }

        System.out.println(safeReportCount); // answer: 631
        System.out.println(problemDampenedSafeReportCount); // answer: 665

    }

    private boolean isSafe(List<String> input) {
        var report = new ArrayList<Integer>();
        var increasing = false;
        var decreasing = false;
        for (String level : input) report.add(Integer.valueOf(level)); 
        for (int i = 1; i < report.size(); i++) {
            if (Objects.equals(report.get(i), report.get(i - 1))) return false;
            if (Math.abs(report.get(i) - report.get(i - 1)) > 3 || Math.abs(report.get(i) - report.get(i - 1)) < 1) return false;
            if (report.get(i) < report.get(i - 1) && increasing == true) return false;
            if (report.get(i) > report.get(i - 1) && decreasing == true) return false;
            if (report.get(i) > report.get(i - 1)) increasing = true;
            if (report.get(i) < report.get(i - 1)) decreasing = true;
        }
        return true;
    }

    private boolean isSafeWithProblemDampener(List<String> input) {
        for (int i = 0; i < input.size(); i++) {
            var modifiedReport = new ArrayList<String>();
            for (int j = 0; j < i; j++) modifiedReport.add(input.get(j));
            for (int j = i + 1; j < input.size(); j++) modifiedReport.add(input.get(j));
            if (isSafe(modifiedReport)) return true;
        }
        return false;
    }

    @Override
    public int getDayNumber() {
        return 2;
    }
}