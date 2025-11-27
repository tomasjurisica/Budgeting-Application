package interface_adapter.add_entry;

import java.time.LocalDate;
import java.util.List;

public class AddEntryState {
    // initial output? (just usernames)
    private String[] allUserNames; // all usernames from household

    // inputs?
    private String name; // name of entry
    private String category;
    private LocalDate date;
    private float total;
    private List<String> userNames; // usernames for people added to entry
    private float[] percents;

    // errors
    private String errorMessage;
    private boolean hasError;

    public String[] getAllUserNames() {
        return allUserNames;
    }
    public void setAllUserNames(String[] allUserNames) {
        this.allUserNames = allUserNames;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public float getTotal() {
        return total;
    }
    public void setTotal(float total) {
        this.total = total;
    }

    public List<String> getUserNames() {
        return this.userNames;
    }
    public void setUserNames(List<String> userNames) {
        this.userNames = userNames;
    }

    public float[] getPercents() {
        return percents;
    }
    public void setPercents(float[] percents) {
        this.percents = percents;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isHasError() {return hasError;}
    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }
}
