package interface_adapter.detailed_spending;

import use_case.detailed_spending.DetailedSpendingOutputBoundary;
import use_case.detailed_spending.DetailedSpendingOutputData;

import java.util.ArrayList;
import java.util.List;

public class DetailedSpendingPresenter implements DetailedSpendingOutputBoundary {
    private final DetailedSpendingViewModel detailedSpendingViewModel;

    public DetailedSpendingPresenter(DetailedSpendingViewModel detailedSpendingViewModel) {
        this.detailedSpendingViewModel = detailedSpendingViewModel;
    }

    @Override
    public void prepareSuccessView(DetailedSpendingOutputData response) {
        final DetailedSpendingState detailedSpendingState = detailedSpendingViewModel.getState();
        // detailedSpendingState.getPurchases(response.getPurchases());
        detailedSpendingState.setCategoryName(response.getCategoryName());
        List<DetailedSpendingState.PurchaseUIModel> uiPurchases = new ArrayList<>();
        for (var p : response.getPurchases()) {
            uiPurchases.add(new DetailedSpendingState.PurchaseUIModel(
                    p.purchaseName(),
                    p.date(),
                    p.amount()
            ));
        }

        detailedSpendingState.setPurchases(uiPurchases);

        detailedSpendingState.setHasError(false);
        detailedSpendingState.setErrorMessage(null);
        detailedSpendingViewModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final DetailedSpendingState detailedSpendingState = detailedSpendingViewModel.getState();
        detailedSpendingState.setHasError(true);
        detailedSpendingState.setErrorMessage(errorMessage);
        detailedSpendingViewModel.firePropertyChange();
    }

}
