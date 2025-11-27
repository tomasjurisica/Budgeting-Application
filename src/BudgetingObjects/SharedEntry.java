package BudgetingObjects;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class SharedEntry extends Entry{
    private final List<User> users;

    private final float[] contributed;

    private final List<Entry> entries = new ArrayList<Entry>() {
    };

    /**
     * Creates a SharedEntry.
     * @param headEntry the shared entry for the whole household
     * @param users the list of users for this entry
     * @param percents the percent contributed by each user.
     */
    public SharedEntry(Entry headEntry, List<User> users, float[] percents) {
        super(headEntry.getName(), headEntry.getCategory(), headEntry.getAmount(), headEntry.getDate());
        this.users = users;
        this.contributed = new float[percents.length];

        int i = 0;

        for (float percent : percents) {
            BigDecimal contribution = BigDecimal.valueOf(headEntry.getAmount() * percent);
            contribution = contribution.setScale(2, RoundingMode.HALF_UP);
            this.contributed[i] = contribution.floatValue();

            i++;
        }

        i = 0;

        for (User user : users) {
            Entry addedEntry = new Entry(headEntry.getName(),
                    headEntry.getCategory(), this.contributed[i], headEntry.getDate());
            user.addEntry(addedEntry);
            this.entries.add(addedEntry);

            i++;
        }
    }

    /**
     * @return Returns a copy of the list of users on this shared entry.
     */
    public List<User> getUsers() {
        return new ArrayList<>(users);
    }

    /**
     * @return Returns the dollar amount each user contributes to the total
     */
    public float[] getContributed() {
        return contributed;
    }

    /**
     *
     * @return Returns a copy of the arraylist of entries
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