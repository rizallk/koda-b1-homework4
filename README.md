# Homework 4 - Java & .NET
## Struktur Kelas Utama menggunakan Class Diagram (Mermaid)
```mermaid
classDiagram
  class IMenu {
    <<interface>>
    + showInfo() string
  }
  class IPaymentMethod {
    <<interface>>
    + pay() void
    + getIsSuccess() boolean
    + getChange() long
    + getPaymentDetails() string
  }
  class Category {
    -categoryId : int
    -name : string
    +setCategoryId() void
    +getCategoryId() string
    +setName() void
    +getName() string
  }
  class Cart {
    -cartId : int
    -name : string
    -price : int
    -quantity : int
    +getCartid() int
    +setCardId() void
    +getName() string
    +setName() void
    +getPrice() int
    +setPrice() void
    +getQuantity() int
    +setQuantity() void
    +showInfo() string
  }
  class PaymentMethod {
    +pay() void
    +getPaymentDetails() string
    +getName() string
  }
  class Cash {
    -name : string
    -change : long
    -isSuccess : boolean
    +pay() void
    +getPaymentDetails() string
    +getName() string
    +getChange() long
    +getIsSuccess() boolean
  }
  class Cashless {
    -name : string
    -change : long
    -isSuccess : boolean
    +pay() void
    +getPaymentDetails() string
    +getName() string
    +getChange() long
    +getIsSuccess() boolean
  }
  class CheckoutHistory {
    -transactionId : int
    -items : List~Cart~
    -paymentMethod : string
    -totalAmount : long
    +showInfo() void
  }
  class Menu {
    -menuId : int
    -name : string
    -price : int
    -stock : stock
    +getMenuId() int
    +getName() string
    +getPrice() int
    +getStock() int
    +decreaseStock() void
    +showInfo() string
  }

  Menu --> IMenu
  Menu --> Category
  PaymentMethod --> IPaymentMethod
  Cash --> PaymentMethod
  Cashless --> PaymentMethod
```

## Konsep OOP yang Digunakan
1. Ecapsulation
2. Inheritance
3. Abstraction
4. Polymorphism
5. Interface

## Cara Menjalankan Aplikasi
### Java :
1. Masuk ke folder homework-java
2. Buka CMD (Command Prompt)
3. Jalankan perintah: ```mvn compile && java -cp target/classes com.homework.java.App```

### .NET :
1. Masuk ke folder .net
2. Buka CMD (Command Prompt)
3. Jalankan perintah: ```dotnet run --project src/Homework4.Console```

## Screenshot Hasil Program
<p align="center">
<img src="hw4-1.png" width="550">
</p>
<p align="center">
<img src="hw4-2.png" width="550">
</p>