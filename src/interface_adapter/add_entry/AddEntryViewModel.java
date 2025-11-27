package interface_adapter.add_entry;

import interface_adapter.ViewModel;

public class AddEntryViewModel extends ViewModel<AddEntryState> {
    public AddEntryViewModel() {
        super("add entry");
        setState(new AddEntryState());
    }
}
