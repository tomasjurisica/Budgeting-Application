package use_case.add_household_entry;

public class AddHouseholdEntryOutputData {
    private final String[] userNames;

    public AddHouseholdEntryOutputData(String[] userNames) {
        this.userNames = userNames;
    }

    public String[] getUserNames() {
        return this.userNames;
    }

}
