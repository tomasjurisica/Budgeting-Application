package interface_adapter.individual_entry;

import use_case.individual_entry.add_entry.AddIndividualEntryOutputBoundary;
import use_case.individual_entry.add_entry.AddIndividualEntryOutputData;

public class IndividualEntryPresenter
        implements AddIndividualEntryOutputBoundary {

    private final IndividualEntryViewModel viewModel;

    public IndividualEntryPresenter(IndividualEntryViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(AddIndividualEntryOutputData data) {
        viewModel.setLastOutput(data);
        // If you want to trigger UI updates, follow the pattern used in other presenters,
        // e.g. firePropertyChange on a ViewModel.
    }

    @Override
    public void prepareFailView(String errorMessage) {
        AddIndividualEntryOutputData data =
                new AddIndividualEntryOutputData(
                        "", "", 0, null, errorMessage, true);
        viewModel.setLastOutput(data);
    }
}
