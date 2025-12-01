package interface_adapter.household_dashboard;

import interface_adapter.ViewModel;
import use_case.add_user.AddUserInputBoundary;
import use_case.add_user.AddUserInputData;
import use_case.add_user.AddUserOutputBoundary;
import use_case.add_user.AddUserOutputData;

public class HouseholdDashboardViewModel extends ViewModel<HouseholdDashboardState>
        implements AddUserOutputBoundary {

    private AddUserInputBoundary addUserInteractor;

    public HouseholdDashboardViewModel() {
        super("logged in");
        setState(new HouseholdDashboardState());
    }

    public void setAddUserInteractor(AddUserInputBoundary interactor) {
        this.addUserInteractor = interactor;
    }

    // Called by the view when the user clicks "Add Roommate"
    public void addUser(String name) {
        if (name == null || name.trim().isEmpty() || addUserInteractor == null) return;
        addUserInteractor.addUser(new AddUserInputData(name.trim()));
    }

    // Called by the interactor to update the state/UI
    @Override
    public void present(AddUserOutputData outputData) {
        getState().setAddUserError(null);
        // FIX: Add the string name to the state list, not the User object
        getState().getUserNames().add(outputData.user().getName());
        firePropertyChange();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        getState().setAddUserError(errorMessage);
        firePropertyChange();
    }
}