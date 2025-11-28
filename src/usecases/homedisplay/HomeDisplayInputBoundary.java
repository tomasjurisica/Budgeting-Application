package usecases.homedisplay;

import BudgetingObjects.Household;

public interface HomeDisplayInputBoundary {
    void fetchHomeData(Household household);
}
