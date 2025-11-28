package interface_adapter.detailed_spending;
import data_access.FileUserDataAccessObject;
import interface_adapter.ViewManagerModel;
import interface_adapter.detailed_spending.DetailedSpendingState;
import interface_adapter.detailed_spending.DetailedSpendingViewModel;
import use_case.detailed_spending.DetailedSpendingOutputBoundary;
import use_case.detailed_spending.DetailedSpendingOutputData;

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
        detailedSpendingState.setPurchases(response.getPurchases());
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
