package BudgetingObjects;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Household{
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

    public ArrayList<Entry> getSharedEntries(int year, int month) {
        ArrayList<Entry> returnList = new ArrayList<>();

        int lastDay = 28;

        // Calculate last day of month
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            lastDay = 31;
        }
        else if (month == 4 || month == 6 || month == 9 || month == 11) {
            lastDay = 30;
        }
        else if ((year % 4 == 0) && (year % 100 != 0 || year % 400 == 0)) {
            lastDay = 29;
        }

        LocalDate firstDate = LocalDate.of(year, month, 1);
        LocalDate lastDate = LocalDate.of(year, month, lastDay);

        int startIndex = getStartIndex(firstDate, lastDate);
        int endIndex = getEndIndex(firstDate, lastDate);

        // If indexes are valid, add all values in range to return list
        if (startIndex != -1 && endIndex != -1){
            for(int i=0; i+startIndex<=endIndex;i++){
                returnList.add(sharedEntries.get(i + startIndex));
            }
        }

        return returnList;
    }

    /**
     * @return Returns the admin of the household.
     */
    public User getAdmin() {
        return users.getFirst();
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
     * Adds a new shared entry to the household. In chronological order
     * @param newEntry sharedEntry to be added to the household
     */
    public void addSharedEntry(SharedEntry newEntry) {
        LocalDate checkedDate = newEntry.getDate();

        // adds to end if entries is empty or if the new entry is the most recent item chronologically
        if (sharedEntries.isEmpty() || !sharedEntries.getLast().getDate().isAfter(checkedDate)) {
            sharedEntries.add(newEntry);
        }
        else {
            int i = 0;

            while (i < sharedEntries.size() && !sharedEntries.get(i).getDate().isAfter(checkedDate)) {
                i++;
            }

            sharedEntries.add(i, newEntry);

        }
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

    /**
     * Helper method for returning entries from a date range
     * @param firstDate the first date in range to be considered.
     * @param lastDate the last date in range to be considered. lastDate is after firstDate
     * @return the starting index of entries from specific date(s). Returns -1 if date does not exist
     */
    private int getStartIndex(LocalDate firstDate, LocalDate lastDate) {
        int i = 0;

        while (i < sharedEntries.size()) {
            if (!sharedEntries.get(i).getDate().isBefore(firstDate) && !sharedEntries.get(i).getDate().isAfter(lastDate)) {
                return i;
            }
            i ++;
        }

        return -1;
    }

    /**
     * Helper method for returning entries from a date range
     * @param firstDate the first date in range to be considered.
     * @param lastDate the last date in range to be considered. lastDate is after firstDate
     * @return the ending index of entries from specific date(s). Returns -1 if date does not exist
     */
    private int getEndIndex(LocalDate firstDate, LocalDate lastDate) {
        int result = -1;
        int i = 0;

        while (i < sharedEntries.size()) {
            if (!sharedEntries.get(i).getDate().isBefore(firstDate) && !sharedEntries.get(i).getDate().isAfter(lastDate)) {
                result = i;
            }
            i ++;
        }

        return result;
    }
}
