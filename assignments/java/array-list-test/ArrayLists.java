import java.util.ArrayList;

public class ArrayLists {

  /*
   * Check whether a string is captitalized. You'll need this method for some of
   * the code you have to write.
   */
  public boolean isCapitalized(String s) {
    return s.equals(s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase());
  }

}
