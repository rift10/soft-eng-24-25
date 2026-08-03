package net.berkeley.students.rebeccafogartythomas.day4;

import java.nio.file.Path;
import java.util.List;
import net.berkeley.students.rebeccafogartythomas.Day;
import net.berkeley.students.rebeccafogartythomas.Util;

public class DayFour implements Day {

    private final Path filePath = Path.of("/workspaces/rift10/projects/advent-of-code/src/net/berkeley/students/rebeccafogartythomas/day4/Input.txt");
    private final List<String> list = Util.readFileToList(filePath);
    private int xmasCount = 0;
    private final int xmasLength = 2;
    private final boolean isPartTwo = false;

    @Override
    public void run() {
        if (!isPartTwo) findXmases();
        else findMasesInX();
        System.out.println(xmasCount);
    }

    private boolean findWest(int i, int j) {
        String string = list.get(i);
        if (j <= xmasLength) return false;
        return (string.charAt(j) == 'X' && string.charAt(j - 1) == 'M' && string.charAt(j - 2) == 'A' && string.charAt(j - 3) == 'S');
    }

    private boolean findEast(int i, int j) {
        String string = list.get(i);
        if (j >= string.length() - xmasLength) return false;
        return string.charAt(j) == 'X' && string.charAt(j + 1) == 'M' && string.charAt(j + 2) == 'A' && string.charAt(j + 3) == 'S';
    }

    private boolean findNorth(int i, int j) {
        if (i <= xmasLength) return false;
        return list.get(i).charAt(j) == 'X' && list.get(i - 1).charAt(j) == 'M' && list.get(i - 2).charAt(j) == 'A' && list.get(i - 3).charAt(j) == 'S';
    }

    private boolean findSouth(int i, int j) {
        if (i >= list.size() - (xmasLength + 1)) return false;
        return list.get(i).charAt(j) == 'X' && list.get(i + 1).charAt(j) == 'M' && list.get(i + 2).charAt(j) == 'A' && list.get(i + 3).charAt(j) == 'S';
    }

    private boolean findNorthWest(int i, int j) {
        if (i <= xmasLength || j <= xmasLength) return false;
        return list.get(i).charAt(j) == 'X' && list.get(i - 1).charAt(j - 1) == 'M' && list.get(i - 2).charAt(j - 2) == 'A' && list.get(i - 3).charAt(j - 3) == 'S';
    }

    private boolean findNorthEast(int i, int j) {
        if (i <= xmasLength || j >= list.get(i).length() - (xmasLength + 1)) return false;
        return list.get(i).charAt(j) == 'X' && list.get(i - 1).charAt(j + 1) == 'M' && list.get(i - 2).charAt(j + 2) == 'A' && list.get(i - 3).charAt(j + 3) == 'S';
    }

    private boolean findSouthWest(int i, int j) {
        if (i >= list.size() - (xmasLength + 1) || j <= xmasLength) return false;
        return list.get(i).charAt(j) == 'X' && list.get(i + 1).charAt(j - 1) == 'M' && list.get(i + 2).charAt(j - 2) == 'A' && list.get(i + 3).charAt(j - 3) == 'S';
    }

    private boolean findSouthEast(int i, int j) {
        if (i >= list.size() - (xmasLength + 1) || j >= list.get(i).length() - (xmasLength + 1)) return false;
        return list.get(i).charAt(j) == 'X' && list.get(i + 1).charAt(j + 1) == 'M' && list.get(i + 2).charAt(j + 2) == 'A' && list.get(i + 3).charAt(j + 3) == 'S';
    }
    
    private boolean findMasMas(int i, int j) {
        return list.get(i).charAt(j) == 'A' && findMas_(i, j) && find_Mas(i, j);
    }

    private boolean findMasSam(int i, int j) {
        return list.get(i).charAt(j) == 'A' && findMas_(i, j) && find_Sam(i, j);
    }

    private boolean findSamMas(int i, int j) {
        return list.get(i).charAt(j) == 'A' && findSam_(i, j) && find_Mas(i, j);
    }

    private boolean findSamSam(int i, int j) {
        return list.get(i).charAt(j) == 'A' && findSam_(i, j) && find_Sam(i, j);
    }

    private boolean findMas_(int i, int j) {
        return list.get(i - 1).charAt(j - 1) == 'M' && list.get(i + 1).charAt(j + 1) == 'S';
    }

    private boolean find_Mas(int i, int j) {
        return (list.get(i + 1).charAt(j - 1) == 'M') && (list.get(i - 1).charAt(j + 1) == 'S');
    }

    private boolean findSam_(int i, int j) {
        return list.get(i - 1).charAt(j - 1) == 'S' && list.get(i + 1).charAt(j + 1) == 'M';
    }

    private boolean find_Sam(int i, int j) {
        return (list.get(i + 1).charAt(j - 1) == 'S') && (list.get(i - 1).charAt(j + 1) == 'M');
    }

    private void findXmases() {
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).length(); j++) {
                if (findWest(i, j)) xmasCount++;
                if (findEast(i, j)) xmasCount++;
                if (findNorth(i, j)) xmasCount++;
                if (findSouth(i, j)) xmasCount++;
                if (findNorthWest(i, j)) xmasCount++;
                if (findNorthEast(i, j)) xmasCount++;
                if (findSouthWest(i, j)) xmasCount++;
                if (findSouthEast(i, j)) xmasCount++;
            }
        }
    }

    private void findMasesInX() {
        for (int i = 1; i < list.size() - 1; i++) {
            for (int j = 1; j < list.get(i).length() - 1; j++) {
                if (findMasMas(i, j) || findMasSam(i, j) || findSamMas(i, j) || findSamSam(i, j)) xmasCount++;
            }
        }
    }

    @Override
    public int getDayNumber() {
        return 4;
    }
}