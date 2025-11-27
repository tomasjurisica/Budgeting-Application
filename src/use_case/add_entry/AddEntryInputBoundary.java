package use_case.add_entry;

public interface AddEntryInputBoundary {
    /**
     * Execute the Add Entry use case
     * @param addEntryInputData input data for this use case
     */
    void execute(AddEntryInputData addEntryInputData);
}
