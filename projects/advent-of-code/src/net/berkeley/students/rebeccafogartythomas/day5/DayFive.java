package net.berkeley.students.rebeccafogartythomas.day5;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import net.berkeley.students.rebeccafogartythomas.Day;
import net.berkeley.students.rebeccafogartythomas.Util;

public class DayFive implements Day {
    private final Path rulesPath = Path.of("/workspaces/rift10/projects/advent-of-code/src/net/berkeley/students/rebeccafogartythomas/day5/InputRules.txt");
    private final Path updatesPath = Path.of("/workspaces/rift10/projects/advent-of-code/src/net/berkeley/students/rebeccafogartythomas/day5/InputUpdates.txt");
    private final List<String> rulesInput = Util.readFileToList(rulesPath);
    private final List<String> updatesInput = Util.readFileToList(updatesPath);
    private final List<List<Integer>> rules = new ArrayList<>();
    private final List<List<Integer>> updates = Util.parseStringsToNestedIntegerList(updatesInput, ",");
    private final List<List<Integer>> correctUpdates = new ArrayList<>();
    private final List<List<Integer>> fixedUpdates = new ArrayList<>();
    private final boolean partTwo = false;
    private int total = 0;
    
    @Override
    public void run() {

        for (String rule: rulesInput) rules.add(List.of(Integer.valueOf(rule.substring(0, 2)), Integer.valueOf(rule.substring(3, 5))));

        for (List<Integer> update : updates) {
            boolean passesAllRules = checkUpdate(update);
            if (passesAllRules) correctUpdates.add(update);
            else fixedUpdates.add(getFixedUpdate(update));
        }

        if (partTwo) {
            for (List<Integer> update: fixedUpdates) {
                total += update.get(update.size() / 2);
            }
        } else {
            for (List<Integer> update: correctUpdates) {
                total += update.get(update.size() / 2);
            }
        }

        System.out.println(total);
    }

    private boolean checkUpdate(List<Integer> update) {
        boolean result = true;
        for (int page : update) {
            result &= checkPage(update, page);
        }
        return result;
    }

    private boolean checkPage(List<Integer> update, int page) {
        return isSecondPartOfRuleAfterIndex(update, page) && isFirstPartOfRuleBeforeIndex(update, page);
    }

    /**
     * loops through all the rules and checks if the first page in the rule is the current page,
     * and if the index of the second page in the rule is after the index of the current page
     */
    private boolean isSecondPartOfRuleAfterIndex(List<Integer> update, int page) {
        boolean result = true;
        for (List<Integer> rule : rules) {
            if (rule.get(0) == page && update.contains(rule.get(1))) {
                result &= update.indexOf(rule.get(1)) > update.indexOf(page);
            }           
        }
        return result;
    }

    /**
     * loops through all the rules and checks if the seconf page in the rule is the current page,
     * and if the index of the first page in the rule is before the index of the current page
     */
    private boolean isFirstPartOfRuleBeforeIndex(List<Integer> update, int page) {
        boolean result = true;
        for (List<Integer> rule : rules) {
            if (update.contains(rule.get(0)) && rule.get(1) == page) {
                result &= update.indexOf(rule.get(0)) < update.indexOf(page);
            }           
        }
        return result;
    }

    /**
     * gets the index of the page that is in an incorrect position
     * if that page is supposed to be before the current page
     * @apiNote only works if we have already determined
     * that there is a page not where it should be
     */
    private int getFirstPartOfRuleIndex(List<Integer> update, int page) {
        boolean result = true;
        for (List<Integer> rule : rules) {
            if (update.contains(rule.get(0)) && rule.get(1) == page) {
                result &= update.indexOf(rule.get(0)) < update.indexOf(page);
                if (!result) {
                    return update.indexOf(rule.get(0));
                }
            }           
        }
        return -1;
    }

    /**
     * gets the index of the page that is in an incorrect position
     * if that page is supposed to be after the current page
     * @apiNote only works if we have already determined
     * that there is a page not where it should be
     */
    private int getSecondPartOfRuleIndex(List<Integer> update, int page) {
        boolean result = true;
        for (List<Integer> rule : rules) {
            if (rule.get(0) == page && update.contains(rule.get(1))) {
                result &= update.indexOf(rule.get(1)) > update.indexOf(page);
                if (!result) {
                    return update.indexOf(rule.get(1));
                }
            }           
        }
        return -1;
    }

    private List<Integer> getFixedUpdate(List<Integer> originalUpdate) {
        List<Integer> list = originalUpdate;
        while (!checkUpdate(list)) {
            for (int i = 0; i < originalUpdate.size() - 1; i++) {
                if (!isSecondPartOfRuleAfterIndex(list, list.get(i))) {
                    list = swap(list, i, getSecondPartOfRuleIndex(list, list.get(i)));
                } else if (!isFirstPartOfRuleBeforeIndex(list, list.get(i))) {
                    list = swap(list, i, getFirstPartOfRuleIndex(list, list.get(i)));
                }
            }
        }
        return list;
    }

    private List<Integer> swap(List<Integer> input, int firstIndex, int secondIndex) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            if (i == firstIndex) {
                result.add(input.get(secondIndex));
            } else if (i == secondIndex) {
                result.add(input.get(firstIndex));
            } else {
                result.add(input.get(i));
            }
        }
        return result;
    }

    @Override
    public int getDayNumber() {
        return 5;
    }
}
