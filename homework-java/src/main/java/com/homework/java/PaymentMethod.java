package com.homework.java;

import com.homework.java.interfaces.PaymentMethodable;

public abstract class PaymentMethod implements PaymentMethodable {
  public abstract void pay(long amount, long total);

  public abstract String getPaymentDetails();

  public abstract String getName();
}
