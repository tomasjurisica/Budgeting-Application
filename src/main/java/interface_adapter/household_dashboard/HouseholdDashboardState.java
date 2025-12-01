package interface_adapter.household_dashboard;

import entity.Household;
import entity.User;
import java.util.ArrayList;
import java.util.List;

public class HouseholdDashboardState {

    private String username;
    private Household household;
    // FIX: Store Strings, not Entities. The View should not know about 'User'.
    private final List<String> userNames = new ArrayList<>();
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
        // FIX: Populate the list of names from the household entity
        if (household != null) {
            userNames.clear();
            for (User u : household.getUsers()) {
                userNames.add(u.getName());
            }
        } else {
            userNames.clear();
        }
    }

    public String getHouseholdID() {
        return household != null ? household.getHouseholdID() : "";
    }

    // FIX: Return Strings
    public List<String> getUserNames() {
        return userNames;
    }

    public String getAddUserError() {
        return addUserError;
    }

    public void setAddUserError(String addUserError) {
        this.addUserError = addUserError;
    }

}