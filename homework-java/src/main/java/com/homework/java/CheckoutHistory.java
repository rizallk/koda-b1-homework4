package com.homework.java;

import java.util.ArrayList;
import java.util.List;

public class CheckoutHistory {
  private int transactionId;
  private List<Cart> items;
  private String paymentMethod;
  private long totalAmount;

  public CheckoutHistory(int transactionId, List<Cart> items, String paymentMethod, long totalAmount) {
    this.transactionId = transactionId;
    this.items = new ArrayList<>(items);
    this.paymentMethod = paymentMethod;
    this.totalAmount = totalAmount;
  }

  public void showInfo() {
    System.out.println("\n---------- TRANSAKSI #" + transactionId + " ----------");
    System.out.println("Metode Pembayaran: " + paymentMethod);

    for (Cart item : items) {
      System.out
          .println("- " + item.getName() + " (x" + item.getQuantity() + ") " + Helper.formatToRupiah(item.getPrice()));
    }

    System.out.println("Total Belanja: " + Helper.formatToRupiah(totalAmount));
  }
}
