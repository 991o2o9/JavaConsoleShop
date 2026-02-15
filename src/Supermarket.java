import java.util.ArrayList;

class Supermarket{
    private ArrayList<Product> products;

    public Supermarket() {
        products = FileManager.loadProducts();
    }

    public Product findProductByName(String name) {
        for (Product prod : products) {
            if (prod.getName().equalsIgnoreCase(name)) {
                return prod;
            }
        }
        return null;
    }

    public void addProduct(Product product){
        if (product == null){
            return;
        }
        Product foundProduct = findProductByName(product.getName());
        if (foundProduct != null){
            foundProduct.setQuantity(foundProduct.getQuantity() + product.getQuantity());
        }
        else {
            products.add(product);
        }
    }

    public void showProducts(){
        if (products.isEmpty()){
            System.out.println("No Products");
        }
        for (int i = 0;i<products.size();i++){
            System.out.println(i+1+ ". " + products.get(i));
        }
    }

    public boolean buyProduct(User user,String name, int amount){
        if(amount<=0){
            System.out.println("Error: Amount must be positive");
            return false;
        }
        Product foundProduct = this.findProductByName(name);
        if (foundProduct == null){
            System.out.println("Error: Product not found");
            return false;
        }
        if (amount > foundProduct.getQuantity()){
            System.out.println("We don't have this amount of " + name);
            System.out.println("Available: " + foundProduct.getQuantity() + ", requested: " + amount);
            return false;
        }

        double totalPrice = foundProduct.getPrice() * amount;

        if (!user.canAfford(totalPrice)){
            System.out.println("Error: Not enough money on balance");
            return false;
        }

        user.deduct(totalPrice);
        foundProduct.setQuantity(foundProduct.getQuantity() - amount);

        System.out.println("You bought: "+ foundProduct.getName() + " in " + amount + " quantity");
        return true;
    }

    public void getTotalValue(){
        double sumOfProducts = 0;
        for (Product prod : products){
            sumOfProducts += prod.getPrice() * prod.getQuantity();
        }
        System.out.println("Total price of all products is: $" + sumOfProducts);
    }
    public ArrayList<Product> getProducts(){
        return products;
    }
}