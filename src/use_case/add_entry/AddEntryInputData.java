package use_case.add_entry;

import java.time.LocalDate;

public class AddEntryInputData {
    private final String name;
    private final String category;
    private final float amount;
    private final LocalDate date;

    public AddEntryInputData(String name, String category, float amount, LocalDate date) {
        this.name = name;
        this.category = category;
        this.amount = amount;
        this.date = date;
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
