package net.berkeley.students.rebeccafogartythomas.day16;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import net.berkeley.students.rebeccafogartythomas.Day;
import net.berkeley.students.rebeccafogartythomas.Util;

public class DaySixteen implements Day {
    private final Path path = Path.of("/workspaces/rift10/projects/advent-of-code/src/net/berkeley/students/rebeccafogartythomas/day16/Test.txt");
    // private final Path path = Path.of("/workspaces/rift10/projects/advent-of-code/src/net/berkeley/students/rebeccafogartythomas/day16/Input.txt");
    private final List<String> list = Util.readFileToList(path);
    private final List<List<Integer>> obstacles = new ArrayList<>();
    private final List<List<Integer>> spotsVisited = new ArrayList<>();
    private final int[] currentPos = new int[2];
    private final int[] initPos = new int[2];
    private final int[] endPos = new int[2];
    private Direction direction = Direction.NORTH;
    private int score = 0;
    private int turns = 0;

    @Override
    public void run() {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).contains("S")) {
                currentPos[0] = i;
                currentPos[1] = list.get(i).indexOf("S");
            }
        }

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).contains("E")) {
                endPos[0] = i;
                endPos[1] = list.get(i).indexOf("E");
            }
        }
        
        initPos[0] = currentPos[0];
        initPos[1] = currentPos[1];

        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(0).length(); j++) {
                if (list.get(i).charAt(j) == '#') {
                    obstacles.add(List.of(i, j));
                }
            }
        }

        resetPos();

        while (currentPos[0] != endPos[0] && currentPos[1] != endPos[1]) { 
            move();
        }

        // 1690 is too low
        System.out.println(score);
    }

    private void move() {
    
        switch (direction) {
            case EAST -> {
                moveEastLogic();
            }
            case NORTH -> {
                moveNorthLogic();
            }
            case SOUTH -> {
                moveSouthLogic();
            }
            case WEST -> {
                moveWestLogic();
            }
        }

        if (turns == 3)

        turns = 0;
    }

    private void resetPos() {
        currentPos[0] = initPos[0];
        currentPos[1] = initPos[1];
        direction = Direction.NORTH;
    }

    private void moveEastLogic() {
        if (obstacles.contains(List.of(currentPos[0], currentPos[1] + 1))) {
            direction = Direction.SOUTH;
            moveSouthLogic();
            // System.out.println("turning to direction: " + direction);
        }
        else moveEast(); 
    }

    private void moveNorthLogic() {
        if (obstacles.contains(List.of(currentPos[0] - 1, currentPos[1]))) {
            direction = Direction.EAST;
            moveEastLogic();
            // System.out.println("turning to direction: " + direction);
        }
        else moveNorth();
    }

    private void moveSouthLogic() {
        if (obstacles.contains(List.of(currentPos[0] + 1, currentPos[1]))) {
            direction = Direction.WEST;
            moveWestLogic();
            // System.out.println("turning to direction: " + direction);
        }
        else moveSouth();
    }

    private void moveWestLogic() {
        if (obstacles.contains(List.of(currentPos[0], currentPos[1] - 1))) {
            direction = Direction.NORTH;
            moveNorth();
            // System.out.println("turning to direction: " + direction);
        }
        else moveWest();
    }

    private void moveEast() {
        currentPos[1] += 1;
    }

    private void moveNorth() {
        currentPos[0] += -1;
    }

    private void moveSouth() {
        currentPos[0] += 1;
    }

    private void moveWest() {
        currentPos[1] += -1;
    }

    @Override
    public int getDayNumber() {
        return 16;
    }
}
