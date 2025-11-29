package use_case.add_household_entry;

import entity.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddHouseholdEntryInteractor implements AddHouseholdEntryInputBoundary {

    private final AddHouseholdEntryDataAccessInterface dataAcess;
    private final AddHouseholdEntryOutputBoundary presenter;

    public AddHouseholdEntryInteractor(AddHouseholdEntryDataAccessInterface dataAcess,
                                       AddHouseholdEntryOutputBoundary presenter) {
        this.dataAcess = dataAcess;
        this.presenter = presenter;
    }

    public void execute(AddHouseholdEntryInputData inputData) {
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

        household.addHouseholdEntry(addedEntry);

    }
}