using Homework4.Console.Models;
using Homework4.Console.Utils;

List<Menu> menus =
[
  new Menu(1, "Original Chicken", 21800, 60, "C001", "Food"),
  new Menu(2, "Super Besar 1", 35400, 10, "C001", "Food"),
  new Menu(3, "French Fries", 19000, 20, "C001", "Food"),
  new Menu(4, "Soft Ice Cream", 7200, 13, "C002", "Drinks"),
  new Menu(5, "Coca-Cola Medium", 11800, 35, "C002", "Drinks")
];

List<Cart> carts = [];
List<CheckoutHistory> checkoutHistories = [];

int mainMenuInput = -1;

while (mainMenuInput != 0)
{
  UI.MainMenu();

  Console.Write("Silahkan pilih menu: ");
  mainMenuInput = Input.Int();

  switch (mainMenuInput)
  {
    case 1:
      // Lihat menu
      Menu(menus, carts, checkoutHistories);
      break;

    case 2:
      // Keranjang
      ManageCart(carts, menus, checkoutHistories);
      break;

    case 3:
      // Checkout History
      CheckoutHistory(checkoutHistories);
      break;

    case 4:
      // Exit
      Console.WriteLine("\nTerima kasih sudah menggunakan layanan kami.");
      return;

    default:
      Console.WriteLine("\nMenu tidak tersedia.");
      break;
  }
}

static void Menu(List<Menu> menus, List<Cart> carts, List<CheckoutHistory> checkoutHistories)
{
  int menuInput = -1;

  while (menuInput! != 0)
  {
    Console.WriteLine("\n========== MENU ==========");
    menus.ForEach(menu =>
    {
      Console.WriteLine(menu.ShowInfo());
    });
    Console.WriteLine("0. Kembali");

    Console.Write("\nPilih makanan: ");
    menuInput = Input.Int();

    if (menuInput == 0)
    {
      break;
    }

    if (menuInput < 1 || menuInput > menus.Count)
    {
      Console.WriteLine("\nPilihan tidak tersedia.");
      continue;
    }

    int qtyInput;
    while (true)
    {
      Console.Write("Masukkan kuantitas: ");
      qtyInput = Input.Int();

      if (qtyInput > 0)
      {
        break;
      }
      else
      {
        Console.WriteLine("\nKuantitas harus  lebih dari 0");
      }
    }

    int menuIndex = menuInput - 1;
    Menu selectedMenu = menus[menuIndex];

    if (selectedMenu.Stock <= 0)
    {
      Console.WriteLine("\nStok habis");
      continue;
    }

    int qtyInCart = 0;
    Cart? existingCartItem = null;
    foreach (Cart cart in carts)
    {
      if (cart.Name == selectedMenu.Name)
      {
        qtyInCart = cart.Quantity;
        existingCartItem = cart;
        break;
      }
    }

    if ((selectedMenu.Stock - qtyInCart - qtyInput) < 0)
    {
      Console.WriteLine($"\nStok tidak cukup. Sisa stok {selectedMenu.Stock - qtyInCart} (Sudah di keranjang: {qtyInCart})");
      continue;
    }

    if (existingCartItem != null)
    {
      existingCartItem.IncreaseQuantity(qtyInput);
    }
    else
    {
      int newId = carts.Count > 0 ? carts[^1].CartId + 1 : 1;
      carts.Add(new Cart(newId, selectedMenu.Name, selectedMenu.Price, qtyInput));
    }

    Console.WriteLine("\nBerhasil ditambahkan ke keranjang!");

    Console.WriteLine("\n========== KERANJANG ==========");
    carts.ForEach(cart =>
    {
      Console.WriteLine(cart.ShowInfo(true));
    });

    bool shouldExitmenu = CheckoutProcess(carts, checkoutHistories, false, menus);

    if (shouldExitmenu)
    {
      menuInput = 0;
    }
  }
}

