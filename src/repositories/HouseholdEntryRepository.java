//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package repositories;

import BudgetingObjects.Entry;
import BudgetingObjects.Household;
import BudgetingObjects.User;
import java.util.ArrayList;

public class HouseholdEntryRepository implements EntryRepository {
    private Household household;

    public HouseholdEntryRepository(Household household) {
        this.household = household;
    }

    public ArrayList<Entry> GetAllEntries() {
        ArrayList<Entry> allEntries = new ArrayList();

        for(User u : this.household.getUsers()) {
            allEntries.addAll(u.getEntries());
        }

        return allEntries;
    }

    public void addEntry(Entry entry) {
        if (!this.household.getUsers().isEmpty()) {
            ((User)this.household.getUsers().get(0)).addEntry(entry);
        }

    }
}
