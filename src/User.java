public class User {
    private String username;
    private String password;
    private double balance;

    public User(String username,String password,double balance) {
        this.username = username;
        this.password = password;
        this.balance = balance;
    }

    public boolean checkPassword(String enteredPass){
        return password.equals(enteredPass);
    }

    public boolean canAfford(double amount){
        return balance >= amount;
    }

    public void deduct(double amount){
        balance -= amount;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword(){
        return password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString(){
        return "Name: " + username + ", Balance: " + balance;
    }
}
