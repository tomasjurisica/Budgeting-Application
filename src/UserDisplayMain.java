import BudgetingObjects.User;
import BudgetingObjects.UserDisplayGUI;
import data_access.UserEntryDataAccessObject;
import interface_adapters.add_entry.AddEntryController;
import interface_adapters.add_entry.AddEntryPresenter;
import interface_adapters.add_entry.AddEntryViewModel;
import use_case.add_entry.AddEntryInteractor;

import javax.swing.*;

public class UserDisplayMain {
    public static void main(String[] args) {

        User user = new User("John Doe");

        UserEntryDataAccessObject userDAO = new UserEntryDataAccessObject(user);

        AddEntryViewModel viewModel = new AddEntryViewModel();
        AddEntryPresenter presenter = new AddEntryPresenter(viewModel);

        AddEntryInteractor interactor = new AddEntryInteractor(userDAO, presenter);

        AddEntryController controller = new AddEntryController(interactor);

        SwingUtilities.invokeLater(() -> {
            UserDisplayGUI gui = new UserDisplayGUI(user, controller);
            gui.setVisible(true);
        });
    }
}
