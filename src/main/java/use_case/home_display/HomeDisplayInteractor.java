package use_case.home_display;

import interface_adapter.home_display.HomeDisplayViewModel;

public class HomeDisplayInteractor {
    private final HomeDisplayViewModel viewModel;

    public HomeDisplayInteractor(HomeDisplayViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void execute(HomeDisplayRequestModel request) {
        HomeDisplayResponseModel response = new HomeDisplayResponseModel(request.getEntries());
        viewModel.setEntries(response.entries);
    }
}
