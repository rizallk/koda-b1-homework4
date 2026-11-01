namespace Homework4.Console.Interface;

public interface IPaymentMethod
{
  void Pay(long amount, long total);
  bool GetIsSuccess();
  long GetChange();
  string GetPaymentDetails();
}