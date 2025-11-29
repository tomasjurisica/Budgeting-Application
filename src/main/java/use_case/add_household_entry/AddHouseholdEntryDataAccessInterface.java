package use_case.add_household_entry;
import entity.*;

import java.util.List;

public interface AddHouseholdEntryDataAccessInterface {
    /**
     * Get users based on currently logged-in user
     * (Need to see who's the logged-in users, get the household, then all the users in the household
     * @return List of users on the currently logged in household
     */
    List<User> getUsers();

    void addHouseholdEntry(SharedEntry householdEntry);
}
