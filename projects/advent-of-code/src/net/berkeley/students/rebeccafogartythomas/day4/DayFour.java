package net.berkeley.students.rebeccafogartythomas.day4;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import net.berkeley.students.rebeccafogartythomas.Day;
import net.berkeley.students.rebeccafogartythomas.Util;

public class DayFour implements Day {

    private final Path filePath = Path.of("/workspaces/rift10/projects/advent-of-code/src/net/berkeley/students/rebeccafogartythomas/day4/Input.txt");
    private List<String> list = new ArrayList<>();
    private int xmasCount = 0;
    private final int xmasLength = 2;

    @Override
    public void run() {
        list = Util.readFileToList(filePath);
        findXMases();

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
        // System.out.println("testing: " + i + ", " + j);
        if (i >= list.size() - (xmasLength + 1) || j >= list.get(i).length() - (xmasLength + 1)) return false;
        // System.out.println(list.get(i).charAt(j));
        // System.out.println(list.get(i + 1).charAt(j + 1));
        // System.out.println(list.get(i + 2).charAt(j + 2));
        // System.out.println(list.get(i + 3).charAt(j + 3));
        return list.get(i).charAt(j) == 'X' && list.get(i + 1).charAt(j + 1) == 'M' && list.get(i + 2).charAt(j + 2) == 'A' && list.get(i + 3).charAt(j + 3) == 'S';
    }
    
    private void findXmases() {
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).length(); j++) {
                if (findWest(i, j)) {
                    // System.out.println("west x at: " + i + ", " + j); 
                    xmasCount++;
                }
                if (findEast(i, j)) {
                    // System.out.println("east x at: " + i + ", " + j); 
                    xmasCount++;
                }
                if (findNorth(i, j)) {
                    // System.out.println("north x at: " + i + ", " + j); 
                    xmasCount++;
                }
                if (findSouth(i, j)) {
                    // System.out.println("south x at: " + i + ", " + j); 
                    xmasCount++;
                }
                if (findNorthWest(i, j)) {
                    // System.out.println("northWest x at: " + i + ", " + j); 
                    xmasCount++;
                }
                if (findNorthEast(i, j)) {
                    // System.out.println("northEast x at: " + i + ", " + j); 
                    xmasCount++;
                }
                if (findSouthWest(i, j)) {
                    // System.out.println("southWest x at: " + i + ", " + j); 
                    xmasCount++;
                }
                if (findSouthEast(i, j)) {
                    // System.out.println("southEast x at: " + i + ", " + j); 
                    xmasCount++;
                }
            }
        }
    }

    private void findXMases() {
        // 1191 is too low
        // 2163 is too high
        for (int i = 1; i < list.size() - 1; i++) {
            for (int j = 1; j < list.get(i).length() - 1; j++) {
                if (findMasMas(i, j)) {
                    System.out.println("mas mas a at: " + i + ", " + j);
                    xmasCount++;
                }
                if (findMasSam(i, j)) {
                    System.out.println("mas sam a at: " + i + ", " + j);
                    xmasCount++;
                }
                if (findSamMas(i, j)) {
                    System.out.println("sam mas a at: " + i + ", " + j);
                    xmasCount++;
                }
                if (findSamSam(i, j)) {
                    System.out.println("sam sam a at: " + i + ", " + j);
                    xmasCount++;
                }
            }
        }
    }

    private boolean findMasMas(int i, int j) {
        return list.get(i).charAt(j) == 'A' && list.get(i - 1).charAt(j - 1) == 'M' && list.get(i + 1).charAt(j + 1) == 'S'
                && ((list.get(i + 1).charAt(j - 1) == 'M') && (list.get(i - 1).charAt(j + 1) == 'S'));
    }

    private boolean findMasSam(int i, int j) {
        return list.get(i).charAt(j) == 'A' && list.get(i - 1).charAt(j - 1) == 'M' && list.get(i + 1).charAt(j - 1) == 'S'
                && ((list.get(i + 1).charAt(j - 1) == 'S') && (list.get(i - 1).charAt(j + 1) == 'M'));
    }

    private boolean findSamMas(int i, int j) {
        return list.get(i).charAt(j) == 'A' && list.get(i - 1).charAt(j - 1) == 'S' && list.get(i + 1).charAt(j - 1) == 'M'
                && ((list.get(i + 1).charAt(j - 1) == 'M') && (list.get(i - 1).charAt(j + 1) == 'S'));
    }

    private boolean findSamSam(int i, int j) {
        return list.get(i).charAt(j) == 'A' && list.get(i - 1).charAt(j - 1) == 'S' && list.get(i + 1).charAt(j + 1) == 'M'
                && ((list.get(i + 1).charAt(j - 1) == 'S') && (list.get(i + 1).charAt(j + 1) == 'M'));
    }
}