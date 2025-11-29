package interface_adapter.add_household_entry;

import use_case.add_household_entry.AddHouseholdEntryOutputBoundary;
import use_case.add_household_entry.AddHouseholdEntryOutputData;

public class AddHouseholdEntryPresenter implements AddHouseholdEntryOutputBoundary {
    private final AddHouseholdEntryViewModel addEntryViewModel;

    public AddHouseholdEntryPresenter(AddHouseholdEntryViewModel addEntryViewModel) {
        this.addEntryViewModel = addEntryViewModel;
    }

    @Override
    public void prepareSuccessView(AddHouseholdEntryOutputData response) {
        final AddHouseholdEntryState addEntryState = addEntryViewModel.getState();

        // Display all usernames (?)
        addEntryState.setAllUserNames(response.getUserNames());
        addEntryState.setHasError(false);
        addEntryState.setErrorMessage(null);
        addEntryViewModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final AddHouseholdEntryState addEntryState = addEntryViewModel.getState();

        addEntryState.setHasError(true);
        addEntryState.setErrorMessage(errorMessage);
        addEntryViewModel.firePropertyChange();
    }
}
