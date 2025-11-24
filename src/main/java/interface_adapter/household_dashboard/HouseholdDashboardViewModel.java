package interface_adapter.household_dashboard;

import entity.User;
import use_case.add_user.AddUserInputBoundary;
import use_case.add_user.AddUserInputData;
import use_case.add_user.AddUserOutputBoundary;
import use_case.add_user.AddUserOutputData;
import interface_adapter.ViewModel;

import java.util.List;

/**
 * ViewModel for the logged-in household dashboard.
 */
public class HouseholdDashboardViewModel extends ViewModel<HouseholdDashboardState>
        implements AddUserOutputBoundary {

    private AddUserInputBoundary addUserInteractor;

    public HouseholdDashboardViewModel() {
        super("logged in");
        setState(new HouseholdDashboardState());
    }

    // Inject interactor after login
    public void setAddUserInteractor(AddUserInputBoundary interactor) {
        this.addUserInteractor = interactor;
    }

    // Called by View when user clicks "Add Roommate"
    public void addUser(String name) {
        if (name == null || name.trim().isEmpty()) return;
        AddUserInputData inputData = new AddUserInputData(name.trim());
        addUserInteractor.addUser(inputData); // forwards to interactor
    }

    @Override
    // Called by interactor to update state and refresh UI
    public void present(AddUserOutputData outputData) {
        List<User> users = getState().getUsers();
        users.add(outputData.getUser()); // add new roommate
        firePropertyChange(); // notify the view
    }

    public String getViewName() {
        return super.getViewName();
    }

}
