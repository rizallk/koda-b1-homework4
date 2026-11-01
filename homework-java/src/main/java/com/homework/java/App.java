package com.homework.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    List<Menu> menus = new ArrayList<>();

    menus.add(
        new Menu(1, "Original Chicken", 21800, 60, "C001", "Food"));
    menus.add(
        new Menu(2, "Super Besar 1", 35400, 10, "C001", "Food"));
    menus.add(
        new Menu(3, "French Fries", 19000, 28, "C001", "Food"));
    menus.add(
        new Menu(4, "Soft Ice Cream", 7200, 13, "C002", "Drinks"));
    menus.add(
        new Menu(5, "Coca-Cola Medium", 11800, 35, "C002", "Drinks"));

    List<Cart> carts = new ArrayList<>();
    List<CheckoutHistory> checkoutHistories = new ArrayList<>();

    int mainMenuInput = -1;

    while (mainMenuInput != 0) {
      UI.mainMenu();

      System.out.print("Silahkan pilih menu: ");
      mainMenuInput = Input.getIntInput(scanner);

      switch (mainMenuInput) {
        case 1:
          // Lihat menu
          menu(menus, scanner, carts, checkoutHistories);
          break;

        case 2:
          // Keranjang
          manageCart(carts, menus, checkoutHistories, scanner);
          break;

        case 3:
          // Checkout History
          checkoutHistory(checkoutHistories);
          break;

        case 4:
          // Exit
          System.out.println("\nTerima kasih sudah menggunakan layanan kami.");
          return;

        default:
          System.out.println("\nMenu tidak tersedia.");
      }
    }

    scanner.close();
  }

  private static void menu(List<Menu> menus, Scanner scanner, List<Cart> carts,
      List<CheckoutHistory> checkoutHistories) {
    int menuInput = -1;

    while (menuInput != 0) {
      System.out.println("\n========== MENU ==========");
      menus.forEach(menu -> {
        System.out.println(menu.showInfo());
      });
      System.out.println("0. Kembali");

      System.out.print("\nPilih makanan: ");
      menuInput = Input.getIntInput(scanner);

      if (menuInput == 0) {
        break;
      }

      if (menuInput < 1 || menuInput > menus.size()) {
        System.out.println("\nPilihan tidak tersedia.");
        continue;
      }

      int qtyInput = 0;
      while (true) {
        System.out.print("Masukkan kuantitas: ");
        qtyInput = Input.getIntInput(scanner);

        if (qtyInput > 0) {
          break;
        } else {
          System.out.println("\nKuantitas harus lebih dari 0!");
        }
      }

      int menuIndex = menuInput - 1;
      Menu selectedMenu = menus.get(menuIndex);

      if (selectedMenu.getStock() <= 0) {
        System.out.println("\nStok habis");
        continue;
      }

      int qtyInCart = 0;
      Cart existingCartItem = null;
      for (Cart cart : carts) {
        if (cart.getName().equals(selectedMenu.getName())) {
          qtyInCart = cart.getQuantity();
          existingCartItem = cart;
          break;
        }
      }

      if ((selectedMenu.getStock() - qtyInCart - qtyInput) < 0) {
        System.out.println("\nStok tidak cukup. Sisa stok: " + (selectedMenu.getStock() - qtyInCart) +
            " (Sudah di keranjang: " + qtyInCart + ")");
        continue;
      }

      if (existingCartItem != null) {
        existingCartItem.setQuantity(existingCartItem.getQuantity() + qtyInput);
      } else {
        int newId = carts.isEmpty() ? 1 : carts.get(carts.size() - 1).getCartId() + 1;
        carts.add(new Cart(newId, selectedMenu.getName(), selectedMenu.getPrice(), qtyInput));
      }

      System.out.println("\nBerhasil ditambahkan ke keranjang!");

      System.out.println("\n========== KERANJANG ==========");
      carts.forEach(cart -> {
        System.out.println(cart.showInfo(true));
      });

      boolean shouldExitMenu = checkoutProcess(carts, checkoutHistories, scanner, false, menus);

      if (shouldExitMenu) {
        menuInput = 0;
      }
    }
  }

  private static boolean checkoutProcess(List<Cart> carts, List<CheckoutHistory> checkoutHistories, Scanner scanner,
      boolean isCartMenu, List<Menu> menus) {
    if (carts.isEmpty()) {
      System.out.println("Keranjang kosong");
      return false;
    }

    long total = carts.stream().mapToLong(cart -> cart.getQuantity() * cart.getPrice()).sum();

    System.out.println("\nTotal Belanja: " + Helper.formatToRupiah(total));

    String addMoreInput = "";

    if (!isCartMenu) {
      while (true) {
        System.out.print("\nTambah item lagi? (Y/n): ");
        addMoreInput = Input.getStrInput(scanner);

        if (addMoreInput.equalsIgnoreCase("y") || addMoreInput.equalsIgnoreCase("n")) {
          break;
        } else {
          System.out.println("\nPilihan tidak tersedia.");
        }
      }
    }

    if (addMoreInput.equalsIgnoreCase("n") || isCartMenu) {
      String checkoutInput = "";

      while (true) {
        System.out.print("\nCheckout (Y/n): ");
        checkoutInput = Input.getStrInput(scanner);

        if (checkoutInput.equalsIgnoreCase("y") ||
            checkoutInput.equalsIgnoreCase("n")) {
          break;
        } else {
          System.out.println("\nPilihan tidak tersedia.");
        }
      }

      if (checkoutInput.equalsIgnoreCase("y")) {
        System.out.println("\nMetode Pembayaran:");

        PaymentMethod[] paymentMethods = { new Cash("Cash"), new Cashless("QRIS") };

        for (int i = 0; i < paymentMethods.length; i++) {
          System.out.println((i + 1) + ". " + paymentMethods[i].getName());
        }

        PaymentMethod finalPaymentMethod = null;

        while (true) {
          System.out.print("\nPilih metode: ");
          int paymentMethodInput = Input.getIntInput(scanner);

          if (paymentMethodInput >= 1 && paymentMethodInput <= paymentMethods.length) {
            finalPaymentMethod = paymentMethods[paymentMethodInput - 1];
            break;
          } else {
            System.out.println("\nPilihan tidak tersedia.");
          }
        }

        while (true) {
          System.out.print("Masukkan uang: ");
          int payAmountInput = Input.getIntInput(scanner);

          if (payAmountInput < 0) {
            System.out.println("\nUang tidak boleh negatif.");
          } else if (payAmountInput >= 0) {
            finalPaymentMethod.pay(payAmountInput, total);

            if (finalPaymentMethod.getIsSuccess()) {
              // ========= Receipt
              System.out.println("\n========== RECEIPT ==========");
              carts.forEach(cart -> {
                System.out.println(cart.showInfo(false));
              });
              System.out.println("-----------------------------");
              System.out.println("Total     : " + Helper.formatToRupiah(total));
              System.out.println("Bayar     : " + Helper.formatToRupiah(payAmountInput));
              if (finalPaymentMethod.getChange() > 0) {
                System.out.println("Kembalian : " + Helper.formatToRupiah(finalPaymentMethod.getChange()));
              }

              System.out.println("\nTerima kasih sudah membeli!");

              int newTransactionId = checkoutHistories.size() + 1;
              checkoutHistories
                  .add(new CheckoutHistory(newTransactionId, new ArrayList<>(carts), finalPaymentMethod.getName(),
                      total));

              for (Cart cart : carts) {
                for (Menu menu : menus) {
                  if (menu.getName().equals(cart.getName())) {
                    menu.decreaseStock(cart.getQuantity());
                    break;
                  }
                }
              }

              carts.clear();

              return true;
            }
          } else {
            System.out.println("\nPilihan tidak tersedia.");
          }
        }
      } else {
        return true;
      }
    }

    return false;
  }

  private static void manageCart(List<Cart> carts, List<Menu> menus, List<CheckoutHistory> checkoutHistories,
      Scanner scanner) {
    while (true) {
      System.out.println("\n========== KERANJANG ==========");

      if (carts.isEmpty()) {
        System.out.println("Keranjang kosong");
        return;
      }

      for (int i = 0; i < carts.size(); i++) {
        System.out.println((i + 1) + ". " + carts.get(i).showInfo(false));
      }

      System.out.println("\nOpsi Keranjang:");
      System.out.println("1. Checkout");
      System.out.println("2. Ubah Kuantitas Item");
      System.out.println("3. Hapus Item");
      System.out.println("0. Kembali");

      System.out.print("\nPilih opsi: ");
      int optionInput = Input.getIntInput(scanner);

      if (optionInput == 0) {
        break;
      } else if (optionInput == 1) {
        boolean success = checkoutProcess(carts, checkoutHistories, scanner, true, menus);
        if (success) {
          break;
        }
      } else if (optionInput == 2) {
        // Ubah Kuantitas
        System.out.print("Pilih nomor item yang ingin diubah: ");
        int editIndex = Input.getIntInput(scanner) - 1;

        if (editIndex >= 0 && editIndex < carts.size()) {
          Cart selectedCart = carts.get(editIndex);
          System.out.print("Masukkan kuantitas baru: ");
          int newQty = Input.getIntInput(scanner);

          if (newQty <= 0) {
            System.out.println("\nKuantitas harus lebih dari 0!");
          } else {
            // Validasi ketersediaan stok master
            int availableStock = 0;
            for (Menu m : menus) {
              if (m.getName().equals(selectedCart.getName())) {
                availableStock = m.getStock();
                break;
              }
            }

            if (availableStock >= newQty) {
              selectedCart.setQuantity(newQty);
              System.out.println("\nKuantitas berhasil diubah!");
            } else {
              System.out.println("\nStok tidak mencukupi! Maksimal yang bisa dipesan: " + availableStock);
            }
          }
        } else {
          System.out.println("\nItem tidak ditemukan.");
        }
      } else if (optionInput == 3) {
        // Hapus Item
        System.out.print("Pilih nomor item yang ingin dihapus: ");
        int delIndex = Input.getIntInput(scanner) - 1;

        if (delIndex >= 0 && delIndex < carts.size()) {
          Cart removed = carts.remove(delIndex);
          System.out.println("\n" + removed.getName() + " berhasil dihapus dari keranjang.");
        } else {
          System.out.println("\nItem tidak ditemukan.");
        }
      } else {
        System.out.println("\nPilihan tidak tersedia.");
      }
    }
  }

  private static void checkoutHistory(List<CheckoutHistory> checkoutHistories) {
    System.out.println("\n========== CHECKOUT HISTORY ==========");
    if (checkoutHistories.size() > 0) {
      checkoutHistories.forEach(ch -> {
        ch.showInfo();
      });
    } else {
      System.out.println("Checkout kosong");
    }
  }
}
