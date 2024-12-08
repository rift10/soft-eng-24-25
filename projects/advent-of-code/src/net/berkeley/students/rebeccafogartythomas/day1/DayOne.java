package net.berkeley.students.rebeccafogartythomas.day1;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.berkeley.students.rebeccafogartythomas.Day;
import net.berkeley.students.rebeccafogartythomas.Util;

public class DayOne implements Day {

    private List<String> bigList = new ArrayList<>();
    private final List<Integer> listOne = new ArrayList<>();
    private final List<Integer> listTwo = new ArrayList<>();
    private int total = 0;
    private int similarityScore = 0;
    private final Path filePath = Path.of("/workspaces/rift10/projects/advent-of-code/src/net/berkeley/students/rebeccafogartythomas/day1/Input.txt");

    @Override
    public void run() {
        bigList = Util.readFileToList(filePath);

        for (int i = 0; i < bigList.size(); i++) {
            listOne.add(Integer.valueOf(bigList.get(i).substring(0, 5)));
            listTwo.add(Integer.valueOf(bigList.get(i).substring(8, 13)));
            // for test case
            // listOne.add(Integer.valueOf(bigList.get(i).substring(0, 1)));
            // listTwo.add(Integer.valueOf(bigList.get(i).substring(4, 5)));
        }
        
        listOne.sort(Comparator.naturalOrder());
        listTwo.sort(Comparator.naturalOrder());
        
        for (int i = 0; i < listOne.size(); i++) {
            total += Math.abs(listOne.get(i) - listTwo.get(i));
            similarityScore += listOne.get(i) * Util.countInstances(listTwo, listOne.get(i));
        }

        System.out.println("total: " + total); // answer: 1879048
        System.out.println("similarity score: " + similarityScore); // answer: 21024792
    }
}
