package rig;

import parser.ByteSize;

public class ByteSizeTest {
  public static void main(String[] args) {
    test("1024B", 1024);
    test("1KB", 1024);
    test("2KB", 2048);
    test("1.5MB", 1572864); // 1.5 * 1024 * 1024
    test("0.5GB", 536870912); // 0.5 * 1024^3
    test("100B", 100);
    test("3GB", 3221225472L); // 3 * 1024^3
    test("   256KB ", 262144); // spaces and casing

    // Optional: test invalid
    try {
      new ByteSize("badFormat");
      System.out.println("❌ Error expected but not thrown");
    } catch (IllegalArgumentException e) {
      System.out.println("✅ Caught expected exception for 'badFormat'");
    }
  }

  private static void test(String input, long expectedBytes) {
    try {
      ByteSize size = new ByteSize(input);
      long actual = size.getBytes();
      if (actual == expectedBytes) {
        System.out.println("✅ " + input + " → " + actual + " bytes");
      } else {
        System.out.println("❌ " + input + " → " + actual + " bytes (expected " + expectedBytes + ")");
      }
    } catch (Exception e) {
      System.out.println("❌ " + input + " → Exception: " + e.getMessage());
    }
  }
}