static bool CheckoutProcess(List<Cart> carts, List<CheckoutHistory> checkoutHistories, bool isCartMenu, List<Menu> menus)
{
  if (carts.Count == 0)
  {
    Console.WriteLine("Keranjang kosong");
    return false;
  }

  long total = carts.Sum(cart => cart.Quantity * cart.Price);

  Console.WriteLine($"\nTotal Belanja: {total:C0}");

  string addMoreInput = "";

  if (!isCartMenu)
  {
    while (true)
    {
      Console.Write("\nTambah item lagi? (Y/n): ");
      addMoreInput = Input.String();

      if (addMoreInput?.ToLower() == "y" || addMoreInput?.ToLower() == "n")
      {
        break;
      }
      else
      {
        Console.WriteLine("\nPilihan tidak tersedia.");
      }
    }
  }

  if (addMoreInput?.ToLower() == "n" || isCartMenu)
  {
    string checkoutInput = "";

    while (true)
    {
      Console.Write("\nCheckout (Y/n): ");
      checkoutInput = Input.String();

      if (checkoutInput?.ToLower() == "y" || checkoutInput?.ToLower() == "n")
      {
        break;
      }
      else
      {
        Console.WriteLine("\nPilihan tidak tersedia.");
      }
    }

    if (checkoutInput?.ToLower() == "y")
    {
      Console.WriteLine("\nMetode Pembayaran:");

      PaymentMethod[] paymentMethods = [new Cash("Cash"), new Cashless("QRIS")];

      for (int i = 0; i < paymentMethods.Length; i++)
      {
        Console.WriteLine((i + 1) + ". " + paymentMethods[i].GetName());
      }

      PaymentMethod? finalPaymentMethod = null;

      while (true)
      {
        Console.Write("\nPilih metode: ");
        int paymentMethodInput = Input.Int();

        if (paymentMethodInput >= 1 && paymentMethodInput <= paymentMethods.Length)
        {
          finalPaymentMethod = paymentMethods[^1];
          break;
        }
        else
        {
          Console.WriteLine("\nPilihan tidak tersedia.");
        }
      }

      while (true)
      {
        Console.Write("Masukkan uang: ");
        int payAmountInput = Input.Int();

        if (payAmountInput < 0)
        {
          Console.WriteLine("\nUang tidak boleh negatif.");
        }
        else if (payAmountInput >= 0)
        {
          finalPaymentMethod.Pay(payAmountInput, total);

          if (finalPaymentMethod.GetIsSuccess())
          {
            // ========= Receipt
            Console.WriteLine("\n========== RECEIPT ==========");
            carts.ForEach(cart =>
            {
              Console.WriteLine(cart.ShowInfo(false));
            });
            Console.WriteLine("-----------------------------");
            Console.WriteLine($"Total     : {total:C0}");
            Console.WriteLine($"Bayar     : {payAmountInput:C0}");
            if (finalPaymentMethod.GetChange() > 0)
            {
              Console.WriteLine($"Kembalian : {finalPaymentMethod.GetChange():C0}");
            }

            Console.WriteLine("\nTerima kasih sudah membeli!");

            int newTransactionId = checkoutHistories.Count + 1;
            checkoutHistories.Add(new CheckoutHistory(newTransactionId, new List<Cart>(carts), finalPaymentMethod.GetName(), total));

            foreach (Cart cart in carts)
            {
              foreach (Menu menu in menus)
              {
                if (menu.Name == cart.Name)
                {
                  menu.DecreaseStock(cart.Quantity);
                  break;
                }
              }
            }

            carts.Clear();

            return true;
          }
        }
        else
        {
          Console.WriteLine("\nPilihan tidak tersedia.");
        }
      }
    }
    else
    {
      return true;
    }
  }

  return false;
}

static void ManageCart(List<Cart> carts, List<Menu> menus, List<CheckoutHistory> checkoutHistories)
{
  while (true)
  {
    Console.WriteLine("\n========== KERANJANG ==========");

    if (carts.Count == 0)
    {
      Console.WriteLine("Keranjang kosong");
      return;
    }

    for (int i = 0; i < carts.Count; i++)
    {
      Console.WriteLine((i + 1) + ". " + carts[i].ShowInfo(false));
    }

    Console.WriteLine("\nOpsi Keranjang:");
    Console.WriteLine("1. Checkout");
    Console.WriteLine("2. Ubah Kuantitas Item");
    Console.WriteLine("3. Hapus Item");
    Console.WriteLine("0. Kembali");

    Console.Write("\nPilih opsi: ");
    int optionInput = Input.Int();

    if (optionInput == 0)
    {
      break;
    }
    else if (optionInput == 1)
    {
      bool success = CheckoutProcess(carts, checkoutHistories, true, menus);
      if (success)
      {
        break;
      }
    }
    else if (optionInput == 2)
    {
      // Ubah kuantitas
      Console.Write("Pilih nomor item yang ingin diubah: ");
      int editIndex = Input.Int() - 1;

      if (editIndex >= 0 && editIndex < carts.Count)
      {
        Cart selectedCart = carts[editIndex];
        Console.Write("Masukkan kuantitas baru: ");
        int newQty = Input.Int();

        if (newQty <= 0)
        {
          Console.WriteLine("\nKuantitas harus lebih dari 0!");
        }
        else
        {
          // Validasi ketersediaan stok master
          int availableStock = 0;
          foreach (Menu m in menus)
          {
            if (m.Name == selectedCart.Name)
            {
              availableStock = m.Stock;
              break;
            }
          }

          if (availableStock >= newQty)
          {
            selectedCart.SetQuantity(newQty);
            Console.WriteLine("\nKuantitas berhasil diubah!");
          }
          else
          {
            Console.WriteLine($"\nStok tidak mencukupi! Maksimal yang bisa dipesan: {availableStock}");
          }
        }
      }
      else
      {
        Console.WriteLine("\nItem tidak ditemukan.");
      }
    }
    else if (optionInput == 3)
    {
      // Hapus Item
      Console.Write("Pilih nomor item yang ingin dihapus: ");
      int delIndex = Input.Int() - 1;

      if (delIndex >= 0 && delIndex < carts.Count)
      {
        Cart itemToRemoved = carts[delIndex];
        carts.RemoveAt(delIndex);
        Console.WriteLine($"\n{itemToRemoved.Name} berhasil dihapus dari keranjang.");
      }
      else
      {
        Console.WriteLine("\nItem tidak ditemukan.");
      }
    }
    else
    {
      Console.WriteLine("\nPikihan tidak tersedia");
    }
  }
}

static void CheckoutHistory(List<CheckoutHistory> checkoutHistories)
{
  Console.WriteLine("\n========== CHECKOUT HISTORY ==========");
  if (checkoutHistories.Count > 0)
  {
    checkoutHistories.ForEach(ch =>
    {
      ch.ShowInfo();
    });
  }
  else
  {
    Console.WriteLine("Checkout kosong");
  }
}