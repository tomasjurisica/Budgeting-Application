package use_case.homedisplay;

import entity.Entry;

import java.util.List;
import java.util.Map;

public class HomeDisplayOutputData {
    public final List<Entry> entries;
    public final Map<String, Float> categoryTotals;

    public HomeDisplayOutputData(List<Entry> entries, Map<String, Float> categoryTotals) {
        this.entries = entries;
        this.categoryTotals = categoryTotals;
    }
}
