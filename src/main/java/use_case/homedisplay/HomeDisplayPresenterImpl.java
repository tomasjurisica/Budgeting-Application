package use_case.homedisplay;

import viewmodels.HomePageViewModel;
import entity.Entry;
import java.util.List;

public class HomeDisplayPresenterImpl implements HomeDisplayOutputBoundary{
    private final HomePageViewModel viewModel;

    public HomeDisplayPresenterImpl(HomePageViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentHomeData(List<Entry> entries) {
        viewModel.setEntries(entries);
    }

}
