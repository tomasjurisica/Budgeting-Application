package entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Household {
    private final ArrayList<User> users = new ArrayList<>();

    private final ArrayList<SharedEntry> householdEntries = new ArrayList<>();
    private final ArrayList<Entry> normalHouseholdEntries = new ArrayList<>();

    private final String householdID;
    private final String password;

    /**
     * Creates a new user with the given non-empty name and non-empty password.
     *
     * @param householdID the household ID
     * @param password    the password
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

    public void addNormalHouseholdEntry(Entry newEntry) {
        normalHouseholdEntries.add(newEntry);
    }

    public ArrayList<Entry> getNormalHouseholdEntries() {
        return this.normalHouseholdEntries;
    }

    /**
     * Adds a user to the household. The first user added becomes the admin.
     *
     * @param newUser User to be added to the household.
     */
    public void addUser(User newUser) {
        users.add(newUser);
        newUser.setHousehold(this);
    }

    /**
     * @return Returns a copy of the list of users in this household.
     */
    public ArrayList<User> getUsers() {
        return new ArrayList<>(users);
    }

    public String getHouseholdID() {
        return householdID;
    }

    public String getPassword() {
        return password;
    }

    /**
     * @return Returns a list of sharedEntries, in chronological order
     */
    public ArrayList<Entry> getHouseholdEntries() {
        return new ArrayList<>(householdEntries);
    }

    /**
     * Adds a new shared entry to the household. In chronological order
     *
     * @param newEntry sharedEntry to be added to the household
     */
    public void addHouseholdEntry(SharedEntry newEntry) {
        LocalDate checkedDate = newEntry.getDate();

        // adds to end if entries is empty or if the new entry is the most recent item chronologically
        if (householdEntries.isEmpty() || !householdEntries.getLast().getDate().isAfter(checkedDate)) {
            householdEntries.add(newEntry);
        } else {
            int i = 0;

            while (i < householdEntries.size() && !householdEntries.get(i).getDate().isAfter(checkedDate)) {
                i++;
            }

            householdEntries.add(i, newEntry);

        }
    }

    public ArrayList<Entry> getAllEntries() {
        ArrayList<Entry> allEntries = new ArrayList<>();
        for (SharedEntry entry : householdEntries) {
            allEntries.add(entry);
        }
        return allEntries;
    }

    // Memento Pattern Implementation

    /**
     * Memento class to store a snapshot of household entries state.
     * This is a nested class following the Memento design pattern.
     */
    public static class HouseholdMemento {
        private final ArrayList<SharedEntry> savedEntries;

        /**
         * Creates a memento with a snapshot of the household entries.
         * @param entries The entries to save in this memento
         */
        private HouseholdMemento(ArrayList<SharedEntry> entries) {
            // Deep copy to ensure immutability
            this.savedEntries = new ArrayList<>();
            for (SharedEntry entry : entries) {
                this.savedEntries.add(entry);
            }
        }

        /**
         * Gets the saved entries. Only accessible by the Household class.
         * @return A copy of the saved entries
         */
        private ArrayList<SharedEntry> getSavedEntries() {
            return new ArrayList<>(savedEntries);
        }
    }

    /**
     * Creates a memento (snapshot) of the current household entries state.
     * This is the Originator's method to create a memento.
     *
     * @return A memento containing the current state of household entries
     */
    public HouseholdMemento createMemento() {
        return new HouseholdMemento(householdEntries);
    }

    /**
     * Restores the household entries state from a memento.
     * This is the Originator's method to restore state from a memento.
     *
     * @param memento The memento to restore from
     */
    public void restoreFromMemento(HouseholdMemento memento) {
        if (memento != null) {
            this.householdEntries.clear();
            this.householdEntries.addAll(memento.getSavedEntries());
        }
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Household {\n");
        sb.append("  householdID='").append(householdID).append("',\n");

        // Do NOT print real password for security
        sb.append("  password='***',\n");

        // Users list
        sb.append("  users=[\n");
        for (User u : users) {
            sb.append("    ").append(u.toString()).append(",\n");
        }
        sb.append("  ],\n");

        // Household shared entries
        sb.append("  householdEntries=[\n");
        for (SharedEntry e : householdEntries) {
            sb.append("    ").append(e.toString()).append(",\n");
        }
        sb.append("  ]\n");

        sb.append("}");

        return sb.toString();
    }

}
