package interface_adapters.add_entry;

import use_case.add_entry.AddEntryInputBoundary;
import use_case.add_entry.AddEntryInputData;

import java.time.LocalDate;

public class AddEntryController {

    private final AddEntryInputBoundary interactor;

    public AddEntryController(AddEntryInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void addEntry(String name, String category, float amount, LocalDate date) {
        AddEntryInputData inputData = new AddEntryInputData(name, category, amount, date);
        interactor.execute(inputData);
    }
}
