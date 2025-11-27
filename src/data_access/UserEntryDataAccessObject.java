package data_access;

import BudgetingObjects.Entry;
import BudgetingObjects.User;
import use_case.add_entry.AddEntryUserDataAccessInterface;

import java.util.ArrayList;
import java.util.List;

public class UserEntryDataAccessObject implements AddEntryUserDataAccessInterface {

    private final User user;

    public UserEntryDataAccessObject(User user) {
        this.user = user;
    }

    @Override
    public void addEntry(Entry entry) {
        user.addEntry(entry);
    }

    @Override
    public List<Entry> getAllEntries() {
        return new ArrayList<>(user.getEntries());
    }
}
