package interface_adapter.detailed_spending;

import use_case.detailed_spending.DetailedSpendingInputBoundary;
import use_case.detailed_spending.DetailedSpendingInputData;

public class DetailedSpendingController {
    private final DetailedSpendingInputBoundary interactor;

    public DetailedSpendingController(DetailedSpendingInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Executes the detailed spending request by creating the input data object
     * and transferring the call to the interactor.
     *
     * @param householdID the householdID associated with the request
     * @param categoryName the category to retrieve spending details for
     * @param month the month of the spending data
     * @param year the year of the spending data
     */
    public void execute(String householdID, String categoryName, int month, int year) {
        final DetailedSpendingInputData inputData =
                new DetailedSpendingInputData(householdID, categoryName, month, year);
        interactor.execute(inputData);
    }
}

