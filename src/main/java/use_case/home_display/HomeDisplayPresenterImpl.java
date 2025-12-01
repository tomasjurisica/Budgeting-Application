package use_case.home_display;

import entity.Entry;
import interface_adapter.home_display.HomeDisplayViewModel;

import java.util.List;

public class HomeDisplayPresenterImpl implements HomeDisplayOutputBoundary{
    private final HomeDisplayViewModel viewModel;

    public HomeDisplayPresenterImpl(HomeDisplayViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentHomeData(List<Entry> entries) {
        viewModel.setEntries(entries);
    }

}
