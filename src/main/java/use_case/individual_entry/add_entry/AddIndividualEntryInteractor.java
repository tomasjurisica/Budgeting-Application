package use_case.individual_entry.add_entry;

import entity.IndividualEntry;
import use_case.individual_entry.IndividualEntryDataAccessInterface;

public class AddIndividualEntryInteractor implements AddIndividualEntryInputBoundary {

    private final IndividualEntryDataAccessInterface individualEntryDataAccess;
    private final AddIndividualEntryOutputBoundary presenter;

    public AddIndividualEntryInteractor(IndividualEntryDataAccessInterface individualEntryDataAccess,
                                        AddIndividualEntryOutputBoundary presenter) {
        this.individualEntryDataAccess = individualEntryDataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(AddIndividualEntryInputData inputData) {
        String username = inputData.getUsername();
        String name = inputData.getName();
        String category = inputData.getCategory();
        float amount = inputData.getAmount();

        if (name == null || name.trim().isEmpty()) {
            presenter.prepareFailView("Entry name cannot be empty.");
            return;
        }

        if (Float.isNaN(amount)) {
            presenter.prepareFailView("Amount is invalid.");
            return;
        }

        if (category == null || category.trim().isEmpty()) {
            category = "N/A";
        }

        IndividualEntry entry = new IndividualEntry(
                name,
                category,
                amount,
                inputData.getDate()
        );

        individualEntryDataAccess.saveEntry(username, entry);

        AddIndividualEntryOutputData outputData = new AddIndividualEntryOutputData(
                entry.getName(),
                entry.getCategory(),
                entry.getAmount(),
                entry.getDate(),
                "Entry added successfully.",
                false
        );

        presenter.prepareSuccessView(outputData);
    }
}
