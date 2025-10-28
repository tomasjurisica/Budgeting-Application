package BudgetingObjects;

import java.util.ArrayList;

public class Household {
    private ArrayList<User> users = new ArrayList<>();

    private ArrayList<Entry> sharedEntries = new ArrayList<>();

    public Household() {

    }

    public void addUser(User newUser) {
        users.add(newUser);
    }

    public ArrayList<User> getUsers() {
        return new ArrayList<>(users);
    }
}
