package use_case.add_entry;

import BudgetingObjects.Entry;

import java.util.List;

public interface AddEntryUserDataAccessInterface {

    void addEntry(Entry entry);

    List<Entry> getAllEntries();
}
