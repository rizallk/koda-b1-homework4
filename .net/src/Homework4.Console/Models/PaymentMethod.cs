using Homework4.Console.Interface;

namespace Homework4.Console.Models;

public abstract class PaymentMethod : IPaymentMethod
{
  public abstract void Pay(long amount, long total);
  public abstract string GetPaymentDetails();
  public abstract long GetChange();
  public abstract bool GetIsSuccess();
  public abstract string GetName();
}