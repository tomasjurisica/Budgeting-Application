package use_case.add_entry;

public interface AddEntryOutputBoundary {
    /**
     * Prepares for success view
     */
    void prepareSuccessView(AddEntryOutputData outputData);

    /**
     * Prepares for fail view
     * @param errorMessage the explanation of failure
     */
    void prepareFailView(String errorMessage);
}
