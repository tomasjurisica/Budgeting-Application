package use_case.select_user;

public class SelectUserInteractor implements SelectUserInputBoundary {

    private final SelectUserOutputBoundary presenter;

    public SelectUserInteractor(SelectUserOutputBoundary presenter) {
        this.presenter = presenter;
    }

    @Override
    public void execute(SelectUserInputData inputData) {
        // Create output data with roommate selected
        SelectUserOutputData outputData =
                new SelectUserOutputData(inputData.roommateName());

        presenter.present(outputData);
    }
}
