import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.lang.StringBuilder;
import java.lang.Integer;

public class Xor {

  // This is the cipher text, encoded as a hex string. If you translate
  // every two characters of this string into a byte you will have an
  // array of bytes which can be decoded by xor'ing the bytes with bits
  // from the key, taking first the 8 least significant bits of the key
  // for the 0th byte, the the next 8, and so on, looping back around to
  // the least significant bits every four bytes.
  private static final String CIPHERTEXT =
    "452dbb017333a6456328a64f6064a6522722ba4f26";

  // You shouldn't change the code in main but you do need to implement
  // the decode method and an appropriate constructor to make it work.
  // And you'll probably want to implement some helper methods along the
  // way.
  //
  // To convert a String containing a hex digit into a numeric value
  // you'll want to look up the Integer.parseInt method.
  //
  // Also I should have talked to you about the byte data type and how
  // to use the cast operator to cast an int to a byte.
  //
  // To make a String from an array of bytes (byte[]) you can use the
  // String constructor String(byte[] bytes, String enc) like:
  //
  //   new String(bytes, StandardCharsets.UTF_8);
  //
  // The value 567231495 passed to the constructor is the secret key.
  // It's just a random int, i.e. 32 random bits. I produced the value
  // of CIPHERTEXT by first converting a String message to bytes via the
  // String method:
  //
  //   s.getBytes(StandardCharsets.UTF_8)
  //
  // and then encoding those bytes via the same process as I described
  // above for decoding. (This is a symmetric cipher meaning encryption
  // and decryption are the same process.)

  private int key;
  private int[] byteMasks = {0b10000000, 0b01000000, 0b00100000, 0b00010000};
  private static final int[] FIRST_BYTE_MASKS = {0b01111111, 0b00011111, 0b00001111, 0b00000111};

  public Xor(int key) {
    this.key = key;
  }

  public byte[] cipherToBytes(String cipher) {
    byte[] cipherBytes = new byte[cipher.length() / 2 + 1];
    int index = 0;
    for (int i = 0; i < cipher.length() - 1; i += 2) {
      cipherBytes[index] = (byte) Integer.parseInt(cipher.substring(i, i + 2), 16);
      index++;
    }
    return cipherBytes;
  }

  public byte[] xor(String cipher) {
    int keySection = key;
    byte[] cipherBytes = cipherToBytes(cipher);
    byte[] result = new byte[cipher.length() / 2 + 1];
    for (int i = 0; i < cipher.length() / 2; i++) {
      if (i % 4 == 0) keySection = key;
      result[i] = (byte) (keySection ^ cipherBytes[i]);
      keySection = keySection >> 8;
    }
    return result;
  }

  public String decodeOneByteToUtf8(byte[] letterBytes) {
    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < letterBytes.length; i++) {
      sb.append(Character.toChars(letterBytes[i]));
    }

    return sb.toString();
  }

  public String decodeToUtf8(byte[] letterBytes) {
    StringBuilder sb = new StringBuilder();
    int numBytes = 1;
    byte codePoint = 0;
    System.out.println(letterBytes.length);
    
    // for (int i = 0; i < letterBytes.length; i++) {
    //   for (int j = 0; j < byteMasks.length; j++) {
    //     System.out.println("byte: " + Integer.toBinaryString(letterBytes[i]) + 
    //     ", checker: " + Integer.toBinaryString(byteMasks[j]) + 
    //     ", result: " +  ((letterBytes[i] & (byteMasks[j])) >>> 7 - j) +
    //      ", boolean: " + (((letterBytes[i] & (byteMasks[j])) >>> 7 - j) == 1));
    //   }
    // }

    for (int i = 0; i < letterBytes.length; i += numBytes) {
      numBytes = 0;
      while (((letterBytes[i] & (byteMasks[numBytes])) >>> (7 - numBytes)) == 1) {
        // System.out.println("byte: " + Integer.toBinaryString(letterBytes[i]) + 
        // ", checker: " + Integer.toBinaryString(byteMasks[numBytes]) + 
        // ", result: " +  ((letterBytes[i] & (byteMasks[numBytes])) >>> (7 - numBytes)) +
        //  ", boolean: " + (((letterBytes[i] & (byteMasks[numBytes])) >>> (7 - numBytes)) == 1));
        numBytes++;
      }
      // codePoint = 0;
      // for (int j = 0; j < numBytes - 1; j++) {
      //   codePoint |= letterBytes[i + j];
      // }
      // // sb.append(Character.toChars(codePoint));
    }

    return new String();
    // return sb.toString();
  }

  // private String decodeUtf8(byte[] bytes) {
  //   var sb = new StringBuilder();
  //   var i = 0;
  //   while (i < bytes.length) {
  //     var n = numBytes(bytes[i]);
  //     var codePoint = bytes[i++] & FIRST_BYTE_MASKS[n - 1];
  //     for (var j = 0; j < n - 1; j++) {
  //       codePoint = (codePoint << 6) | (bytes[i++] & 0b00111111);
  //     }
  //     sb.append(Character.toChars(codePoint));
  //   }
  //   return sb.toString();
  // }

  // private int numBytes(byte b) {
  //   var n = Integer.numberOfLeadingZeros(~b & 0xff) - 24;
  //   return n == 0 ? 1 : n;
  // }

  public String decode(String cipher) {
    return new String(xor(cipher), StandardCharsets.UTF_8);
  }

  public String decodeWithUtf8(String text) {
    // return decodeOneByteToUtf8(new String(text).getBytes(StandardCharsets.UTF_8));
    return decodeToUtf8(new String(text).getBytes(StandardCharsets.UTF_8));
  }

  public static void main(String[] argv) throws Exception {
    // System.out.println(new Xor(567231495).decode(CIPHERTEXT));
    // System.out.println(new Xor(567231495).decodeWithUtf8("hello world"));
    System.out.println(new Xor(567231495).decodeWithUtf8("(づ ◕‿◕ )づ"));

  }
}
