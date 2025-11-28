package BudgetingObjects;

import java.time.LocalDate;

/**
 * Represents an individual (non-shared) budget entry for a single user.
 * This is a thin subclass of Entry so that we can distinguish individual
 * and shared entries by type if we ever need to.
 */
public class IndividualEntry extends Entry {

    public IndividualEntry(String name, String category, float amount, LocalDate date) {
        super(name, category, amount, date);
    }

    public IndividualEntry(String name, float amount) {
        super(name, amount);
    }
}
