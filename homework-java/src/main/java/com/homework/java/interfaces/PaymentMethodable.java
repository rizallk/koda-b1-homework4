package com.homework.java.interfaces;

public interface PaymentMethodable {
  void pay(long amount, long total);

  boolean getIsSuccess();

  long getChange();

  String getPaymentDetails();
}
