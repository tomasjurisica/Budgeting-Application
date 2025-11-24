package interface_adapter.household_dashboard;

import interface_adapter.ViewModel;
import entity.User;
import use_case.add_user.*;

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
        getState().getUsers().add(outputData.getUser());
        firePropertyChange();
    }
}
