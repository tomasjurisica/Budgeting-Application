package interface_adapter.individual_entry;

import use_case.individual_entry.add_entry.AddIndividualEntryInputBoundary;
import use_case.individual_entry.add_entry.AddIndividualEntryInputData;

import java.time.LocalDate;

public class IndividualEntryController {

    private final AddIndividualEntryInputBoundary addIndividualEntryUseCase;

    public IndividualEntryController(AddIndividualEntryInputBoundary addIndividualEntryUseCase) {
        this.addIndividualEntryUseCase = addIndividualEntryUseCase;
    }

    /**
     * Called by the view when the user submits a new individual entry.
     */
    public void addEntry(String username,
                         String name,
                         String category,
                         float amount,
                         LocalDate date) {

        AddIndividualEntryInputData inputData =
                new AddIndividualEntryInputData(username, name, category, amount, date);

        addIndividualEntryUseCase.execute(inputData);
    }
}
