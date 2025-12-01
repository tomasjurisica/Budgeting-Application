package use_case.detailed_spending;

import entity.Entry;

import java.util.List;
import java.util.ArrayList;

public class DetailedSpendingInteractor implements DetailedSpendingInputBoundary {
    private final DetailedSpendingUserDataAccessInterface userDataAccessObject;
    // private final DetailedSpendingUserDataAccessInterface userDataAccess;
    private final DetailedSpendingOutputBoundary presenter;

    public DetailedSpendingInteractor(DetailedSpendingUserDataAccessInterface userDataAccessInterface,
                                      DetailedSpendingOutputBoundary presenter) {
        this.userDataAccessObject = userDataAccessInterface;
        this.presenter = presenter;
    }

    /**
     * Executes the detailed spending use case by retrieving all entries for the
     * given user, month, and year, filtering them by category, and passing the
     * result to the presenter.
     *
     * @param inputData the input data containing the username, category,
     *                  year, and month for which to retrieve spending details.
     */

    public void execute(DetailedSpendingInputData inputData) {
        final String username = inputData.getUserName();
        final String categoryName = inputData.getCategoryName();
        final int year = inputData.getYear();
        final int month = inputData.getMonth();
        final List<Entry> entries = userDataAccessObject.getEntries(username, year, month);
        final List<DetailedSpendingOutputData.Purchase> purchases = new ArrayList<>();

        for (Entry entry : entries) {
            if (entry.getCategory().equals(categoryName)) {
                purchases.add(
                    new DetailedSpendingOutputData.Purchase(entry.getName(),
                            entry.getDate(), entry.getAmount()));
            }
        }
        if (purchases.isEmpty()) {
            presenter.prepareFailView("No purchases were found for " + categoryName);
        }
        final DetailedSpendingOutputData outputData = new DetailedSpendingOutputData(categoryName, purchases);
        presenter.prepareSuccessView(outputData);
    }
}
