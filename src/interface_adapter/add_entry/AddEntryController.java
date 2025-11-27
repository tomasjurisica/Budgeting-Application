package interface_adapter.add_entry;

import use_case.add_entry.AddEntryInputBoundary;
import use_case.add_entry.AddEntryInputData;

import java.time.LocalDate;
import java.util.List;

public class AddEntryController {
    private final AddEntryInputBoundary interactor;

    public AddEntryController(AddEntryInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(String name, String category, LocalDate date,
                        float total, List<String> userNames, float[] percents) {
        AddEntryInputData inputData =
                new AddEntryInputData(name, category, date, total, userNames, percents);
        interactor.execute(inputData);
    }
}
