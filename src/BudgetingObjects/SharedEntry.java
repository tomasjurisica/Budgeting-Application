package BudgetingObjects;

import java.util.ArrayList;
import java.util.List;

public class SharedEntry extends Entry{
    private final List<User> users;

    private final float[] contributed;

    private final List<Entry> entries = new ArrayList<Entry>() {
    };

    /**
     * Creates a SharedEntry.
     * @param hEntry the shared entry for the whole household
     * @param users the list of users for this entry
     * @param contributed the dollar amount contributed by each user. Total sum should add to hEntry's amount
     *                    Note: May be changed to give percents instead of contribution
     */
    public SharedEntry(Entry hEntry, List<User> users, float[] contributed) {
       super(hEntry.getName(), hEntry.getCategory(), hEntry.getAmount(), hEntry.getDate());
       this.users = users;
       this.contributed = contributed;


        int i = 0;

       for (User user : users) {
           Entry addedEntry = new Entry(hEntry.getName(), hEntry.getCategory(), contributed[i], hEntry.getDate());
           user.addEntry(addedEntry);
           this.entries.add(addedEntry);

           i++;
       }
    }

    public List<User> getUsers() {
        return users;
    }

    public float[] getContributed() {
        return contributed;
    }

    /**
     *
     * @return Returns arraylist of entries
     */
    public List<Entry> getEntries() {
        return new ArrayList<>(entries);
    }

    /**
     * Made for potential future use, not implemented. Will returns string version of SharedEntry.
     * Has extra empty line at the end, may be removed in future
     */
    public String string() {
        StringBuilder result = new StringBuilder();

        for (User user : users) {
            result.append(string(user));
            result.append("\n");
        }
        return result.toString();
    }

    /**
     * Made for potential future use. Will returns string version of entry for user, listing Name, Amount, and Category
     */
    public String string(User user) {
        return getEntry(user).getName() + " | " + getEntry(user).getAmount() + " | " + getEntry(user).getCategory();
    }

    /**
     * @param user The user whose entry you want to get
     * @return The entry corresponding to that user
     */
    public Entry getEntry(User user) {
        return entries.get(users.indexOf(user));
    }

    /**
     * @param user The user whose contribution you want to get
     * @return A float for how much the user is contributing to the household
     */
    public float getContribution (User user) {
        return contributed[users.indexOf(user)];
    }


}
