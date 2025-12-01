package interface_adapter.individual_entry;

import use_case.individual_entry.add_entry.AddIndividualEntryOutputBoundary;
import use_case.individual_entry.add_entry.AddIndividualEntryOutputData;

import javax.swing.*;

public class IndividualEntryPopupPresenter implements AddIndividualEntryOutputBoundary {

    @Override
    public void prepareSuccessView(AddIndividualEntryOutputData outputData) {
        // Optional: show a toast / message if you want:
        // JOptionPane.showMessageDialog(null, outputData.getMessage());
        // For now we stay silent on success.
    }

    @Override
    public void prepareFailView(String errorMessage) {
        JOptionPane.showMessageDialog(
                null,
                errorMessage,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
