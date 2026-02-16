import java.util.*;

public class HistoryService {
    private List<Purchase> purchases;

    public HistoryService(){
        purchases = FileManager.loadPurchases();
    }

    public List<Purchase> getAllPurchasesSorted(){
        List<Purchase> sorted = new ArrayList<>(purchases);
        Collections.sort(sorted, Comparator.comparing(Purchase::getTimestamp));
        return sorted;
    }

    public List<Purchase> getUserPurchases(String username){
        return purchases.stream().filter(p -> p.getUsername().equalsIgnoreCase(username)).sorted(Comparator.comparing(Purchase::getTimestamp)).toList();
    }

    public double getTotalRevenue(){
        return purchases.stream().mapToDouble(Purchase::getTotalPrice).sum();
    }

    public double getAveragePurchaseValue(){
        if (purchases.isEmpty()) return 0;

        return getTotalRevenue() / purchases.size();
    }

    public String getTopCustomer(){
        if (purchases.isEmpty()){
            return "No data";
        }
        Map<String, Double> totals = new HashMap<>();

        for (Purchase p: purchases){
            String user = p.getUsername();
            double price = p.getTotalPrice();

            Double current = totals.get(user);
            if (current == null){
                totals.put(user,price);
            }
            else{
                totals.put(user, current + price);
            }
        }

        String topUser = null;
        double maxSum = -1;

        for (Map.Entry<String, Double> entry : totals.entrySet()){
            if (entry.getValue() > maxSum){
                maxSum = entry.getValue();
                topUser = entry.getKey();
            }
        }
        return topUser != null ? topUser : "No data";
    }

    public void addPurchase(Purchase purchase){
        purchases.add(purchase);
        FileManager.appendPurchase(purchase);
    }
}
