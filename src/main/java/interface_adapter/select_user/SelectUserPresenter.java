package interface_adapter.select_user;

import entity.Household;
import entity.User;
import interface_adapter.ViewManagerModel;
import interface_adapter.home_display.HomeDisplayViewModel;
import interface_adapter.household_dashboard.HouseholdDashboardViewModel;
import use_case.select_user.*;
import use_case.select_user.SelectUserOutputData;
import view.HomeDisplayView;

public class SelectUserPresenter implements SelectUserOutputBoundary {

    private final HomeDisplayViewModel homeDisplayViewModel;
    private final ViewManagerModel viewManagerModel;
    private final HouseholdDashboardViewModel householdDashboardViewModel;
    private final HomeDisplayView homeDisplayView;

    public SelectUserPresenter(HomeDisplayViewModel homeDisplayViewModel,
                               ViewManagerModel viewManagerModel,
                               HouseholdDashboardViewModel householdDashboardViewModel,
                               HomeDisplayView homeDisplayView) {
        this.homeDisplayViewModel = homeDisplayViewModel;
        this.viewManagerModel = viewManagerModel;
        this.householdDashboardViewModel = householdDashboardViewModel;
        this.homeDisplayView = homeDisplayView;
    }

    @Override
    public void execute(SelectUserInputData inputData) {

    }

    @Override
    public void present(SelectUserOutputData outputData) {
        // Get the household from the dashboard view model
        Household household = householdDashboardViewModel.getState().getHousehold();

        if (household == null) {
            return; // Can't proceed without household
        }

        // Find the user by name
        User selectedUser = null;
        for (User user : household.getUsers()) {
            if (user.getName().equals(outputData.roommateName())) {
                selectedUser = user;
                break;
            }
        }

        if (selectedUser == null) {
            return; // User not found
        }

        // Store the current user in HomePageViewModel
        homeDisplayViewModel.setCurrentUser(selectedUser);

        // Update the HomePageView with the household
        homeDisplayView.setHousehold();

        // Switch to the home page view
        viewManagerModel.setState(homeDisplayView.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
