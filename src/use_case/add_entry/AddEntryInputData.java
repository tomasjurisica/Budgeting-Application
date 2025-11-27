package use_case.add_entry;

import java.time.LocalDate;
import java.util.List;

public class AddEntryInputData {
    private final String name; // name of entry
    private final String category;
    private final LocalDate date;
    private final float total;
    private final List<String> userNames;
    private final float[] percents;

    /**
     *
     * @param userNames Has to contain at least one username
     */
    public AddEntryInputData(String name, String category, LocalDate date,
                             float total, List<String> userNames, float[] percents) {
        this.name = name;
        this.category = category;
        this.date = date;
        this.total = total;
        this.userNames = userNames;
        this.percents = percents;
    }

    public String getName() {
        return name;
    }

    public  String getCategory() {
        return category;
    }

    public LocalDate getDate() {
        return date;
    }

    public float getTotal() {
        return total;
    }

    public List<String> getUserNames() {
        return userNames;
    }

    public float[] getPercents() {
        return percents;
    }
}
