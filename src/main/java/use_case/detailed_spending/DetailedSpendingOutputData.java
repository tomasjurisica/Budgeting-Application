package use_case.detailed_spending;

import java.time.LocalDate;
import java.util.List;

public class DetailedSpendingOutputData {
    private final String categoryName;
    private final List<Purchase> purchases;

    /**
     * Represents an individual purchase belonging to a specific category.
     *
     * @param purchaseName the name of the purchase
     * @param date the date of the purchase
     * @param amount the amount spent
     */

    public record Purchase(String purchaseName, LocalDate date, float amount) {

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
