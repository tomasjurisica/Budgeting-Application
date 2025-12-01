package interface_adapter.detailed_spending;

// import use_case.detailed_spending.DetailedSpendingOutputData;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DetailedSpendingState {
    private String userName = "";
    private String categoryName = "";
    private List<PurchaseUIModel> purchases = new ArrayList<>();
    private String errorMessage;
    private int month;
    private int year;
    private boolean hasError;

    public String getUsername() {
        return userName;
    }

    public void setUsername(String userName) {
        this.userName = userName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<PurchaseUIModel> getPurchases() { return purchases; }

    public void setPurchases(List<PurchaseUIModel> purchases) {
        this.purchases = purchases;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public static class PurchaseUIModel {
        private final String name;
        private final LocalDate date;
        private final float amount;

        public PurchaseUIModel(String name, LocalDate date, float amount) {
            this.name = name;
            this.date = date;
            this.amount = amount;
        }

        public String getName() { return name; }
        public LocalDate getDate() { return date; }
        public float getAmount() { return amount; }
    }
}
