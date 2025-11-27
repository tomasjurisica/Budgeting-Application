package interface_adapters.add_entry;

import use_case.add_entry.AddEntryOutputBoundary;
import use_case.add_entry.AddEntryOutputData;

public class AddEntryPresenter implements AddEntryOutputBoundary {

    private final AddEntryViewModel viewModel;

    public AddEntryPresenter(AddEntryViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(AddEntryOutputData outputData) {
        viewModel.setLastMessage(outputData.getMessage());
    }

    @Override
    public void prepareFailView(String errorMessage) {
        viewModel.setLastMessage(errorMessage);
    }
}
