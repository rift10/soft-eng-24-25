package projects.recursion;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Functions {

    private static final Map<Integer, Long> factMap = new HashMap<>();
    private static final Map<Integer, Long> fibMap = new HashMap<>();
    private static final Map<Change, Long> changeMap = new HashMap<>();

    public static long naiveFact(int num) {
        if (num == 1) return 1;
        if (factMap.containsKey(num)) return factMap.get(num);
        factMap.put(num, num * naiveFact(num-1));
        return num * naiveFact(num-1);
    }

    public static long factLoop(int num) {
        long result = 1;
        long mult = num;
        for (long i = 0; i < num; i++) {
            result *= mult;
            mult--;
        }
        return result;
    }

    public static long factorial(int n) {
        return memoFact(n, new long[n + 1]);
    }
      
    private static long memoFact(int n, long[] memo) {
        if (n == 1) return n;
        if (memo[n] == 0) memo[n] = n * memoFact(n - 1, memo);
        return memo[n];
    }

    public static long naiveFib(int num) {
        if (num < 2) return num;
        if (fibMap.containsKey(num)) return fibMap.get(num);
        fibMap.put(num, naiveFib(num-1) + naiveFib(num-2));
        return naiveFib(num-1) + naiveFib(num-2);
    }

    public static long fibLoop(int num) {
        long one = 0;
        long two = 1;
        for (long i = 0; i < num; i++) {
            long tmp = one;
            one = two;
            two += tmp;
        }
        return one;
    }

    public static long fibonacci(int n) {
        return memoFib(n, new long[n + 1]);
    }
      
    private static long memoFib(int n, long[] memo) {
        if (n < 2) return n;
        if (memo[n] == 0) memo[n] = memoFib(n - 1, memo) + memoFib(n - 2, memo);
        return memo[n];
    }

    public static long dynamicFib(int n) {
        var table = new long[n + 1];
        table[1] = 1;
        for (int i = 2; i < table.length; i++) {
            table[i] = table[i - 1] + table[i - 2];
        }
        return table[n];
      }

    public static long naiveChange(int money, List<Integer> coins) {
        System.out.println("calling memo change with amount: " + money + " and coins: " + coins);
        if (money == 0) return 1;
        if (coins.isEmpty() || money < 0) return 0;
        Change input = new Change(money, coins);
        if (changeMap.containsKey(input)) return changeMap.get(input);
        changeMap.put(input, naiveChange(money - coins.get(0), coins) + naiveChange(money, dropFirst(coins)));    
        return naiveChange(money - coins.get(0), coins) + naiveChange(money, dropFirst(coins));
    }

    public static long change(int money, List<Integer> coins) {
        // array: first dimension is the coins in use, second dimension is the available money
        return memoChange(money, coins, new long[coins.size() + 1][money + 1]);
    }

    public static long memoChange(int money, List<Integer> coins, long[][] memo) {
        if (money == 0) return 1;
        if (coins.isEmpty() || money < 0) return 0;
        if (coins.get(0) > money) return 0;
        if (memo[coins.size()][money] == 0) {
            memo[coins.size()][money] = memoChange(money - coins.get(0), coins, memo) + memoChange(money, dropFirst(coins), memo);
        }
        var result = memo[coins.size()][money];
        System.out.println("calling memo change with amount: " + money + " and coins: " + coins + " with result: " + result);
        if (money == 100 && coins.equals(List.of(1, 5, 10, 25, 50))) printTableToFile(convertToArrayList(memo), "projects/recursion/table.txt");
        return result;
    }

    // public static long dynamicChange(int money, List<Integer> coins) {
    //     var table = new long[coins.size() + 1][money + 1];
    //     for (int i = 1-1; i < table[0].length; i++) {
    //         table[i][0] = 1;
    //     }
    //     for (int i = 1; i < table.length; i++) {
    //         table[0][i] = 0;
    //     }
    //     for (int i = 1; i < table.length; i++) {
    //         for (int j = 1; j < table[0].length; j++) {
    //             table[i][j] = table[i-1][j] + table[i][j-1];
    //         }
    //     }
    //     printTableToFile(convertToArrayList(table), "projects/recursion/dynamic.txt");
    //     return table[coins.size()][money - 1];
    //     // return naiveChange(money - coins.get(0), coins) + naiveChange(money, dropFirst(coins));
    // }

    public static long dynamicChange(int money, List<Integer> coins) {
        var table = new long[coins.size() + 1][money];
        for (int i = 1-1; i < table.length; i++) {
            table[i][0] = 0;
        }
        for (int i = 1; i < table[0].length; i++) {
            table[0][i] = 0;
        }
        for (int i = 1; i < table.length; i++) {
            for (int j = 1; j < table[0].length; j++) {
                if (i == coins.get(j - 1)) {
                    table[i][j] = 1;
                    continue;
                }
                if (j - coins.get(j-1) <= 0) {
                    table[i][j] = 0;
                    continue;
                }
                table[i][j] = table[i-1][j] + table[i][j-coins.get(j-1)];
            }
        }
        for (int i = 1-1; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                System.out.print(table[i][j] + " ");
            }
            System.out.println(); // Move to the next line after printing each row
        }
        printTableToFile(convertToArrayList(table), "projects/recursion/dynamic.txt");
        return table[coins.size()][money - 1];
        // return naiveChange(money - coins.get(0), coins) + naiveChange(money, dropFirst(coins));
    }

    public static void main(String[] args) {
        System.out.println(dynamicChange(5, List.of(1, 5, 10, 25, 50)));
        // System.out.println(change(100, List.of(1, 5, 10, 25, 50)));
        // System.out.println(naiveChange(1000, List.of(1, 5, 10, 25, 50)));
    }

    public static void printTable(long[][] table) {
        for (int i = 1-1; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                System.out.print(table[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void printTableToFile(List<List<Integer>> tableData, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (List<Integer> row : tableData) {
                String formattedRow = String.join("\t", row.toString());
                writer.write(formattedRow);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public static List<List<Integer>> convertToArrayList(long[][] array) {
        List<List<Integer>> result = new ArrayList<>();
        for (long[] row : array) {
            List<Integer> intRow = new ArrayList<>();
            for (long value : row) {
                intRow.add((int) value); // Cast long to int
            }
            result.add(intRow);
        }
        return result;
    }

    public static <T> List<T> dropFirst(List<T> list) {
        return list.subList(1, list.size());
    }

    public record Change(long money, List<Integer> coins) {}
}
