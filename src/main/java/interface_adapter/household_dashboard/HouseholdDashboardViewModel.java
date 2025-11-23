package interface_adapter.household_dashboard;

import interface_adapter.ViewModel;

/**
 * The View Model for the Logged In View.
 */
public class HouseholdDashboardViewModel extends ViewModel<HouseholdDashboardState> {

    public HouseholdDashboardViewModel() {
        super("logged in");
        setState(new HouseholdDashboardState());
    }

}
