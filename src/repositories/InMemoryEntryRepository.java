//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package repositories;

import BudgetingObjects.Entry;
import java.util.ArrayList;
import java.util.List;

public class InMemoryEntryRepository implements EntryRepository {
    private final ArrayList<Entry> entries = new ArrayList();

    public synchronized void addEntry(Entry entry) {
        this.entries.add(entry);
    }

    public synchronized List<Entry> GetAllEntries() {
        return new ArrayList(this.entries);
    }

    public synchronized void clear() {
        this.entries.clear();
    }
}
