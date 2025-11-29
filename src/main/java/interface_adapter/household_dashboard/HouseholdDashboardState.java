package interface_adapter.household_dashboard;

import entity.Household;
import entity.User;
import java.util.ArrayList;
import java.util.List;

public class HouseholdDashboardState {

    private String username;
    private Household household;
    private final List<User> users = new ArrayList<>();
    private String addUserError;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Household getHousehold() {
        return household;
    }

    public void setHousehold(Household household) {
        this.household = household;
        // Populate users list from household when household is set
        if (household != null) {
            users.clear();
            users.addAll(household.getUsers());
        } else {
            users.clear();
        }
    }

    public String getHouseholdID() {
        return household != null ? household.getHouseholdID() : "";
    }

    public List<User> getUsers() {
        return users;
    }

    public String getAddUserError() {
        return addUserError;
    }

    public void setAddUserError(String addUserError) {
        this.addUserError = addUserError;
    }

    /**
     * Refreshes the users list from the household.
     * Useful if the household has been updated outside of this state.
     */
    public void refreshUsersFromHousehold() {
        if (household != null) {
            users.clear();
            users.addAll(household.getUsers());
        } else {
            users.clear();
        }
    }
}
