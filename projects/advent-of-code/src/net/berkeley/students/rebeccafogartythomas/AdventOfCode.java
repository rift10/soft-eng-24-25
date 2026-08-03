package net.berkeley.students.rebeccafogartythomas;

import java.util.List;
import net.berkeley.students.rebeccafogartythomas.day7.DaySeven;

public class AdventOfCode {

    private static final List<Day> days = List.of(
        // new DayOne(),
        // new DayTwo(),
        // new DayThree(),
        // new DayFour(),
        // new DayFive(),
        // new DaySix()
        new DaySeven()
        // new DayEight()
        // new DayNine()
        // new DayTen()
        // new DayEleven()
        // new DayTwelve()
        );

    public static void main(String[] args) { 
        for (Day day : days) {
            System.out.println("day " + day.getDayNumber());
            day.run();
            System.out.println();
        }
    }
}