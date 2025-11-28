package BudgetingObjects;

import java.util.ArrayList;

public class Household {
    private ArrayList<User> users = new ArrayList<>();

    private ArrayList<Entry> sharedEntries = new ArrayList<>();

    private String houseID = "Default";

    /**
     * Try not to use this version of the constructor. Legacy only to not break test code.
     */
    public Household() {

    }

    /**
     *
     * @param houseID The id of the house. Passed as a string
     */
    public Household(String houseID) {
        this.houseID = houseID;
    }

    public void setHouseID (String s) {
        houseID = s;
    }

    public void addUser(User newUser) {
        users.add(newUser);
        newUser.setHousehold(this);
    }

    public ArrayList<User> getUsers() {
        return new ArrayList<>(users);
    }
}
