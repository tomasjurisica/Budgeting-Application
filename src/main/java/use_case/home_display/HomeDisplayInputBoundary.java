package use_case.home_display;

import entity.Household;

public interface HomeDisplayInputBoundary {
    void fetchHomeData(Household household);
}
