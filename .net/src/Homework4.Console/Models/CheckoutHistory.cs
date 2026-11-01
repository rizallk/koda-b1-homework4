namespace Homework4.Console.Models;

public class CheckoutHistory(int transactionId, List<Cart> items, string paymentMethod, long totalAmount)
{
  private readonly int TransactionId = transactionId;
  private readonly List<Cart> Items = new List<Cart>(items);
  private readonly string PaymentMethod = paymentMethod;
  private readonly long TotalAmount = totalAmount;

  public void ShowInfo()
  {
    System.Console.WriteLine("\n---------- TRANSAKSI #" + TransactionId + " ----------");
    System.Console.WriteLine("Metode Pembayaran: " + PaymentMethod);

    foreach (Cart item in Items)
    {
      System.Console.WriteLine($"- {item.Name} (x{item.Quantity}) {item.Price:C0}");
    }

    System.Console.WriteLine($"Total Belanja: {TotalAmount:C0}");
  }
}