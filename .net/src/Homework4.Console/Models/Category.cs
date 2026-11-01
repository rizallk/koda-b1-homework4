namespace Homework4.Console.Models;

public class Category(string categoryId, string name)
{
  public string CategoryId { get; private set; } = categoryId;
  public string Name { get; private set; } = name;

  public void SetName(string name)
  {
    Name = name;
  }

  public void SetCategoryId(string categoryId)
  {
    CategoryId = categoryId;
  }
}