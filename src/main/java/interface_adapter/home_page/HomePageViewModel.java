package interface_adapter.home_page;

import interface_adapter.ViewModel;
import use_case.select_user.*;

public class HomePageViewModel extends ViewModel<HomePageState>
        implements SelectUserOutputBoundary {

    private SelectUserInputBoundary selectUserInteractor;

    public HomePageViewModel() {
        super("home page");
        setState(new HomePageState());
    }

    public void setSelectUserInteractor(SelectUserInputBoundary interactor) {
        this.selectUserInteractor = interactor;
    }

    // Called by the controller
    public void selectUser(String roommateName) {
        if (roommateName == null || roommateName.trim().isEmpty() ||
                selectUserInteractor == null) return;

        selectUserInteractor.execute(
                new SelectUserInputData(roommateName)
        );
    }

    @Override
    public void execute(SelectUserInputData inputData) {

    }

    // Called by the interactor to update the UI/state
    @Override
    public void present(SelectUserOutputData outputData) {
        getState().setSelectedUser(outputData.roommateName());
        firePropertyChange();
    }
}
