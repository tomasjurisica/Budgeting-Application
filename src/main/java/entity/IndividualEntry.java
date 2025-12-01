package entity;

import java.time.LocalDate;

/**
 * Represents an individual entry for a user.
 * Extends Entry so we can reuse all the existing fields/logic.
 */
public class IndividualEntry extends Entry {

    public IndividualEntry(String name,
                           String category,
                           float amount,
                           LocalDate date) {

        super(name, category, amount, date);
    }
}
