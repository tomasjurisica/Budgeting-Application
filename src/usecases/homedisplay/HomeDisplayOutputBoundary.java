package usecases.homedisplay;

import BudgetingObjects.Entry;
import java.util.List;

public interface HomeDisplayOutputBoundary {
    void presentHomeData(List<Entry> entries);
}
