package entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        householdEntries.add(newEntry);
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
        private final ArrayList<Entry> savedNormalEntries;
        private final Map<String, ArrayList<Entry>> savedUserEntries;

        /**
         * Creates a memento with a snapshot of the household entries.
         * @param entries The household entries (SharedEntry) to save in this memento
         * @param normalEntries The normal household entries (Entry) to save in this memento
         * @param users The list of users whose entries should be saved
         */
        private HouseholdMemento(ArrayList<SharedEntry> entries, ArrayList<Entry> normalEntries, ArrayList<User> users) {
            // Deep copy household entries (SharedEntry) to ensure immutability
            this.savedEntries = new ArrayList<>();
            for (SharedEntry entry : entries) {
                this.savedEntries.add(entry);
            }
            
            // Deep copy normal household entries (Entry)
            this.savedNormalEntries = new ArrayList<>(normalEntries);
            
            // Deep copy user entries
            this.savedUserEntries = new HashMap<>();
            for (User user : users) {
                ArrayList<Entry> userEntriesCopy = new ArrayList<>(user.getEntries());
                this.savedUserEntries.put(user.getName(), userEntriesCopy);
            }
        }

        /**
         * Gets the saved household entries. Only accessible by the Household class.
         * @return A copy of the saved household entries
         */
        private ArrayList<SharedEntry> getSavedEntries() {
            return new ArrayList<>(savedEntries);
        }
        
        /**
         * Gets the saved normal household entries. Only accessible by the Household class.
         * @return A copy of the saved normal entries
         */
        private ArrayList<Entry> getSavedNormalEntries() {
            return new ArrayList<>(savedNormalEntries);
        }
        
        /**
         * Gets the saved user entries. Only accessible by the Household class.
         * @return A copy of the saved user entries map
         */
        private Map<String, ArrayList<Entry>> getSavedUserEntries() {
            return new HashMap<>(savedUserEntries);
        }
    }

    /**
     * Creates a memento (snapshot) of the current household entries state.
     * This is the Originator's method to create a memento.
     *
     * @return A memento containing the current state of household entries
     */
    public HouseholdMemento createMemento() {
        return new HouseholdMemento(householdEntries, normalHouseholdEntries, users);
    }

    /**
     * Restores the household entries state from a memento.
     * This is the Originator's method to restore state from a memento.
     *
     * @param memento The memento to restore from
     */
    public void restoreFromMemento(HouseholdMemento memento) {
        if (memento != null) {
            // Restore household entries (SharedEntry)
            this.householdEntries.clear();
            this.householdEntries.addAll(memento.getSavedEntries());
            
            // Restore normal household entries (Entry)
            this.normalHouseholdEntries.clear();
            this.normalHouseholdEntries.addAll(memento.getSavedNormalEntries());
            
            // Restore user entries
            Map<String, ArrayList<Entry>> savedUserEntries = memento.getSavedUserEntries();
            for (User user : users) {
                ArrayList<Entry> userEntries = savedUserEntries.get(user.getName());
                if (userEntries != null) {
                    // Clear the user's current entries and restore from memento
                    user.clearAndSetEntries(userEntries);
                }
            }
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

