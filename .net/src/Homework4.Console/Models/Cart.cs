namespace Homework4.Console.Models;

public class Cart(int cartId, string name, int price, int quantity)
{
  public int CartId { get; set; } = cartId;
  public string Name { get; set; } = name;
  public int Price { get; set; } = price;
  public int Quantity { get; private set; } = quantity;

  public void SetQuantity(int amount)
  {
    if (amount < 0)
    {
      System.Console.WriteLine("Kuantitas harus lebih dari 0!");
      return;
    }
    Quantity = amount;
  }

  public void IncreaseQuantity(int amount)
  {
    if (amount < 0)
    {
      System.Console.WriteLine("Kuantitas harus lebih dari 0!");
      return;
    }
    Quantity += amount;
  }

  public string ShowInfo(bool withId)
  {
    long subTotal = Price * Quantity;
    return withId ? $"{CartId}. {Name} (x{Quantity}) {subTotal:C0}" : $"{Name} (x{Quantity}) {subTotal:C0}";
  }
}