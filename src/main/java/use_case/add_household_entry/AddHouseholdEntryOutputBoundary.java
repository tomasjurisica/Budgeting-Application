package use_case.add_household_entry;

public interface AddHouseholdEntryOutputBoundary {
    /**
     * Prepares for success view
     */
    void prepareSuccessView(AddHouseholdEntryOutputData outputData);

    /**
     * Prepares for fail view
     * @param errorMessage the explanation of failure
     */
    void prepareFailView(String errorMessage);
}