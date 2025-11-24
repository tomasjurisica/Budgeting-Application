package interface_adapter.household_dashboard;

import entity.Household;
import entity.User;
import java.util.ArrayList;
import java.util.List;

public class HouseholdDashboardState {

    private String username;
    private Household household; // <- add this
    private List<User> users;

    public HouseholdDashboardState() {
        this.username = "";
        this.users = new ArrayList<>();
    }


    // getter and setter for username
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    // getter and setter for users
    public List<User> getUsers() { return users; }
    public void setUsers(List<User> users) { this.users = users; }

    // getter and setter for household
    public Household getHousehold() { return household; } // <- add this
    public void setHousehold(Household household) { this.household = household; } // <- add this
}
