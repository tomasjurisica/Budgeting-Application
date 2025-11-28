package use_case.select_user;

import use_case.select_user.SelectUserOutputData;

public interface SelectUserOutputBoundary {
    void execute(SelectUserInputData inputData);

    void present(SelectUserOutputData outputData);
}
