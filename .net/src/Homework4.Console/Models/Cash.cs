namespace Homework4.Console.Models;

public class Cash(string name) : PaymentMethod
{
  public string Name { get; } = name;
  public long Change { get; private set; } = 0;
  private bool IsSuccess;

  public override void Pay(long amount, long total)
  {
    if (amount < total)
    {
      System.Console.WriteLine("\nSaldo Anda tidak cukup.\n");
      IsSuccess = false;
    }
    else
    {
      if (amount > total)
      {
        Change = amount - total;
      }
      IsSuccess = true;
    }
  }

  public override string GetPaymentDetails()
  {
    return $"Metode Pembayaran Tunai {Name}";
  }

  public override string GetName()
  {
    return Name;
  }

  public override bool GetIsSuccess()
  {
    return IsSuccess;
  }

  public override long GetChange()
  {
    return Change;
  }
}