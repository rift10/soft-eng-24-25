package net.berkeley.students.rebeccafogartythomas.day6;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import net.berkeley.students.rebeccafogartythomas.Day;
import net.berkeley.students.rebeccafogartythomas.Util;

public class DaySix implements Day {
    // private final Path path = Path.of("/workspaces/rift10/projects/advent-of-code/src/net/berkeley/students/rebeccafogartythomas/day6/Test.txt");
    private final Path path = Path.of("/workspaces/rift10/projects/advent-of-code/src/net/berkeley/students/rebeccafogartythomas/day6/Input.txt");
    private final List<String> list = Util.readFileToList(path);
    private final List<List<Integer>> obstacles = new ArrayList<>();
    private final List<List<Integer>> spotsVisited = new ArrayList<>();
    private final List<List<Integer>> originalSpotsVisited = new ArrayList<>();
    private final int[] currentPos = new int[2];
    private final int[] initPos = new int[2];
    private Direction direction = Direction.NORTH;
    private int repetition = 0;
    private int possiblePositions = 0;
    private boolean shouldContinue = false;

    @Override
    public void run() {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).contains("^")) {
                currentPos[0] = i;
                currentPos[1] = list.get(i).indexOf("^");
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

        while (isInBounds(currentPos)) {
            if (!originalSpotsVisited.contains(List.of(currentPos[0], currentPos[1])))
                originalSpotsVisited.add(List.of(currentPos[0], currentPos[1]));
    
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
        }

        resetPos();

        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).length(); j++) {
                if (!originalSpotsVisited.contains(List.of(i, j)) || obstacles.contains(List.of(i, j))) continue;
                obstacles.add(List.of(i, j));
                System.out.println("current obstacle: " + List.of(i, j));
                while (isInBounds(currentPos)) { 
                    move();
                    if (shouldContinue) {
                        possiblePositions++;
                        break;
                    }
                }
                shouldContinue = false;
                repetition = 0;
                obstacles.remove(obstacles.size() - 1);
                spotsVisited.clear();
                resetPos();
            }
        }

        // 1690 is too low
        System.out.println(possiblePositions);
    }

    private void move() {
        if (spotsVisited.contains(List.of(currentPos[0], currentPos[1]))) {
            repetition++;
            if (repetition > spotsVisited.size()) {
                shouldContinue = true;
                possiblePositions++;
                return;
            }
            // System.out.println("already visited " + currentPos[0] + ", " + currentPos[1] + ", with obs " + obstacles.get(obstacles.size() - 1));
            // System.out.println(repetition);
        } else if (!spotsVisited.contains(List.of(currentPos[0], currentPos[1]))) {
            spotsVisited.add(List.of(currentPos[0], currentPos[1]));
            // System.out.println("current pos: " + currentPos[0] + ", " + currentPos[1]);
            repetition = 0;
        }
    
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

    private boolean isInBounds(int[] pos) {
        return pos[0] >= 0 && pos[0] < list.size()
                && pos[1] >= 0 && pos[1] < list.get(0).length();
    }

    @Override
    public int getDayNumber() {
        return 6;
    }
}
