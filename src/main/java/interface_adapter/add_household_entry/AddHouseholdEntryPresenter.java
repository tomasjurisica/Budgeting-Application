package interface_adapter.add_household_entry;

import interface_adapter.ViewManagerModel;
import interface_adapter.home_page.HomePageViewModel;
import use_case.add_household_entry.AddHouseholdEntryOutputBoundary;
import use_case.add_household_entry.AddHouseholdEntryOutputData;

public class AddHouseholdEntryPresenter implements AddHouseholdEntryOutputBoundary {
    private final AddHouseholdEntryViewModel addEntryViewModel;
    private final HomePageViewModel homePageViewModel;
    private final ViewManagerModel viewManagerModel;

    public AddHouseholdEntryPresenter(AddHouseholdEntryViewModel addEntryViewModel,
                                      HomePageViewModel homePageViewModel,
                                      ViewManagerModel viewManagerModel) {
        this.addEntryViewModel = addEntryViewModel;
        this.homePageViewModel = homePageViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(AddHouseholdEntryOutputData response) {
        final AddHouseholdEntryState addEntryState = addEntryViewModel.getState();

        // Display all usernames (?)
        addEntryState.setAllUserNames(response.getUserNames());
        addEntryState.setHasError(false);
        addEntryState.setErrorMessage(null);
        addEntryViewModel.firePropertyChange();

        // Refresh home page data to show the new entry
        if (homePageViewModel != null) {
            homePageViewModel.loadCurrentUser();
        }

        // Navigate back to home page
        if (viewManagerModel != null) {
            viewManagerModel.setState("home page");
            viewManagerModel.firePropertyChange();
        }
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final AddHouseholdEntryState addEntryState = addEntryViewModel.getState();

        addEntryState.setHasError(true);
        addEntryState.setErrorMessage(errorMessage);
        addEntryViewModel.firePropertyChange();
    }
}
