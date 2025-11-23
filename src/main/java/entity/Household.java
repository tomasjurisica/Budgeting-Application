package entity;

import java.util.ArrayList;

/**
 * A simple entity representing a household. Households have a username and password.
 */
public class Household {
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Entry> householdEntries = new ArrayList<>();
    private final String householdID;
    private final String password;

    //private String houseID = "Default";

    /**
     * Creates a new user with the given non-empty name and non-empty password.
     * @param householdID the household ID
     * @param password the password
     * @throws IllegalArgumentException if the password or name are empty
     */
    public Household(String householdID, String password) {
        if ("".equals(householdID)) {
            throw new IllegalArgumentException("Household ID cannot be empty");
        }
        if ("".equals(password)) {
            throw new IllegalArgumentException("Household password cannot be empty");
        }
        this.householdID = householdID;
        this.password = password;
    }

    public String getHouseholdID() {
        return householdID;
    }

    public String getPassword() {
        return password;
    }

    public void addUser(User newUser) {
        users.add(newUser);
        newUser.setHousehold(this);
    }

    public ArrayList<User> getUsers() {
        return users;
    }

}
