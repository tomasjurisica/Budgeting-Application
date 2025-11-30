package use_case.add_user;

public interface AddUserOutputBoundary {
    void present(AddUserOutputData outputData);

    /**
     * Prepares the failure view for the Add User Use Case.
     *
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
