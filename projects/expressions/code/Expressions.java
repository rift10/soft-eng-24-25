package projects.expressions.code;

import java.util.HashMap;
import java.util.Map;

public class Expressions {

    public interface Expression {
        public double evaluate(Map<String, Double> env);
    }

    public record Number(double value) implements Expression {
        @Override
        public double evaluate(Map<String, Double> env) {
            return value;
        }
    }

    public record Variable(String name) implements Expression {
        @Override
        public double evaluate(Map<String, Double> env) {
            return env.get(name);
        }

        public String getName() {
            return name;
        }
    }

    public record Addition(Expression... x) implements Expression {

        @Override
        public double evaluate(Map<String, Double> env) {
            double result = 0;
            for (Expression i : x) result += i.evaluate(env);
            return result;
        }
    }

    public record Subtraction(Expression x, Expression y) implements Expression {
        @Override
        public double evaluate(Map<String, Double> env) {
            return x.evaluate(env) - y.evaluate(env);
        }
    }

    public record Multiplication(Expression... x) implements Expression {
        @Override
        public double evaluate(Map<String, Double> env) {
            double result = 1;
            for (Expression i : x) result *= i.evaluate(env);
            return result;
        }
    }

    public record Division(Expression x, Expression y) implements Expression {
        @Override
        public double evaluate(Map<String, Double> env) {
            return x.evaluate(env) / y.evaluate(env);
        }
    }

    public record Modulo(Expression x, Expression y) implements Expression {
        @Override
        public double evaluate(Map<String, Double> env) {
            return x.evaluate(env) % y.evaluate(env);
        }
    }

    public static final Number one = new Number(1);
    public static final Number two = new Number(2);
    public static final Number three = new Number(3);
    public static final Number four = new Number(4);
    public static final Number five = new Number(5);
    public static final Number six = new Number(6);
    public static final Number seven = new Number(7);
    public static final Number eight = new Number(8);
    public static final Number nine = new Number(9);
    public static final Number ten = new Number(10);

    public static final Variable x = new Variable("x");
    public static final Variable y = new Variable("y");
    public static final Variable z = new Variable("z");

    public static final Addition addition = new Addition(one, two);
    public static final Addition additionWithVar = new Addition(x, one, two);
    public static final Subtraction subtraction = new Subtraction(three, four);
    public static final Subtraction subtractionWithVar = new Subtraction(y, four);
    public static final Multiplication multiplication = new Multiplication(five, six);
    public static final Multiplication multiplicationWithVar = new Multiplication(five, z);
    public static final Division division = new Division(seven, eight);
    public static final Modulo modulo = new Modulo(nine, ten);

    public static final HashMap<String, Double> map = new HashMap<>();

    public static void initializeMap() {
        map.put(x.getName(), Double.valueOf(20));
        map.put(y.getName(), Double.valueOf(30));
        map.put(z.getName(), Double.valueOf(40));
    }

    public static void main(String[] args) {
        initializeMap();
        System.out.println("addition: " + addition.evaluate(map));
        System.out.println("subtraction: " + subtraction.evaluate(map));
        System.out.println("multiplication: " + multiplication.evaluate(map));
        System.out.println("division: " + division.evaluate(map));
        System.out.println("modulo: " + modulo.evaluate(map));
        System.out.println();
        System.out.println("addition with variable: " + additionWithVar.evaluate(map));
        System.out.println("subtraction with variable: " + subtractionWithVar.evaluate(map));
        System.out.println("multiplication with variable: " + multiplicationWithVar.evaluate(map));
    }

}
