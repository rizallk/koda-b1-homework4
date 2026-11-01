package com.homework.java;

import com.homework.java.interfaces.Menuable;

public class Menu extends Category implements Menuable {
  private int menuId;
  private String name;
  private int price;
  private int stock;

  public Menu(int menuId, String name, int price, int stock, String categoryId,
      String categoryName) {
    super(categoryId, categoryName);
    this.menuId = menuId;
    this.name = name;
    this.price = price;
    this.stock = stock;
  }

  public int getMenuId() {
    return menuId;
  }

  public String getName() {
    return name;
  }

  public int getPrice() {
    return price;
  }

  public int getStock() {
    return stock;
  }

  public void decreaseStock(int stock) {
    if ((this.stock - stock) < 0) {
      System.out.println("Stok tidak boleh negatif");
      return;
    }
    this.stock -= stock;
  }

  public String showInfo() {
    return "%d. %s - %s".formatted(menuId, name, Helper.formatToRupiah(price));
  }
}
