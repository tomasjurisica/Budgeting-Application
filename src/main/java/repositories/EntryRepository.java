//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package repositories;

import entity.Entry;
import java.util.List;

public interface EntryRepository {
    void addEntry(Entry var1);

    List<Entry> GetAllEntries();
}
