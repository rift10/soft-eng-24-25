package net.berkeley.students.rebeccafogartythomas.day5;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import net.berkeley.students.rebeccafogartythomas.Day;
import net.berkeley.students.rebeccafogartythomas.Util;

public class DayFive implements Day {
    private final Path rulesPath = Path.of("/workspaces/rift10/projects/advent-of-code/src/net/berkeley/students/rebeccafogartythomas/day5/InputRules.txt");
    private final Path updatesPath = Path.of("/workspaces/rift10/projects/advent-of-code/src/net/berkeley/students/rebeccafogartythomas/day5/InputUpdates.txt");
    private List<String> rulesInput = new ArrayList<>();
    private List<String> updatesInput = new ArrayList<>();
    private final List<List<Integer>> rules = new ArrayList<>();
    private final List<List<Integer>> updates = new ArrayList<>();
    private final List<List<Integer>> correctUpdates = new ArrayList<>();
    private int total = 0;
    
    @Override
    public void run() {
        rulesInput = Util.readFileToList(rulesPath);
        updatesInput = Util.readFileToList(updatesPath);

        for (String rule: rulesInput) {
            rules.add(List.of(Integer.valueOf(rule.substring(0, 2)), Integer.valueOf(rule.substring(3, 5))));
        }

        for (String update: updatesInput) {
            List<Integer> updatePages = (List.of(update.split(","))).stream().map(Integer::valueOf).collect(Collectors.toList());
            updates.add(updatePages);
        }

        for (List<Integer> update : updates) {
            boolean passesAllRules = true;
            for (int page : update) {
                for (List<Integer> rule : rules) {
                    if (rule.contains(page) && update.contains(rule.get(0)) && update.contains(rule.get(1))) {
                        passesAllRules &= checkRule(update, page, rule);
                        // System.out.println("is currently passing all rules: " + passesAllRules);
                        // if (checkRule(update, page, rule)) System.out.println("rule is true");
                        // else System.out.println("rule is false");
                    }
                }
            }
            if (passesAllRules) correctUpdates.add(update);
        }

        for (List<Integer> update: correctUpdates) {
            total += update.get(update.size() / 2);
        }

        System.out.println(total);
    }

    private boolean checkRule(List<Integer> update, int page, List<Integer> rule) {
        // System.out.println(update + " checking rule: " + rule.get(0) + ", " + rule.get(1) + " with page: " + page);
        // if (rule.get(0) == page) 
        //     System.out.println("page is before (>): " + update.indexOf(rule.get(1)) + ", " + update.indexOf(page) + ", " + (update.indexOf(rule.get(1)) > update.indexOf(page)));

        // if (rule.get(1) == page) 
        //     System.out.println("page is after (<): " + update.indexOf(rule.get(0)) + ", " + update.indexOf(page));
        return (rule.get(0) == page && update.indexOf(rule.get(1)) > update.indexOf(page)) || (rule.get(1) == page && update.indexOf(rule.get(0)) < update.indexOf(page));
    }
}
