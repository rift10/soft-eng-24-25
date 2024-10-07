
import java.util.Arrays;
import java.util.stream.Collectors;

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

  // public String[] collectFourLetterWords(String[] ss) {
  //   return Arrays
  //       .stream(ss)
  //       .filter(s -> s.length() == 4)
  //       .toArray();
  // }

  public String[] collectShouting(String[] ss) {
    return Arrays
        .stream(ss)
        .map(s -> s.toUpperCase())
        .toArray();
  }

  // public int hailstoneLength() {}

  // public int hailstoneMax() {}

  // public hailstone() {}

  // public lengthHistogram() {}

}
