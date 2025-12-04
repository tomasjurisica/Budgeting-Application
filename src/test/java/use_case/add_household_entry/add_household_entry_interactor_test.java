package use_case.add_household_entry;

import entity.*;
import interface_adapter.add_household_entry.AddHouseholdEntryPresenter;
import interface_adapter.add_household_entry.AddHouseholdEntryViewModel;
import data_access.FileUserDataAccessObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class add_household_entry_interactor_test {
    private AddHouseholdEntryOutputBoundary outputBoundary = new AddHouseholdEntryOutputBoundary() {
        @Override
        public void prepareSuccessView(AddHouseholdEntryOutputData outputData) {

        }

        @Override
        public void prepareFailView(String errorMessage) {

        }
    };
    private AddHouseholdEntryViewModel viewModel;
    private AddHouseholdEntryInteractor interactor;
    private FileUserDataAccessObject dAO;

    @BeforeEach
    void setup() {
        dAO = new FileUserDataAccessObject("test.json", new HouseholdFactory()) {
            // Override save to do nothing for testing
            @Override
            public void addHouseholdEntry (SharedEntry newEntry){
                // No file read/write
            }

            @Override
            public ArrayList<User> getUsers() {
                // No file read/write
                ArrayList<User> users = new ArrayList<>();
                users.add(new User("Person1"));
                users.add(new User("Person2"));
                users.add(new User("Person3"));
                return users;
            }
        };
        viewModel = new AddHouseholdEntryViewModel();
        interactor = new AddHouseholdEntryInteractor(dAO, outputBoundary);
    }

    @Test
    void addHouseholdEntryTest() {
        String name = "Test";
        String category = "Tests";
        LocalDate date = LocalDate.of(2025, 5, 12);
        float total = 100.0f;
        List<String> userNames = new ArrayList<>();
        userNames.add("Person1");
        userNames.add("Person2");
        String[] allUserNames = {"Person1", "Person2", "Person3"};
        float[] percents = {25.0f, 75.0f};

        AddHouseholdEntryOutputData outputData = new AddHouseholdEntryOutputData(allUserNames);

        AddHouseholdEntryInputData inputData = new AddHouseholdEntryInputData(name,
                category, date, total, userNames, percents);

        interactor.execute(inputData);

        outputData.getUserNames();
    }
}
