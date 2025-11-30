package use_case.home_display;

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
