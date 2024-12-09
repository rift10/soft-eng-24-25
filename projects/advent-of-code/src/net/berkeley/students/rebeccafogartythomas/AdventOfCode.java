package net.berkeley.students.rebeccafogartythomas;

import java.util.List;
import net.berkeley.students.rebeccafogartythomas.day6.DaySix;

public class AdventOfCode {

    private static final List<Day> days = List.of(
        // new DayOne(),
        // new DayTwo(),
        // new DayThree(),
        // new DayFour(),
        // new DayFive(),
        new DaySix()
        // new DaySeven(),
        // new DayEight()
        );

    public static void main(String[] args) { 
        for (Day day : days) day.run();
    }
}