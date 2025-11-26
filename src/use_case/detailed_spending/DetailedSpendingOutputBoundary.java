package use_case.detailed_spending;

/**
 * The output boundary for the Detailed Spending Use Case.
 */
public interface DetailedSpendingOutputBoundary {
    /**
     * Prepares the success view for the Detailed Spending Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(DetailedSpendingOutputData outputData);

    /**
     * Prepares the failure view for the Detailed Spending Use Case.
     * @param errorMessage the explanation of the failure
     */
    // PRESENTER DECIDES HOW THE VIEW SHOULD REACT
    void prepareFailView(String errorMessage);

}