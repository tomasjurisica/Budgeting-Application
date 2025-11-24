package interface_adapter.select_user;

import interface_adapter.ViewManagerModel;
import interface_adapter.home_page.HomePageViewModel;
import use_case.select_user.*;
import use_case.select_user.SelectUserOutputData;

public class SelectUserPresenter implements SelectUserOutputBoundary {

    private final HomePageViewModel homePageViewModel;
    private final ViewManagerModel viewManagerModel;

    public SelectUserPresenter(HomePageViewModel homePageViewModel,
                               ViewManagerModel viewManagerModel) {
        this.homePageViewModel = homePageViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void execute(SelectUserInputData inputData) {

    }

    @Override
    public void present(SelectUserOutputData outputData) {

        // Update selected roommate in homepage VM
        homePageViewModel.present(outputData);
    }
}
