package use_case;

public class DetailedSpendingInputData {
    private final String userName;
    private final String categoryName;
    private final int month;
    private final int year;

    public DetailedSpendingInputData(String userName,
                                     String categoryName,
                                     int month,
                                     int year) {
        this.userName = userName;
        this.categoryName = categoryName;
        this.month = month;
        this.year = year;
    }

    public String getCategoryName() { return categoryName; }

    public String getUserName() { return userName; }

    public int getMonth() { return month; }

    public int getYear() { return year; }
}
