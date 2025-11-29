package use_case.homedisplay;

import entity.Entry;
import java.util.List;

public interface HomeDisplayOutputBoundary {
    void presentHomeData(List<Entry> entries);
}
