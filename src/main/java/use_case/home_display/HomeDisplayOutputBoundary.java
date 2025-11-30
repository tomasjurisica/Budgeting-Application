package use_case.home_display;

import entity.Entry;
import java.util.List;

public interface HomeDisplayOutputBoundary {
    void presentHomeData(List<Entry> entries);
}
