package use_case.individual_entry.add_entry;

import java.time.LocalDate;

public class AddIndividualEntryInputData {
    private final String username;
    private final String name;
    private final String category;
    private final float amount;
    private final LocalDate date;

    public AddIndividualEntryInputData(String username,
                                       String name,
                                       String category,
                                       float amount,
                                       LocalDate date) {
        this.username = username;
        this.name = name;
        this.category = category;
        this.amount = amount;
        this.date = date;
    }

    public String getUsername() {
        return username;
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
