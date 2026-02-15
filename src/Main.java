import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
  public static void main(String[] args){
    String ADMIN = "admin123";
    Scanner scanner = new Scanner(System.in);
    boolean isManager = false;
    User currentUser = null;
    boolean LoggedIn = false;
    String choice = "";

    UserManager userManager = new UserManager();

    Supermarket supermarket = new Supermarket();

    HistoryService historyService = new HistoryService();

    FileManager.saveProducts(supermarket.getProducts());

    System.out.println("--------------------");
    System.out.println("Welcome to 7-Eleven");
    System.out.println("--------------------");

    while (!LoggedIn){
      System.out.println("--------------------");
      System.out.println("LOGIN/REGISTER");
      System.out.println("--------------------");
      System.out.println("(1) - Login");
      System.out.println("(2) - Create account");
      System.out.print("\nEnter your choice: ");
      choice = scanner.nextLine();

      switch (choice){
        case "1" -> {
          System.out.println("\n-----Welcome back!------");
          System.out.print("Enter username: ");
          String username = scanner.nextLine();
          System.out.print("Enter password: ");
          String password = scanner.nextLine();
          currentUser = userManager.login(username,password);
          if (currentUser != null){
            isManager = password.equals(ADMIN);
            LoggedIn = true;
          } else{
            System.out.println("Error: invalid password or username");
          }
        }
        case "2" ->{
          System.out.println("\n-----SIGN UP-----");
          System.out.print("Enter username: ");
          String username = scanner.nextLine();
          System.out.print("Enter password: ");
          String password = scanner.nextLine();
          System.out.print("Enter balance: ");
          double balance = scanner.nextDouble();
          scanner.nextLine();
          User newUser = new User(username,password,balance);
          userManager.addUser(newUser);
          FileManager.saveUsers(userManager.getUsers());
          currentUser = userManager.login(username,password);
          if (currentUser != null){
            LoggedIn = true;
          }
        }
        default -> System.out.println("Unsupported answer. Try again");
      }
    }

    while (LoggedIn){
      if (isManager){
        System.out.println("\n---------ADMIN PANEL---------");
        System.out.println("Name: " + currentUser.getUsername());
        System.out.println("-----------------------------");
        System.out.println("(1) - Show All Products");
        System.out.println("(2) - Add product");
        System.out.println("(3) - Get total price of all products");
        System.out.println("(4) - Get all purchase history");
        System.out.println("(0) - Logout");
      }
      else {
        System.out.println("\n-----------------------------");
        System.out.println("Name: " + currentUser.getUsername() + ", Balance: " + currentUser.getBalance());
        System.out.println("-----------------------------");
        System.out.println("(1) - Show All Products");
        System.out.println("(2) - Buy Product");
        System.out.println("(3) - Get my purchase history");
        System.out.println("(0) - Logout");
      }
      System.out.println("_______________________________");
      System.out.print("\nEnter you choice: ");
      choice = scanner.nextLine();
      System.out.println();
      if (isManager){
        switch (choice){
          case "1" -> supermarket.showProducts();
          case "2" -> {
            System.out.print("Enter product name: ");
            String prTitle = scanner.nextLine();
            System.out.print("Enter " + prTitle + " price: ");
            double prPrice = scanner.nextDouble();
            System.out.print("Enter " + prTitle + " amount to add: ");
            int prQuantity = scanner.nextInt();
            scanner.nextLine();
            supermarket.addProduct(new Product(prTitle,prPrice,prQuantity));
            FileManager.saveProducts(supermarket.getProducts());
          }
          case "3" -> supermarket.getTotalValue();
          case "4" ->{
            for (Purchase purchase : historyService.getAllPurchasesSorted()){
              System.out.println(purchase);
            }
          }
          case "0"->{
            System.out.println("Thank you!");
            LoggedIn = false;
          }
          default -> System.out.println("Unsupported choice, try again");
          }
        }
      else{
        switch (choice){
          case "1" -> supermarket.showProducts();
          case "2" -> {
            System.out.print("Enter product you want to buy: ");
            String prTitle = scanner.nextLine();
            System.out.print("Enter the amount: ");
            int prAmount = scanner.nextInt();
            scanner.nextLine();
            boolean success = supermarket.buyProduct(currentUser,prTitle,prAmount);
            Product wantedProduct = supermarket.findProductByName(prTitle);
            if (success){
              FileManager.saveUsers(userManager.getUsers());
              FileManager.saveProducts(supermarket.getProducts());
              Purchase purchase = new Purchase(currentUser.getUsername(), prTitle,prAmount,wantedProduct.getPrice() * prAmount, LocalDateTime.now());
              historyService.addPurchase(purchase);
            }
          }
          case "3" ->{
            for (Purchase purchase : historyService.getUserPurchases(currentUser.getUsername())){
              System.out.println(purchase);
            }
          }
          case "0" ->{
            System.out.println("Thank you!");
            LoggedIn = false;
          }
          default -> System.out.println("Unsupported choice, try again");
        }
      }
    }
  }
}