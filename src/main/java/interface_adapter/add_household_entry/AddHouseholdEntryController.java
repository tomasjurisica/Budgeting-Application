package interface_adapter.add_household_entry;

import use_case.add_household_entry.AddHouseholdEntryInputBoundary;
import use_case.add_household_entry.AddHouseholdEntryInputData;

import java.time.LocalDate;
import java.util.List;

public class AddHouseholdEntryController {
    private final AddHouseholdEntryInputBoundary interactor;

    public AddHouseholdEntryController(AddHouseholdEntryInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(String name, String category, LocalDate date,
                        float total, List<String> userNames, float[] percents) {
        AddHouseholdEntryInputData inputData =
                new AddHouseholdEntryInputData(name, category, date, total, userNames, percents);
        interactor.execute(inputData);
    }
}