package use_case.home_display;

import interface_adapter.home_page.HomePageViewModel;

public class HomeDisplayInteractor {
    private final interface_adapter.home_page.HomePageViewModel viewModel;

    public HomeDisplayInteractor(HomePageViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void execute(HomeDisplayRequestModel request) {
        HomeDisplayResponseModel response = new HomeDisplayResponseModel(request.getEntries());
        viewModel.setEntries(response.entries);
    }
}
