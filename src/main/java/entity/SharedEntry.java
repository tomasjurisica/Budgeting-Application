package entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class SharedEntry extends Entry {
    private final List<User> users;

    private final float[] contributed;

    private final List<Entry> entries = new ArrayList<Entry>() {
    };

    /**
     * Creates a SharedEntry.
     *
     * @param headEntry the shared entry for the whole household
     * @param users     the list of users for this entry
     * @param percents  the percent contributed by each user. (Sum of percents needs to be <= 100)
     */
    public SharedEntry(Entry headEntry, List<User> users, float[] percents) {
        // init
        super(headEntry.getName(), headEntry.getCategory(), headEntry.getAmount(), headEntry.getDate());
        this.users = users;
        this.contributed = new float[percents.length];

        // bigdecimal inits
        BigDecimal hundred = new BigDecimal("100");
        BigDecimal amount = BigDecimal.valueOf(headEntry.getAmount());
        BigDecimal numberOfUsers = BigDecimal.valueOf(percents.length);
        BigDecimal bDPercent;

        // calculate how much is "missing" from 100%.
        BigDecimal totalPercent = BigDecimal.ZERO;
        for (float percent : percents) {
            bDPercent = new BigDecimal(String.valueOf(percent));
            totalPercent = totalPercent.add(bDPercent);
        }
        BigDecimal percentError = hundred.subtract(totalPercent);


        BigDecimal totalError = amount.multiply(percentError).divide(hundred, 10, RoundingMode.HALF_UP);

        // Calculate
        BigDecimal[] bdContribution = new BigDecimal[percents.length];
        int i = 0;
        for (float percent : percents) {
            bDPercent = new BigDecimal(String.valueOf(percent));

            BigDecimal exactContribution = amount.multiply(bDPercent).divide(hundred, 10, RoundingMode.HALF_UP);
            BigDecimal contribution = exactContribution.setScale(2, RoundingMode.HALF_UP);

            totalError = totalError.add(exactContribution.subtract(contribution));

            bdContribution[i] = contribution;

            i++;
        }

        BigDecimal evenlyDivide = totalError.divide(numberOfUsers, 2, RoundingMode.HALF_UP);
        totalError = totalError.subtract(evenlyDivide.multiply(numberOfUsers));
        i = 0;
        for (BigDecimal db : bdContribution) {
            bdContribution[i] = bdContribution[i].add(evenlyDivide);
            i++;
        }


        BigDecimal cent = new BigDecimal("0.01");
        // now we have the total error, will evenly split amongst remaining
        i = 0;
        while (totalError.compareTo(BigDecimal.ZERO) > 0) {
            if (i == contributed.length) {
                i = 0;
            }
            bdContribution[i] = bdContribution[i].add(cent);

            totalError = totalError.subtract(cent);
            i++;
        }


        for (int j = 0; j < percents.length; j++) {
            contributed[j] = bdContribution[j].floatValue();
        }



        // I don't think this is needed?
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
    public float getContribution(User user) {
        return contributed[users.indexOf(user)];
    }


}
