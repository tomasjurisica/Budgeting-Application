package interface_adapter.household_dashboard;

import entity.Household;
import entity.User;
import java.util.ArrayList;
import java.util.List;

public class HouseholdDashboardState {

    private String username;
    private Household household;
    private final List<User> users = new ArrayList<>();

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public Household getHousehold() { return household; }
    public void setHousehold(Household household) { this.household = household; }

    public String getHouseholdID() {
        return household != null ? household.getHouseholdID() : "";
    }

    public List<User> getUsers() { return users; }
}
