
import java.util.Arrays;

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
    return result;
  }

  // public String disemvowel(String s) {
  //   return Arrays
  //       .stream()
  //       .filter(c -> !isVowel(c))
  //       .toList();
  // }

}
