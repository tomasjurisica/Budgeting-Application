package entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

import static java.lang.Math.max;

public class User {
    private String name;

    private Household householdPointer;

    private ArrayList<Entry> entries = new ArrayList<>(); // stores all entries, sorted in chronological order

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
     * NOT IMPLEMENTED YET
     * @param year: The year of entries to be returned.
     * @param month: The month of entries to be returned.
     * @return Returns an arraylist of entries from the given year and month, in chronological order. Returns an empty list if no entries that month.
     */
    public ArrayList<Entry> getEntries(int year, int month) {
        ArrayList<Entry> returnList = new ArrayList<>();

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

        int top = entries.size() - 1;
        int bottom = 0;
        int startIndex = -1;

        while (top >= bottom) {
            int middle = (top - bottom) / 2 + bottom;

            if (entries.get(middle).getDate().isAfter(dateOf)) {
                top = middle - 1;
            }
            else if (entries.get(middle).getDate().isBefore(dateOf)) {
                bottom = middle + 1;
            }
            else {
                startIndex = middle;
                top = middle - 1;
            }
        }

        top = entries.size() - 1;
        bottom = 0;
        int endIndex = -1;

        while (top >= bottom) {
            int middle = (top - bottom) / 2 + bottom;

            if (entries.get(middle).getDate().isAfter(dateOf)) {
                top = middle - 1;
            }
            else if (entries.get(middle).getDate().isBefore(dateOf)) {
                bottom = middle + 1;
            }
            else {
                endIndex = middle;
                bottom = middle + 1;
            }
        }

        if (startIndex != -1 && endIndex != -1){
            for(int i=0; i+startIndex<=endIndex;i++){
                returnList.add(entries.get(i));
            }
        }

        return returnList;
    }

    /**
     * @param category Case-sensitive. Category to retrieve entries of the same type.
     * @return An arraylist of all entries with the corresponding category. Sorted in chronological order. Returns an empty list if no entries match the category.
     */
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

    /**
     * Adds the given entry to the user's entries.
     *
     * @param newEntry: Entry to be added into this user's entries.
     */
//    public void addEntry(Entry newEntry) {
//        LocalDate checkedDate = newEntry.getDate();
//
//        // adds to end if entries is empty or if the new entry is the most recent item chronologically
//        if (entries.isEmpty() || !entries.getLast().getDate().isAfter(checkedDate)) {
//            entries.add(newEntry);
//        }
//        else {
//            int top = entries.size() - 1;
//            int bottom = 0;
//            int middle = entries.size() / 2;
//
//
//            while (top-bottom >= 2) {
//                if (entries.get(middle).getDate().isAfter(checkedDate)) {
//                    top = middle;
//                    middle = middle / 2;
//                }
//                else {
//                    bottom = middle;
//                    middle = top + bottom / 2;
//                }
//            }
//
//
//            entries.add(top, newEntry);
//
//        }
//    }

    /**
     * Adds the given list of entries to the user's entries.
     *
     * @param listOfEntries: List of entries SORTED in chronological order.
     */
    public void addEntry(ArrayList<Entry> listOfEntries) {
        if (entries.isEmpty()) {
            entries.addAll(listOfEntries);
        } else {
            int i = 0;
            int j = 0;

            while (j < listOfEntries.size()) {
                if (i >= entries.size()) {
                    entries.add(listOfEntries.get(j));
                    j++;
                } else if (entries.get(i).getDate().isAfter(listOfEntries.get(j).getDate())) {
                    entries.add(i, listOfEntries.get(j));
                    j++;
                }
                i++;
            }
        }
    }
}
