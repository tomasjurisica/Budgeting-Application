package use_case.add_entry;

import java.time.LocalDate;

public class AddEntryOutputData {

    private final String name;
    private final String category;
    private final float amount;
    private final LocalDate date;
    private final String message;
    private final boolean useCaseFailed;

    public AddEntryOutputData(String name, String category, float amount,
                              LocalDate date, String message, boolean useCaseFailed) {
        this.name = name;
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.message = message;
        this.useCaseFailed = useCaseFailed;
    }

    public String getName() { return name; }

    public String getCategory() { return category; }

    public float getAmount() { return amount; }

    public LocalDate getDate() { return date; }

    public String getMessage() { return message; }

    public boolean isUseCaseFailed() { return useCaseFailed; }
}
