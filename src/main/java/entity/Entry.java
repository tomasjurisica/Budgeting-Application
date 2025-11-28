package entity;

import java.time.LocalDate;

public class Entry {
    private String name;
    private String category = "N/A";
    private float amount;
    private LocalDate date = LocalDate.now();

    /**
     * @param name Name of the entry.
     * @param category Category of the entry.
     * @param amount Amount of the entry. Negative for expenses, positive for income.
     * @param date The date of the entry.
     */
    public Entry(String name, String category, float amount, LocalDate date) {
        this.name = name;
        this.category = category;
        this.amount = amount;
        this.date = date;
    }

    /**
     * Create an entry without category or date. Category defaults to N/A, date defaults to current day's date.
     * @param name Name of the entry
     * @param amount Amount of the entry. Negative for expenses, positive for income
     */
    public Entry(String name, float amount) {
        this.name = name;
        this.amount = amount;
        this.category = "N/A";
        this.date = LocalDate.now();
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public float getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }
}
