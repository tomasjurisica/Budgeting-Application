package use_case.homedisplay;

import viewmodels.HomePageViewModel;

public class HomeDisplayInteractor {
    private final HomePageViewModel viewModel;

    public HomeDisplayInteractor(HomePageViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void execute(HomeDisplayRequestModel request) {
        HomeDisplayResponseModel response = new HomeDisplayResponseModel(request.getEntries());
        viewModel.setEntries(response.entries);
    }
}
