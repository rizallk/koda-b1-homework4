package com.homework.java;

public class Cash extends PaymentMethod {
  private String name;
  private long change = 0;
  private boolean isSuccess;

  public Cash(String name) {
    this.name = name;
  }

  @Override
  public void pay(long amount, long total) {
    if (amount < total) {
      System.out.println("\nSaldo Anda tidak cukup.\n");
      isSuccess = false;
    } else {
      if (amount > total) {
        change = amount - total;
      }
      isSuccess = true;
    }
  }

  @Override
  public String getPaymentDetails() {
    return "Metode Pembayaran Tunai (%s)".formatted(name);
  }

  public String getName() {
    return name;
  }

  public long getChange() {
    return change;
  }

  public boolean getIsSuccess() {
    return isSuccess;
  }
}
