import java.util.ArrayList;
import java.util.List;

public class ArrayLists {

  /*
   * Check whether a string is captitalized. You'll need this method for some of
   * the code you have to write.
   */
  public boolean isCapitalized(String s) {
    return s.equals(s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase());
  }

  public ArrayList<String> yummy(int x) {
    var list = new ArrayList<String>();
    for (int i = 0; i < x; i++) {
      list.add("yum");
    }
    return list;
  }

  public ArrayList<String> shouty(ArrayList<String> ss) {
    var list = new ArrayList<String>();
    for (int i = 0; i < ss.size(); i++) {
      list.add(ss.get(i).toUpperCase());
    }
    return list;
  }

  public void replaceEmpty(ArrayList<String> ss) {
    for (int i = 0; i < ss.size(); i++) {
      if (ss.get(i).length() == 0) {
        ss.add(i+1, "EMPTY");
        ss.remove(i);
      }
    }
  }

  public int countCapitalized(ArrayList<String> ss) {
    int result = 0;
    for (int i = 0; i < ss.size(); i++) {
      if (isCapitalized(ss.get(i))) result++;
    }
    return result;
  }

  public String findLastCapitalized(ArrayList<String> ss) {
    String result = null;
    for (int i = 0; i < ss.size(); i++) {
      if (isCapitalized(ss.get(i))) result = ss.get(i);
    }
    return result;
  }

  public ArrayList<String> justCapitalized(ArrayList<String> ss) {
    var result = new ArrayList<String>();
    for (int i = 0; i < ss.size(); i++) {
      if (isCapitalized(ss.get(i))) result.add(ss.get(i));
    }
    return result;
  }

  // public void chickenFirst(ArrayList<String> ss) {
  //   for (int i = 1; i < ss.size(); i++) {
  //     if (ss.get(i) == "egg") {
  //       ss.add(i-1, "chicken");
  //     }
  //   }
  // }

  // public void actuallyEggFirst(ArrayList<String> ss) {
  //   for (int i = 0; i < ss.size() - 2; i++) {
  //     if (ss.get(i) == "chicken" && ss.get(i+1) == "egg") {
  //       ss.remove(i);
  //       ss.remove(i+1);
  //       ss.add(i, "egg");
  //       ss.add(i+1, "chicken");
  //     }
  //   }
  // }

  public void removeEggs(ArrayList<String> ss) {
    for (int j = 0; j < 3; j++) {
      for (int i = 0; i < ss.size(); i++) {
        if (ss.get(i) == "egg") {
          ss.remove(i);
        }
      }
    }
  }

  public ArrayList<Integer> biggerSum(ArrayList<Integer> one, ArrayList<Integer> two) {
    if (mean(one) > mean(two)) {
      return one;
    } else return two;
  }

  public int mean(ArrayList<Integer> list) {
    int result = 0;
    list.forEach(x -> result+=x);
    return result;
  }
}
