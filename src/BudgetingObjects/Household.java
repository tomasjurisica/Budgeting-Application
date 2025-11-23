package BudgetingObjects;

import java.util.ArrayList;
import java.util.List;

public class Household implements EntrySorting{
    private final ArrayList<User> users = new ArrayList<>();

    private final ArrayList<SharedEntry> sharedEntries = new ArrayList<>();
    private String houseID = "Default";

    /**
     * Try not to use this version of the constructor. Legacy only to not break test code.
     */
    public Household() {

    }

    /**
     * @param houseID The id of the house. Passed as a string.
     */
    public Household(String houseID) {
        this.houseID = houseID;
    }

    /**
     * @return Returns a copy of the list of users in this household.
     */
    public ArrayList<User> getUsers() {
        return new ArrayList<>(users);
    }

    public String getHouseId () {
        return houseID;
    }
    
    /**
     * @return Returns a list of sharedEntries, in chronological order
     */
    public ArrayList<Entry> getSharedEntries() {
        return new ArrayList<>(sharedEntries);
    }

    /**
     * @return Returns the admin of the household.
     */
    public User getAdmin() {
        return users.getFirst();
    }

    /**
     * Adds the shared entry. NOT IN CHRONOLOGICAL ORDER YET
     * @param sharedEntry The shared entry to be added
     */
    public void addSharedEntry(SharedEntry sharedEntry) {
        sharedEntries.add(sharedEntry);
    }

    /**
     * Updates the house ID of this household
     * @param s New house ID.
     */
    public void setHouseID (String s) {
        houseID = s;
    }

    /**
     * Adds a user to the household. The first user added becomes the admin.
     * @param newUser User to be added to the household.
     */
    public void addUser(User newUser) {
        users.add(newUser);
        newUser.setHousehold(this);
    }

    /**
     * NOT FULLY IMPLEMENTED TO BE IN CHRONOLOGICAL ORDER. WILL BE UPDATED
     * Adds an entry to the household. VALID CALL ONLY IF MADE BY ADMIN.
     * @param entry The entry to be added
     * @param users The users who are contributing to the entry
     * @param contributions The specific contributions made by each user in dollars, in the order of the users list
     */
    void addEntry(Entry entry, List<User> users, float[] contributions) {
        SharedEntry addedEntry = new SharedEntry(entry, users, contributions);

        this.addSharedEntry(addedEntry);
    }

    void addEntry(SharedEntry entry) {
        this.addSharedEntry(entry);
    }

    /**
     * Adds the given list of entries to the user's entries.
     *
     * @param listOfEntries: List of entries SORTED in chronological order.
     */
    void addEntry(List<SharedEntry> listOfEntries) {
        if (sharedEntries.isEmpty()) {
            sharedEntries.addAll(listOfEntries);
        }
        else {
            int i = 0;
            int j = 0;

            while (j < listOfEntries.size()) {
                if (sharedEntries.get(i).getDate().isAfter(listOfEntries.get(j).getDate())) {
                    sharedEntries.add(i, listOfEntries.get(j));
                    j++;
                }
                i++;
            }
        }
    }
}
