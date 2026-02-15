import java.time.LocalDateTime;

class Purchase {
    private String username;
    private String productName;
    private int quantity;
    private double totalPrice;
    private LocalDateTime timestamp;

    public Purchase(String username,String productName, int quantity, double totalPrice,LocalDateTime timestamp){
        this.username = username;
        this.productName = productName;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.timestamp = timestamp;
    }

    public String getUsername() {
        return username;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return String.format("🛒 %-10s | %-15s | x%d | %.2f $.",
                username, productName, quantity, totalPrice);
    }
}
