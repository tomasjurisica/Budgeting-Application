package use_case.home_display;

import entity.Entry;
import interface_adapter.home_page.HomePageViewModel;

import java.util.List;

public class HomeDisplayPresenterImpl implements HomeDisplayOutputBoundary{
    private final interface_adapter.home_page.HomePageViewModel viewModel;

    public HomeDisplayPresenterImpl(HomePageViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentHomeData(List<Entry> entries) {
        viewModel.setEntries(entries);
    }

}
