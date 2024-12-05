package net.berkeley.students.rebeccafogartythomas;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Util {

    public static int countInstances(List<Integer> list, int number) {
        return (int) list.stream().filter(x -> x == number).count();
    }

    public static List<String> readFileToList(Path filePath) {
        try {
            return Files.readAllLines(filePath);
        } catch (IOException ex) {
            System.err.println(ex);
        }
        return new ArrayList<>();
    }

    public static String readFileToString(Path filePath) {
        try {
            return Files.readString(filePath);
        } catch (IOException ex) {
            System.err.println(ex);
        }
        return new String();
    }
}
