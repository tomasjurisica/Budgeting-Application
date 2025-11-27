package use_case.add_entry;

import BudgetingObjects.Entry;
import BudgetingObjects.Household;
import BudgetingObjects.User;
import BudgetingObjects.SharedEntry;



import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddEntryInteractor implements AddEntryInputBoundary{

    private final AddEntryDataAccessInterface dataAcess;
    private final AddEntryOutputBoundary presenter;

    public AddEntryInteractor(AddEntryDataAccessInterface dataAcess,
                              AddEntryOutputBoundary presenter) {
        this.dataAcess = dataAcess;
        this.presenter = presenter;
    }

    public void execute(AddEntryInputData inputData) {
        // Pull data inputData
        String name = inputData.getName();
        String category = inputData.getCategory();
        LocalDate date = inputData.getDate();
        float total = inputData.getTotal();
        List<String> userNames = inputData.getUserNames();
        float[] percents = inputData.getPercents();

        // Get users from file
        List<User> users = new ArrayList<>();
        for (String userName : userNames) {

        }

        // Create and add shared entry
        Household household = users.getFirst().getHousehold();

        Entry headEntry = new Entry(name, category, total, date);
        SharedEntry addedEntry = new SharedEntry(headEntry, users, percents);

        household.addSharedEntry(addedEntry);


    }
}
