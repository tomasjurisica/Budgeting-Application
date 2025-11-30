package interface_adapter.select_user;

import entity.Household;
import entity.User;
import interface_adapter.ViewManagerModel;
import interface_adapter.home_page.HomePageViewModel;
import interface_adapter.household_dashboard.HouseholdDashboardViewModel;
import use_case.select_user.*;
import use_case.select_user.SelectUserOutputData;
import view.HomePageView;

public class SelectUserPresenter implements SelectUserOutputBoundary {

    private final HomePageViewModel homePageViewModel;
    private final ViewManagerModel viewManagerModel;
    private final HouseholdDashboardViewModel householdDashboardViewModel;
    private final HomePageView homePageView;

    public SelectUserPresenter(HomePageViewModel homePageViewModel,
                               ViewManagerModel viewManagerModel,
                               HouseholdDashboardViewModel householdDashboardViewModel,
                               HomePageView homePageView) {
        this.homePageViewModel = homePageViewModel;
        this.viewManagerModel = viewManagerModel;
        this.householdDashboardViewModel = householdDashboardViewModel;
        this.homePageView = homePageView;
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
        homePageViewModel.setCurrentUser(selectedUser);

        // Update the HomePageView with the household
        homePageView.setHousehold();

        // Switch to the home page view
        viewManagerModel.setState(homePageView.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
