package use_case;

/**
 * Detailed Spending Use Case.
 */
public interface DetailedSpendingInputBoundary {
    /**
     * Execute the Detailed Spending Use Case.
     *
     * @param detailedSpendingInputData the input data for this use case
     */
    void execute(DetailedSpendingInputData detailedSpendingInputData);
}
