package net.berkeley.students.rebeccafogartythomas;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Util {

    public static List<String> readFileToList(Path filePath) {
        try {
            return Files.readAllLines(filePath);
        } catch (IOException ex) {
            System.err.println(ex);
        }
        return new ArrayList<>();
    }

    public static List<List<Integer>> readFileToIntList(Path filePath, String splitter) {
        return parseStringsToNestedIntegerList(readFileToList(filePath), splitter);
    }

    public static String readFileToString(Path filePath) {
        try {
            return Files.readString(filePath);
        } catch (IOException e) {
            System.err.println(e);
        }
        return new String();
    }

    public static String readSingleLineToString(Path path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path.toString()))) {
            return br.readLine();
        } catch (IOException e) {
            System.err.println(e);
        }
        return new String();
    }

    public static List<List<Integer>> parseStringsToNestedIntegerList(List<String> strings, String splitter) {
        List<List<Integer>> result = new ArrayList<>();
        for (String string: strings) {
            List<Integer> updatePages = parseLineToIntegerList(string, splitter);
            result.add(updatePages);
        }
        return result;
    }

    public static List<Integer> parseLineToIntegerList(String string, String splitter) {
        return List.of(string.split(splitter)).stream().map(Integer::valueOf).collect(Collectors.toList());
    }

    public static List<Integer> parseStringToIntList(String string) {
        List<Integer> result = new ArrayList<>();
        for (String ch : string.split("")) {
            result.add(Integer.valueOf(ch));
        }
        return result;
    }

    public static List<Long> intsToLongs(List<Integer> list) {
        return list.stream().map(Integer::longValue).collect(Collectors.toList());
    }

    public static <E> void printListToFile(List<E> list) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("Output.txt"))) {
            for (Object x : list) {
                writer.println(x);
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public static int countInstances(List<Integer> list, int number) {
        return (int) list.stream().filter(x -> x == number).count();
    }

    /**
     * @return The number of ways of obtaining an ordered subset of r elements from a set of n elements
     */
    public static int getPermutations(int n, int r) {
        return factorial(n)/factorial(n - r);
    }

    public static int factorial(int x) {
        if (x == 1) return 1;
        return x * factorial(x - 1);
    }
}
