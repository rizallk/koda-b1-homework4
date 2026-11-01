namespace Homework4.Console.Utils;

public class Input
{
  public static int Int()
  {
    try
    {
      int? input = Convert.ToInt32(System.Console.ReadLine());
      return input ?? -1;
    }
    catch (Exception)
    {
      return -1;
    }
  }

  public static string String()
  {
    try
    {
      string? input = System.Console.ReadLine();
      return input ?? "";
    }
    catch (Exception)
    {
      return "";
    }
  }
}