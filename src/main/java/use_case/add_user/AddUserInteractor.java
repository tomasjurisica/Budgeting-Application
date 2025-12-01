package use_case.add_user;

import entity.Household;
import entity.User;
// FIX: Import the Interface, NOT the FileUserDataAccessObject class
import use_case.add_user.AddUserDataAccessInterface;

public class AddUserInteractor implements AddUserInputBoundary {

    private final Household household;
    private final AddUserOutputBoundary outputBoundary;
    // FIX: Depend on the Interface
    private final AddUserDataAccessInterface userDAO;

    // FIX: Constructor takes the Interface
    public AddUserInteractor(Household household, AddUserOutputBoundary outputBoundary,
                             AddUserDataAccessInterface userDAO) {
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