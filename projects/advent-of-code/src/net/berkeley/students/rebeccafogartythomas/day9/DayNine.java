package net.berkeley.students.rebeccafogartythomas.day9;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.berkeley.students.rebeccafogartythomas.Day;
import net.berkeley.students.rebeccafogartythomas.Util;

public class DayNine implements Day {
    private final Path path = Path.of("/workspaces/rift10/projects/advent-of-code/src/net/berkeley/students/rebeccafogartythomas/day9/Input.txt");
    private final List<Long> list = Util.intsToLongs(Util.parseStringToIntList(Util.readSingleLineToString(path)));
    private final List<DiskSpace> spaces = new ArrayList<>();
    private final List<Long> diskSpace = new ArrayList<>();
    private List<Long> finalList = new ArrayList<>();
    private long totalSpaces = 0;
    private long checkSum = 0;

    @Override
    public void run() {
        boolean isFile = true;
        int currentId = 0;
        for (int i = 0; i < list.size(); i++) {
            spaces.add(new DiskSpace(i, list.get(i), isFile ? currentId : -1, isFile));
            if (isFile) currentId++;
            isFile = !isFile; 
        }

        for (DiskSpace space : spaces) {
            if (space.isFile()) {
                for (int i = 0; i < space.numberOfSpaces(); i++) {
                    diskSpace.add(space.id());
                }
                totalSpaces += space.numberOfSpaces();
            } else {
                for (int i = 0; i < space.numberOfSpaces(); i++) {
                    diskSpace.add(Long.valueOf(-1));
                }
            }
        }

        finalList = diskSpace;

        for (int i = diskSpace.size() - 1; i > 0; i--) {
            if (!Objects.equals(diskSpace.get(i), Long.valueOf(-1))) {
                if (finalList.contains(Long.valueOf(-1))) {
                    finalList.set(finalList.indexOf(Long.valueOf(-1)), diskSpace.get(i));
                } else break;
            }
        }

        String result = new String();

        for (int i = 0; i < totalSpaces; i++) {
            checkSum += (finalList.get(i) * i);
            // System.out.println("multiplying " + finalList.get(i) + " with " + i + ", result: " + (finalList.get(i) * i) + ", adding to sum: " + checkSum);
            result = result.concat(finalList.get(i).toString());
        }

        // System.out.println(result);
        System.out.println(checkSum);
    }

    @Override
    public int getDayNumber() {
        return 9;
    }
}
