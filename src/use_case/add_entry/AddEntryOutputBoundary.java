package use_case.add_entry;

public interface AddEntryOutputBoundary {

    void prepareSuccessView(AddEntryOutputData outputData);

    void prepareFailView(String errorMessage);
}
