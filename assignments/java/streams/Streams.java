
import java.util.Arrays;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Streams {

  /*
   * Returns true if its argument is a single-character string and a vowel.
   */
  public boolean isVowel(String s) {
    return s.length() == 1 && "aeiou".indexOf(s.toLowerCase()) != -1;
  }

  /*
   * Returns true if its argument is a prime number.
   */
  public boolean isPrime(int n) {
    for (int d = 2; d <= Math.sqrt(n); d++) {
      if (n % d == 0) return false;
    }
    return n > 1;
  }

  public int countPrimes(int[] ii) {
    return (int) Arrays
        .stream(ii)
        .filter(n -> isPrime(n))
        .count();
  }

  public int countVowels(String s) {
    return (int) Arrays
        .stream(s.split(""))
        .filter(c -> isVowel(c))
        .count();
  }

  public int indexOfVowel(String s) {
    int result = Arrays
        .stream(s.split(""))
        .map(c -> c.substring(0, 1))
        .takeWhile(c -> !isVowel(c))
        .toArray()
        .length;
    return (result >= s.length()) ? -1 : result;
  }

  public String disemvowel(String s) {
    return Arrays
        .stream(s.split(""))
        .filter(c -> !isVowel(c))
        .collect(Collectors.joining());
  }

  public int countStartsWithVowel(String[] ss) {
    return (int) Arrays
        .stream(ss)
        .map(s -> s.substring(0, 1))
        .filter(c -> isVowel(c))
        .count();
  }

  public int totalLength(String[] ss) {
    return (int) Arrays
        .stream(ss)
        .collect(Collectors.summingInt(s -> s.length()));
  }

  public int[] xs(String[] ss) {
    return Arrays
        .stream(ss)
        .mapToInt(s -> s.indexOf("x"))
        .toArray();
  }

  public int countByLength(String[] ss, int i) {
    return (int) Arrays
        .stream(ss)
        .filter(s -> s.length() == i)
        .count();
  }

  public String[] collectFourLetterWords(String[] ss) {
    return Arrays
        .stream(ss)
        .filter(s -> s.length() == 4)
        .toArray(String[]::new);
  }

  public String[] collectShouting(String[] ss) {
    return Arrays
        .stream(ss)
        .map(s -> s.toUpperCase())
        .toArray(String[]::new);
  }

  private int nextHailstone(int n) {
    return (n % 2 == 0) ? n/2 : 3 * n + 1;
  }

  private IntStream hailstoneStream(int start) {
    return IntStream
        .concat(
          IntStream
            .iterate(start, n -> nextHailstone(n))
            .takeWhile(n -> n != 1),
          IntStream.of(1)
        );
  }

  public int hailstoneLength(int start) {
    return (int) hailstoneStream(start)
        .count();
  }

  public int hailstoneMax(int start) {
    var result = hailstoneStream(start)
        .max();
    return result.isPresent() ? result.getAsInt() : -1;
  }

  public int[] hailstone(int start) {
    return hailstoneStream(start)
        .toArray();
  }

  public int[] lengthHistogram(String[] ss) {
    return IntStream
        .range(0, 10)
        .iterate(0, i -> (int) (Arrays 
          .stream(ss)
          .mapToInt(s -> s.length())
          .filter(s -> s == i)
          .count())
        ) 
        .toArray();
  }

}
