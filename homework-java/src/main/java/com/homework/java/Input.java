package com.homework.java;

import java.util.Scanner;

public class Input {
  public static int getIntInput(Scanner scanner) {
    try {
      return scanner.nextInt();
    } catch (Exception e) {
      scanner.nextLine();
      return -1;
    }
  }

  public static String getStrInput(Scanner scanner) {
    try {
      return scanner.next();
    } catch (Exception e) {
      scanner.nextLine();
      return "";
    }
  }
}
