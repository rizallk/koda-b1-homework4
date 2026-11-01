package com.homework.java;

public class Cart {
  private int cartId = 0;
  private String name;
  private int price;
  private int quantity;

  public Cart(int cartId, String name, int price, int quantity) {
    this.cartId = cartId;
    this.name = name;
    this.price = price;
    this.quantity = quantity;
  }

  public int getCartId() {
    return cartId;
  }

  public void setCartId(int cartId) {
    this.cartId = cartId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    if (quantity < 0) {
      System.out.println("Kuantitas harus lebih dari 0!");
      return;
    }
    this.quantity = quantity;
  }

  public String showInfo(boolean withId) {
    return withId ? "%d. %s (x%d) %s".formatted(cartId, name, quantity, Helper.formatToRupiah(price * quantity))
        : "%s (x%d) %s".formatted(name, quantity, Helper.formatToRupiah(price * quantity));
  }
}
