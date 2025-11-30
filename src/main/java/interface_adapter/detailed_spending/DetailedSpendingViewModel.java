package interface_adapter.detailed_spending;
import interface_adapter.ViewModel;

public class DetailedSpendingViewModel extends ViewModel<DetailedSpendingState> {
    public DetailedSpendingViewModel() {
        super("detailed spending");
        setState(new DetailedSpendingState());
    }

}
