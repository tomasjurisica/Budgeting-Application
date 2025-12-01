package use_case.individual_entry.add_entry;

public interface AddIndividualEntryOutputBoundary {

    void prepareSuccessView(AddIndividualEntryOutputData outputData);

    void prepareFailView(String errorMessage);
}
