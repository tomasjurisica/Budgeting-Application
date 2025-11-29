package use_case.add_household_entry;

public interface AddHouseholdEntryInputBoundary {
    /**
     * Execute the Add Entry use case
     * @param addEntryInputData input data for this use case
     */
    void execute(AddHouseholdEntryInputData addEntryInputData);
}
