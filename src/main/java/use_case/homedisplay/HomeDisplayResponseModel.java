package use_case.homedisplay;

import entity.Entry;
import java.util.List;

public class HomeDisplayResponseModel {
    public final List<Entry> entries;

    public HomeDisplayResponseModel(List<Entry> entries) {
        this.entries = entries;
    }
}
