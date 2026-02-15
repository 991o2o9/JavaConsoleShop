import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
    public void addPurchase(Purchase purchase){
        purchases.add(purchase);
        FileManager.appendPurchase(purchase);
    }
}
