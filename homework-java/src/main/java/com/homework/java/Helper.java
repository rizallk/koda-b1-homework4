package com.homework.java;

import java.text.NumberFormat;
import java.util.Locale;

public class Helper {
  public static String formatToRupiah(long number) {
    NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.of("id", "ID"));
    numberFormat.setMaximumFractionDigits(0);
    return numberFormat.format(number);
  }
}
