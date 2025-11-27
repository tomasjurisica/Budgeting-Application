package interface_adapter.add_entry;

import use_case.add_entry.AddEntryOutputBoundary;
import use_case.add_entry.AddEntryOutputData;

public class AddEntryPresenter implements AddEntryOutputBoundary{
    private final AddEntryViewModel addEntryViewModel;

    public AddEntryPresenter(AddEntryViewModel addEntryViewModel) {
        this.addEntryViewModel = addEntryViewModel;
    }

    @Override
    public void prepareSuccessView(AddEntryOutputData response) {
        final AddEntryState addEntryState = addEntryViewModel.getState();

        // Display all usernames (?)
        addEntryState.setAllUserNames(response.getUserNames());
        addEntryState.setHasError(false);
        addEntryState.setErrorMessage(null);
        addEntryViewModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final AddEntryState addEntryState = addEntryViewModel.getState();

        addEntryState.setHasError(true);
        addEntryState.setErrorMessage(errorMessage);
        addEntryViewModel.firePropertyChange();
    }
}
