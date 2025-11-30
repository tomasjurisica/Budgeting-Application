package use_case.add_household_entry;

import entity.*;
import data_access.FileUserDataAccessObject;
import use_case.login.LoginUserDataAccessInterface;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddHouseholdEntryInteractor implements AddHouseholdEntryInputBoundary {

    private final AddHouseholdEntryDataAccessInterface dataAcess;
    private final AddHouseholdEntryOutputBoundary presenter;
    private HouseholdEntryHistory history; // Optional: for Memento pattern undo/redo

    public AddHouseholdEntryInteractor(AddHouseholdEntryDataAccessInterface dataAcess,
                                       AddHouseholdEntryOutputBoundary presenter) {
        this.dataAcess = dataAcess;
        this.presenter = presenter;
        this.history = null; // Optional - backward compatible
    }

    /**
     * Constructor with optional history for Memento pattern support.
     * If history is provided, mementos will be saved for undo/redo functionality.
     */
    public AddHouseholdEntryInteractor(AddHouseholdEntryDataAccessInterface dataAcess,
                                       AddHouseholdEntryOutputBoundary presenter,
                                       HouseholdEntryHistory history) {
        this.dataAcess = dataAcess;
        this.presenter = presenter;
        this.history = history;
    }

    public void execute(AddHouseholdEntryInputData inputData) {
        // Pull data inputData
        String name = inputData.getName();
        String category = inputData.getCategory();
        LocalDate date = inputData.getDate();
        float total = inputData.getTotal();
        List<String> userNames = inputData.getUserNames();
        float[] percents = inputData.getPercents();

        // If list of users is empty, present fail
        if (userNames.isEmpty()) {
            presenter.prepareFailView("No users selected.");
            return;
        }

        // If percents is over 100, present fail
        float totalPercents = 0;
        for (float percent : percents) {
            totalPercents += percent;
        }

        if (totalPercents > 100) {
            presenter.prepareFailView("Percent total is over 100.");
            return;
        }

        // Get users from file
        List<User> allUsers = dataAcess.getUsers();
        List<User> users = new ArrayList<>();

        // Only keep list of users added to entry
        for (String username : userNames) {
            for (User user : allUsers) {
                if (user.getName().equals(username)) {
                    users.add(user);
                    break;
                }
            }
        }

        // Memento Pattern: Save state before adding entry (if history is available)
        Household currentHousehold = getCurrentHousehold();
        if (history != null && currentHousehold != null) {
            Household.HouseholdMemento memento = currentHousehold.createMemento();
            history.saveMemento(memento);
        }

        // Create shared entry
        Entry headEntry = new Entry(name, category, total, date);
        SharedEntry addedEntry = new SharedEntry(headEntry, users, percents);

        // Write to JSON
        dataAcess.addHouseholdEntry(addedEntry);

        // Prepare success view
        String[] userNamesArray = new String[users.size()];
        for (int i = 0; i < users.size(); i++) {
            userNamesArray[i] = users.get(i).getName();
        }
        AddHouseholdEntryOutputData outputData = new AddHouseholdEntryOutputData(userNamesArray);
        presenter.prepareSuccessView(outputData);

    }

    /**
     * Helper method to get the current household from data access.
     * Used for Memento pattern integration.
     */
    private Household getCurrentHousehold() {
        // Try to get household through data access
        if (dataAcess instanceof FileUserDataAccessObject) {
            FileUserDataAccessObject fileDao = (FileUserDataAccessObject) dataAcess;
            String currentUsername = fileDao.getCurrentUsername();
            if (currentUsername != null) {
                return fileDao.get(currentUsername);
            }
        }
        return null;
    }

    /**
     * Sets the history for undo/redo functionality (Memento pattern).
     * This method allows enabling the Memento pattern after construction.
     */
    public void setHistory(HouseholdEntryHistory history) {
        this.history = history;
    }

    /**
     * Gets the history instance (for undo/redo operations).
     * @return The history instance, or null if not set
     */
    public HouseholdEntryHistory getHistory() {
        return history;
    }
}