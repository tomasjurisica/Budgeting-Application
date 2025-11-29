package use_case.add_user;

import entity.Household;
import entity.User;
import data_access.FileUserDataAccessObject;

public class AddUserInteractor implements AddUserInputBoundary {

    private final Household household;
    private final AddUserOutputBoundary outputBoundary;
    private final FileUserDataAccessObject userDAO;

    public AddUserInteractor(Household household, AddUserOutputBoundary outputBoundary, FileUserDataAccessObject userDAO) {
        this.household = household;
        this.outputBoundary = outputBoundary;
        this.userDAO = userDAO;
    }

    @Override
    public void addUser(AddUserInputData inputData) {
        String name = inputData.name();

        // Check if a user with the same name already exists in the household
        for (User existingUser : household.getUsers()) {
            if (existingUser.getName().equalsIgnoreCase(name)) {
                outputBoundary.prepareFailView("A roommate with the name \"" + name + "\" already exists.");
                return;
            }
        }

        // Create new user and add to household
        User user = new User(name);
        household.addUser(user);

        // Persist to JSON
        userDAO.save(household);

        // Update the ViewModel/UI
        AddUserOutputData outputData = new AddUserOutputData(user);
        outputBoundary.present(outputData);
    }
}
