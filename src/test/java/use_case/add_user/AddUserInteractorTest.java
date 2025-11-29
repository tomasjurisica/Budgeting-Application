package use_case.add_user;

import data_access.FileUserDataAccessObject;
import entity.Household;
import entity.HouseholdFactory;
import entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddUserInteractorTest {

    private Household household;
    private FileUserDataAccessObject userDAO;

    @BeforeEach
    void setUp() {
        household = new Household("Test Household", "test123");
        userDAO = new FileUserDataAccessObject("test.json", new HouseholdFactory()) {
            // Override save to do nothing for testing
            @Override
            public void save(Household household) {
                // No-op
            }
        };
    }

    @Test
    void successAddUserTest() {
        AddUserInputData inputData = new AddUserInputData("Alice");

        AddUserOutputBoundary successPresenter = new AddUserOutputBoundary() {
            @Override
            public void present(AddUserOutputData outputData) {
                assertEquals("Alice", outputData.user().getName());
                assertEquals(1, household.getUsers().size());
                assertEquals("Alice", household.getUsers().get(0).getName());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Unexpected failure: " + error);
            }
        };

        AddUserInputBoundary interactor = new AddUserInteractor(household, successPresenter, userDAO);
        interactor.addUser(inputData);
    }

    @Test
    void failureDuplicateUserTest() {
        // Add an existing user to the household
        household.addUser(new User("Alice"));

        AddUserInputData inputData = new AddUserInputData("Alice");

        AddUserOutputBoundary failurePresenter = new AddUserOutputBoundary() {
            @Override
            public void present(AddUserOutputData outputData) {
                fail("Unexpected success for duplicate user.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("A roommate with the name \"Alice\" already exists.", error);
                assertEquals(1, household.getUsers().size(), "Household should still have only 1 user");
            }
        };

        AddUserInputBoundary interactor = new AddUserInteractor(household, failurePresenter, userDAO);
        interactor.addUser(inputData);
    }
}
