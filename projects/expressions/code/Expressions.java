package projects.expressions.code;

public class Expressions {

    public interface Expression {
        public double evaluate();
        default double evaluate(Variable variable) {
            return 0;
        }
    }

    public record Number(double value) implements Expression {
        @Override
        public double evaluate() {
            return value;
        }
    }

    public record Variable(Number value) implements Expression {
        @Override
        public double evaluate() {
            return value.evaluate();
        }
    }

    public record Addition(Number... x) implements Expression {
        @Override
        public double evaluate() {
            double result = 0;
            for (Number i : x) result += i.evaluate();
            return result;
        }
    }

    public record Subtraction(Number x, Number y) implements Expression {
        @Override
        public double evaluate() {
            return x.evaluate() - y.evaluate();
        }
    }

    public record Multiplication(Number... x) implements Expression {
        @Override
        public double evaluate() {
            double result = 1;
            for (Number i : x) result *= i.evaluate();
            return result;
        }
    }

    public record Division(Number x, Number y) implements Expression {
        @Override
        public double evaluate() {
            return x.evaluate() / y.evaluate();
        }
    }

    public record Modulo(Number x, Number y) implements Expression {
        @Override
        public double evaluate() {
            return x.evaluate() % y.evaluate();
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

    public static final Addition addition = new Addition(one, two);
    public static final Subtraction subtraction = new Subtraction(three, four);
    public static final Multiplication multiplication = new Multiplication(five, six);
    public static final Division division = new Division(seven, eight);
    public static final Modulo modulo = new Modulo(nine, ten);

    public static void main(String[] args) {
        System.out.println("addition: " + addition.evaluate());
        System.out.println("subtraction: " + subtraction.evaluate());
        System.out.println("multiplication: " + multiplication.evaluate());
        System.out.println("division: " + division.evaluate());
        System.out.println("modulo: " + modulo.evaluate());
    }

}
