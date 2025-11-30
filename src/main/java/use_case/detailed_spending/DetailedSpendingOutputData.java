package use_case.detailed_spending;
import java.time.LocalDate;
import java.util.List;

public class DetailedSpendingOutputData {
    private final String categoryName;
    private final List<Purchase> purchases;

    public static class Purchase {
        private final String purchaseName;
        private final LocalDate date;
        private final float amount;
        public Purchase(String purchaseName,LocalDate date, float amount) {
            this.purchaseName = purchaseName;
            this.date = date;
            this.amount = amount;
        }
        public String getPurchaseName() {
            return purchaseName;
        }
        public LocalDate getDate() {
            return date;
        }
        public float getAmount() {
            return amount;
        }

    }

    public DetailedSpendingOutputData(String categoryName,  List<Purchase> purchases) {
        this.categoryName = categoryName;
        this.purchases = purchases;
    }
    public String getCategoryName() {
        return categoryName;
    }
    public List<Purchase> getPurchases() {
        return purchases;
    }
}
