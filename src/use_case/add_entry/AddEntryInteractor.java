package use_case.add_entry;

import BudgetingObjects.Entry;
import BudgetingObjects.IndividualEntry;

public class AddEntryInteractor implements AddEntryInputBoundary {

    private final AddEntryUserDataAccessInterface userDataAccess;
    private final AddEntryOutputBoundary presenter;

    public AddEntryInteractor(AddEntryUserDataAccessInterface userDataAccess,
                              AddEntryOutputBoundary presenter) {
        this.userDataAccess = userDataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(AddEntryInputData inputData) {
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

        Entry entry = new IndividualEntry(name, category, amount, inputData.getDate());
        userDataAccess.addEntry(entry);

        AddEntryOutputData outputData = new AddEntryOutputData(
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
