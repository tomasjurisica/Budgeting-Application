package use_case.household;

import entity.Household;
import entity.User;
import use_case.add_user.AddUserInputBoundary;
import use_case.add_user.AddUserInputData;
import use_case.add_user.AddUserOutputBoundary;
import use_case.add_user.AddUserOutputData;

public class AddUserInteractor implements AddUserInputBoundary {

    private final Household household;
    private final AddUserOutputBoundary outputBoundary;

    public AddUserInteractor(Household household, AddUserOutputBoundary outputBoundary) {
        this.household = household;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void addUser(AddUserInputData inputData) {
        // Business logic: create User and add to household
        User user = new User(inputData.getName());
        household.addUser(user);

        // Prepare output
        AddUserOutputData outputData = new AddUserOutputData(user);
        outputBoundary.present(outputData);
    }
}
