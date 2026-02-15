import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class FileManager {
    static final String userFilePath = "users.txt";
    static final String productsFilePath = "products.txt";
    static final String historyFilePath = "history.txt";

    public static ArrayList<User> loadUsers(){
        ArrayList<User> users = new ArrayList<>();
        try(Scanner scanner = new Scanner(new File(userFilePath))){
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] parts = line.split(";");
                String username = parts[0];
                String password = parts[1];
                double balance = Double.parseDouble(parts[2]);
                users.add(new User(username,password,balance));
            }
        }
        catch (FileNotFoundException e){
            System.out.println("File not found");
        }
        return users;
    }

    public static void saveUsers(ArrayList<User> users){
        try(PrintWriter writer = new PrintWriter(userFilePath)){
            for (User u : users){
                writer.println(u.getUsername() + ";" + u.getPassword() + ";" + u.getBalance());
            }
        }
        catch (FileNotFoundException e){
            System.out.println("File not found");
        }
    }

    public static ArrayList<Product> loadProducts(){
        ArrayList<Product> products = new ArrayList<>();
        try(Scanner scanner = new Scanner(new File(productsFilePath))){
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] part = line.split(";");
                String name = part[0];
                double price = Double.parseDouble(part[1]);
                int quantity = Integer.parseInt(part[2]);
                products.add(new Product(name,price,quantity));
            }
        }
        catch (FileNotFoundException e){
            System.out.println("File not found");
        }
        return products;
    }

    public static void saveProducts(ArrayList<Product> products){
        try(PrintWriter writer = new PrintWriter(productsFilePath)){
            for (Product p : products){
                writer.println(p.getName() + ";" + p.getPrice() + ";" + p.getQuantity());
            }
        }
        catch (FileNotFoundException e){
            System.out.println("File not found");
        }
    }

    public static void appendPurchase(Purchase purchase){
        try(PrintWriter  writer = new PrintWriter(new FileWriter(historyFilePath, true))){
            writer.println(purchase.getUsername() + ";" +
                    purchase.getProductName() + ";" +
                    purchase.getQuantity() + ";" +
                    purchase.getTotalPrice() + ";" +
                    purchase.getTimestamp());
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        catch (IOException e){
            System.out.println("Something went wrong");
        }
    }

    public static List<Purchase> loadPurchases(){
        List<Purchase> purchases = new ArrayList<>();
        try(Scanner scanner = new Scanner(new File(historyFilePath))){
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] parts = line.split(";");
                String username = parts[0];
                String productName = parts[1];
                int quantity = Integer.parseInt(parts[2]);
                double totalPrice = Double.parseDouble(parts[3]);
                LocalDateTime timestamp = LocalDateTime.parse(parts[4]);
                purchases.add(new Purchase(username,productName,quantity,totalPrice,timestamp));
            }
        }
        catch (FileNotFoundException e){
            System.out.println("File not found");
        }
        catch (Exception e){
            System.out.println("Something went wrong with purchases");
        }
        return purchases;
    }
}
