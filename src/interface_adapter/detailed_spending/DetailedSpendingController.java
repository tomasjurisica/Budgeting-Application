package interface_adapter.detailed_spending;

import use_case.detailed_spending.DetailedSpendingInputBoundary;
import use_case.detailed_spending.DetailedSpendingInputData;

public class DetailedSpendingController {
    private final DetailedSpendingInputBoundary interactor;

    public DetailedSpendingController(DetailedSpendingInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(String username, String categoryName, int month, int year) {
        DetailedSpendingInputData inputData =
                new DetailedSpendingInputData(username, categoryName, month, year);
        interactor.execute(inputData);
    }
}

