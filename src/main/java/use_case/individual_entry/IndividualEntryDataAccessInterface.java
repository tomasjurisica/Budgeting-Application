package use_case.individual_entry;

import entity.IndividualEntry;
import java.util.List;

public interface IndividualEntryDataAccessInterface {

    void saveEntry(String username, IndividualEntry entry);

    List<IndividualEntry> loadEntriesForUser(String username);
}
