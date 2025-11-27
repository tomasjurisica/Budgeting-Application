package use_case.add_entry;

public class AddEntryOutputData {
    private final String[] userNames;

    public AddEntryOutputData(String[] userNames) {
        this.userNames = userNames;
    }

    public String[] getUserNames() {
        return this.userNames;
    }

}
