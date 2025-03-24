package projects.recursion;

import java.util.List;

public class Functions {

    public static long naiveFact(long num) {
        if (num == 1) return 1;
        return num * naiveFact(num-1);
    }

    public static long naiveFib(long num) {
        if (num == 0 || num == 1) return num;
        return naiveFib(num-1) + naiveFib(num-2);
    }

    public static long factLoop(long num) {
        long result = 1;
        long mult = num;
        for (long i = 0; i < num; i++) {
            result *= mult;
            mult--;
        }
        return result;
    }

    public static long fibLoop(long num) {
        long one = 0;
        long two = 1;
        for (long i = 0; i < num; i++) {
            long tmp = one;
            one = two;
            two += tmp;
        }
        return one;
    }

    public static long naiveChange(long money, List<Integer> coins) {
        System.out.println("calling change with amount: " + money);
        if (money == 0) return 1;
        if (coins.isEmpty() || money < 0) return 0;    
        return naiveChange(money - coins.get(0), coins) + naiveChange(money, dropFirst(coins));
        // return change(smaller amount, coins) + change (amount, smaller coins)
    }

    public static <T> List<T> dropFirst(List<T> list) {
        return list.subList(1, list.size());
    }

    public static void main(String[] args) {
        System.out.println(naiveChange(100, List.of(1, 5, 10, 25, 50)));
    }
}
