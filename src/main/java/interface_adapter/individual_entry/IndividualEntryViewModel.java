package interface_adapter.individual_entry;

import use_case.individual_entry.add_entry.AddIndividualEntryOutputData;

/**
 * Holds the latest state for the Individual Entry view:
 * list of entries, last message, etc.
 * For now we just store the last output data.
 */
public class IndividualEntryViewModel {

    private AddIndividualEntryOutputData lastOutput;

    public AddIndividualEntryOutputData getLastOutput() {
        return lastOutput;
    }

    public void setLastOutput(AddIndividualEntryOutputData lastOutput) {
        this.lastOutput = lastOutput;
    }
}
