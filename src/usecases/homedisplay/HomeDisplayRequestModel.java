package usecases.homedisplay;

import java.util.List;
import BudgetingObjects.Entry;

public class HomeDisplayRequestModel {
    private final List<Entry> entries;

    public HomeDisplayRequestModel(List<Entry> entries) {
        this.entries = entries;
    }

    public List<Entry> getEntries() {
        return entries;
    }
}
