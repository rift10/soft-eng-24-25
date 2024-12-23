package net.berkeley.students.rebeccafogartythomas.day9;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.berkeley.students.rebeccafogartythomas.Day;
import net.berkeley.students.rebeccafogartythomas.Util;

public class DayNine implements Day {
    private final Path path = Path.of("/workspaces/rift10/projects/advent-of-code/src/net/berkeley/students/rebeccafogartythomas/day9/Test.txt");
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
        String diskSpaceString = new String();
        for (int i = 0; i < diskSpace.size(); i++) {
            diskSpaceString = diskSpaceString.concat(diskSpace.get(i) == -1 ? "." : diskSpace.get(i).toString());
        }

        // for (int i = diskSpace.size() - 1; i > 0; i--) {
        //     if (!Objects.equals(diskSpace.get(i), Long.valueOf(-1))) {
        //         if (finalList.contains(Long.valueOf(-1))) {
        //             finalList.set(finalList.indexOf(Long.valueOf(-1)), diskSpace.get(i));
        //         } else break;
        //     }
        // }

        long ogFileIndex = finalList.size() - 1;
        
        while (ogFileIndex > 0) {
        outer: for (long i = 9; i > 0; i--) {
            int fileSize = 0;
            
            while (finalList.get((int) ogFileIndex) != i) { 
                ogFileIndex--;
                if (ogFileIndex == 0) break outer;
            }

            while (finalList.get((int) ogFileIndex) == i) { 
                ogFileIndex--;
                fileSize++;
            }

            // System.out.println("file id: " + i + ", file size: " + fileSize);
        
            int startingIndex;

            if (getStartIndex(finalList, fileSize) == -1 || getStartIndex(finalList, fileSize) >= ogFileIndex) {
                // System.out.println("continuing");
                continue;
            } else {
                startingIndex = getStartIndex(finalList, fileSize);
            }

            for (int k = 0; k < fileSize; k++) {
                finalList.set(finalList.indexOf(i), Long.valueOf(-1));
            }

            for (int k = 0; k < fileSize; k++) {
                finalList.set(startingIndex + k, i);
                // System.out.println("updated list: " + finalList);
            }
            // System.out.println("og file index: " + ogFileIndex);
        }
    }

        // for (int i = 0; i < totalSpaces; i++) {
        //     checkSum += (finalList.get(i) * i);
        //     // System.out.println("multiplying " + finalList.get(i) + " with " + i + ", result: " + (finalList.get(i) * i) + ", adding to sum: " + checkSum);
        // }

        for (int i = 0; i < finalList.size(); i++) {
            if (Objects.equals(finalList.get(i), Long.valueOf(-1))) continue;
            checkSum += (finalList.get(i) * i);
        }
        
        String result = new String();

        for (int i = 0; i < finalList.size(); i++) {
            result = result.concat(finalList.get(i) == -1 ? "." : finalList.get(i).toString());
        }

        // 16028342059489 is too high
        // System.out.println("og disk space:    " + diskSpaceString);
        // System.out.println("fixed disk space: " + result);
        System.out.println(checkSum);
    }

    private int findNextEmpty(List<Long> list, int currentIndex) {
        return list.subList(currentIndex + 1, list.size() - 1).indexOf(Long.valueOf(-1)) + currentIndex + 1;
    }

    private boolean isSectionEmpty(List<Long> list, int fileLength, int startIndex) {
        boolean result = true;
        for (int i = 0; i < fileLength; i++) {
            if (startIndex + i > list.size() - 1) return false; 
            result &= Objects.equals(list.get(startIndex + i), Long.valueOf(-1));
        }
        return result;
    }

    private int getStartIndex(List<Long> list, int fileLength) {
        int startIndex = list.indexOf(Long.valueOf(-1));
        while (!isSectionEmpty(list, fileLength, startIndex)) { 
            System.out.println("find next empty: " + findNextEmpty(list, startIndex));
            if (findNextEmpty(list, startIndex) == -1) return -1;
            if (findNextEmpty(list, startIndex) == startIndex) return -1;
            startIndex = findNextEmpty(list, startIndex);
        }

        return startIndex;
    }

    @Override
    public int getDayNumber() {
        return 9;
    }
}
