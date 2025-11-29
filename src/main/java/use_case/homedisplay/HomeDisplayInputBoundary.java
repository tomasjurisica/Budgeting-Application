package use_case.homedisplay;

import entity.Household;

public interface HomeDisplayInputBoundary {
    void fetchHomeData(Household household);
}
