package interface_adapter.add_household_entry;

import interface_adapter.ViewModel;

public class AddHouseholdEntryViewModel extends ViewModel<AddHouseholdEntryState> {
    public AddHouseholdEntryViewModel() {
        super("add entry");
        setState(new AddHouseholdEntryState());
    }
}