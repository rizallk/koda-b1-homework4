using Homework4.Console.Interface;

namespace Homework4.Console.Models;

public class Menu(int menuId, string name, int price, int stock, string categoryId, string categoryName) : Category(categoryId, categoryName), IMenu
{
  public int MenuId { get; } = menuId;
  public new string Name { get; } = name;
  public int Price { get; } = price;
  public int Stock { get; set; } = stock;

  public void DecreaseStock(int stock)
  {
    if ((Stock - stock) < 0)
    {
      System.Console.WriteLine("Stok tidak boleh negatif");
      return;
    }
    Stock -= stock;
  }

  public string ShowInfo()
  {
    return $"{MenuId}. {Name} - {Price:C0}";
  }
}