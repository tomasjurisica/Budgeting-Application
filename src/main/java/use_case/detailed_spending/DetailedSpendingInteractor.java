package use_case.detailed_spending;
import entity.Entry;
import use_case.login.LoginUserDataAccessInterface;

import java.util.ArrayList;
import java.util.List;

public class DetailedSpendingInteractor implements DetailedSpendingInputBoundary {
    private final DetailedSpendingUserDataAccessInterface userDataAccessObject;
    // private final DetailedSpendingUserDataAccessInterface userDataAccess;
    private final DetailedSpendingOutputBoundary presenter;

    public DetailedSpendingInteractor(DetailedSpendingUserDataAccessInterface userDataAccessInterface,
                                      DetailedSpendingOutputBoundary presenter) {
        this.userDataAccessObject = userDataAccessInterface;
        this.presenter = presenter;
    }
    public void execute(DetailedSpendingInputData inputData) {
        String username = inputData.getUserName();
        String categoryName = inputData.getCategoryName();
        int year = inputData.getYear();
        int month = inputData.getMonth();
        List<Entry> entries = userDataAccessObject.getEntries(username,year, month);
        List<DetailedSpendingOutputData.Purchase> purchases = new ArrayList<>();

        for (Entry entry : entries) {
            if (entry.getCategory().equals(categoryName)){
                purchases.add(new DetailedSpendingOutputData.Purchase(entry.getName(),
                        entry.getDate(),entry.getAmount()));

            }
        }

        if (purchases.isEmpty()){
            presenter.prepareFailView("No purchases were found for "+ categoryName);
            return;
        }

        DetailedSpendingOutputData outputData = new DetailedSpendingOutputData(categoryName, purchases);
        presenter.prepareSuccessView(outputData);
    }
}
