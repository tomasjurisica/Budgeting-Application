package use_case.homedisplay;

import java.util.List;
import entity.Entry;

public class HomeDisplayRequestModel {
    private final List<Entry> entries;

    public HomeDisplayRequestModel(List<Entry> entries) {
        this.entries = entries;
    }

    public List<Entry> getEntries() {
        return entries;
    }
}
