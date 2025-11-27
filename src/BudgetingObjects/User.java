package BudgetingObjects;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class User{
    private final String name;

    private Household householdPointer;

    private final List<Entry> entries = new ArrayList<>(); // stores all entries, sorted in chronological order

    public User(String name) {
        this.name = name;
    }

    /**
     *
     * @param h: household to be linked to
     */
    public void setHousehold (Household h) {
        householdPointer = h;
    }

    /**
     *
     * @return The household this user is linked to. THIS IS NOT A COPY. Returns null if not linked to a household
     */
    public Household getHousehold () {
        return householdPointer;
    }

    public String getName() {
        return name;
    }

    /**
     * @return Returns this all of user's entries, in chronological order. Returns an empty list if no entries exist.
     */
    public ArrayList<Entry> getEntries() {
        return new ArrayList<>(entries);
    }

    /**
     * @param year: The year of entries to be returned.
     * @param month: The month of entries to be returned.
     * @return Returns an arraylist of entries from the given year and month, in chronological order. Returns an empty list if no entries that month.
     */
    public ArrayList<Entry> getEntries(int year, int month) {
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
                returnList.add(entries.get(i + startIndex));
            }
        }

        return returnList;
    }

    /**
     * @param dateOf: Date of entries to be returned.
     * @return Returns an arraylist of entries from the given date, in chronological order. Returns an empty list if no entries that day.
     */
    public ArrayList<Entry> getEntries(LocalDate dateOf) {
        if (entries.isEmpty()) {
            return new ArrayList<Entry>();
        }

        ArrayList<Entry> returnList = new ArrayList<>();

        int startIndex = getStartIndex(dateOf, dateOf);

        int endIndex = getEndIndex(dateOf, dateOf);

        if (startIndex != -1 && endIndex != -1){
            for(int i=0; i+startIndex<=endIndex;i++){
                returnList.add(entries.get(i));
            }
        }

        return returnList;
    }

    /**
     * Helper method for returning entries from a date range
     * @param firstDate the first date in range to be considered.
     * @param lastDate the last date in range to be considered. lastDate is after firstDate
     * @return the starting index of entries from specific date(s). Returns -1 if date does not exist
     */
    private int getStartIndex(LocalDate firstDate, LocalDate lastDate) {
        int i = 0;

        while (i < entries.size()) {
            if (!entries.get(i).getDate().isBefore(firstDate) && !entries.get(i).getDate().isAfter(lastDate)) {
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

        while (i < entries.size()) {
            if (!entries.get(i).getDate().isBefore(firstDate) && !entries.get(i).getDate().isAfter(lastDate)) {
                result = i;
            }
            i ++;
        }

        return result;
    }

    /**
     * Adds the given entry to the user's entries.
     *
     * @param newEntry: Entry to be added into this user's entries.
     */
    public void addEntry(Entry newEntry) {
        LocalDate checkedDate = newEntry.getDate();

        // adds to end if entries is empty or if the new entry is the most recent item chronologically
        if (entries.isEmpty() || !entries.getLast().getDate().isAfter(checkedDate)) {
            entries.add(newEntry);
        }
        else {
            int i = 0;

            while (i < entries.size() && !entries.get(i).getDate().isAfter(checkedDate)) {
                i++;
            }

            entries.add(i, newEntry);

        }
    }

    /**
     * Adds the given list of entries to the user's entries.
     *
     * @param listOfEntries: List of entries SORTED in chronological order.
     */
    public void addEntry(ArrayList<Entry> listOfEntries) {
        if (entries.isEmpty()) {
            entries.addAll(listOfEntries);
        }
        else {
            int i = 0;
            int j = 0;

            while (j < listOfEntries.size()) {
                if (entries.get(i).getDate().isAfter(listOfEntries.get(j).getDate())) {
                    entries.add(i, listOfEntries.get(j));
                    j++;
                }
                i++;
            }
        }
    }


    // Dead classes
    /*
    public ArrayList<Entry> getEntriesFromCategory (String category) {
        ArrayList<Entry> returnList = new ArrayList<>();

        for (Entry entry : entries) {
            if (Objects.equals(entry.getCategory(), category)) {
                returnList.add(entry);
            }
        }

        return returnList;
    }

    public ArrayList<Entry> getEntriesFromCategory (String category, LocalDate dateOf) {
        ArrayList<Entry> returnList = new ArrayList<>();

        for (Entry entry : getEntries(dateOf)) {
            if (Objects.equals(entry.getCategory(), category)) {
                returnList.add(entry);
            }
        }

        return returnList;
    }
    */
}