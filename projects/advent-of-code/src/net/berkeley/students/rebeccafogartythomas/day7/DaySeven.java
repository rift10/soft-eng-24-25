package net.berkeley.students.rebeccafogartythomas.day7;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import net.berkeley.students.rebeccafogartythomas.Day;
import net.berkeley.students.rebeccafogartythomas.Util;

public class DaySeven implements Day {
    private static final Path path = Path.of("/workspaces/rift10/projects/advent-of-code/src/net/berkeley/students/rebeccafogartythomas/day7/Test.txt");
    private static final List<String> list = Util.readFileToList(path);
    private static final List<String> fixedList = new ArrayList<>();
    private List<List<Integer>> equations = new ArrayList<>();
    private final List<Integer> successfulEquations = new ArrayList<>();
    
    @Override
    public void run() {
        // for (String line : list) {
        //     fixedList.add(line.replace(':', ' '));
        // }

        // equations = Util.parseStringsToNestedIntegerList(fixedList, "\\s+");

        // for (List<Integer> equation : equations) {
        //     int result = equation.get(0);
        //     System.out.println("desired outcome: " + result + ", equationize results: " +  equationize(equation.subList(1, equation.size())));
        //     if (equationize(equation.subList(1, equation.size())).contains(result)) successfulEquations.add(result);
        // }
        
        // System.out.println(successfulEquations);

        // System.out.println(successfulEquations.stream().collect(Collectors.summingInt(x -> x)));

        // System.out.println(getResults(List.of(10, 19), 1));
        System.out.println(getOperationConfigs(4, 2));
    }

    private List<Integer> equationize(List<Integer> input) {
        int numSpaces = input.size() - 1;
        System.out.println("equationize permutations: " + Util.getPermutations(numSpaces, input.size() - 2) + ", input size: " + input.size());
        List<Integer> resultList = new ArrayList<>();
        for (int totalMultiplies = 0; totalMultiplies < numSpaces; totalMultiplies++) {
            if (totalMultiplies > 0) {
                // foo(input, totalMultiplies);
            } else {
                int result = 0;
                for (int i = 0; i < numSpaces; i++) {
                    result += input.get(i);
                }
                resultList.add(result);
            }
        }
        return resultList;
    }

    private List<List<Operation>> getOperationConfigs(int numSpaces, int numMults) {
        List<List<Operation>> resultList = new ArrayList<>();
        if (numMults == 1) {
            int multIndex;
            for (int i = 0; i < numSpaces; i++) {
                List<Operation> result = new ArrayList<>();
                multIndex = i;
                for (int j = 0; j < numSpaces; j++) {
                    if (j == multIndex) result.add(Operation.MULTIPLY);
                    else result.add(Operation.ADD);
                }
                resultList.add(result);
            }
        } else {
            resultList.addAll(getOperationConfigs(numSpaces - 1, numMults - 1));
            resultList.addAll(getOperationConfigs(numSpaces - 1, numMults - 1));
            
            int index = 0;

            for (int i = 0; i < Util.getPermutations(numSpaces - 1, numMults); i++) {
            // for (int i = 0; i < numSpaces - 1; i++) {
                // System.out.println("permutations: " + Util.getPermutations(numSpaces - 1, numMults) + ", with spaces: " + (numSpaces - 1) + ", with mults: " + numMults);
                resultList.get(i).add(index, Operation.MULTIPLY);
                if ((numMults % (i + 1)) == 0) index++;
            }
        }
        return resultList;
    }


    // IF I END UP USING, FIX THIS BC TOTALMULTIPLIES PARAMETER DOESNT AFFECT FUNCTION
    // private List<Integer> getResults(List<Integer> input, int totalMultiplies) {
    //     List<Integer> resultList = new ArrayList<>();
    //     if (input.size() == 2 && totalMultiplies == 1) {
    //         resultList.add(input.get(0) + input.get(1));
    //         resultList.add(input.get(0) * input.get(1));
    //     } else if (input.size() - 1 == totalMultiplies) {
    //         int total = 1;
    //         for (Integer num : input) {
    //             total *= num;
    //         }
    //         resultList.add(total);
    //     } else if (totalMultiplies == 0) {
    //         int total = 0;
    //         for (Integer num : input) {
    //             total += num;
    //         }
    //         resultList.add(total);
    //     } else {

    //     }
    //     return resultList;
    // }

    // private int add(List<Integer> summands) {
    //     int result = 1;
    //     for (int i = 0; i < summands.size(); i++) {
    //         result += summands.get(i);
    //     }
    //     return result;
    // }

    // private int multiply(List<Integer> multiplicands) {
    //     int result = 1;
    //     for (int i = 0; i < multiplicands.size(); i++) {
    //         result *= multiplicands.get(i);
    //     }
    //     return result;
    // }

    @Override
    public int getDayNumber() {
        return 7;
    }
}
