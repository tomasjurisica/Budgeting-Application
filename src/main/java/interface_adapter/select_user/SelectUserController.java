package interface_adapter.select_user;

import use_case.select_user.*;

public class SelectUserController {

    private final SelectUserInputBoundary interactor;

    public SelectUserController(SelectUserInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(String roommateName) {
        SelectUserInputData inputData = new SelectUserInputData(roommateName);
        interactor.execute(inputData);
    }
}
