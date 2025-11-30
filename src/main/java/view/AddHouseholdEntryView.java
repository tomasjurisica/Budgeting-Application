package view;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// CA imports
import interface_adapter.add_household_entry.AddHouseholdEntryViewModel;
import interface_adapter.add_household_entry.AddHouseholdEntryState;
import interface_adapter.add_household_entry.AddHouseholdEntryController;
import interface_adapter.add_household_entry.UndoHouseholdEntryController;
import interface_adapter.ViewManagerModel;
import entity.Household;
import entity.User;


public class AddHouseholdEntryView extends JPanel implements PropertyChangeListener {
    private final String viewName = "add entry";

    // panels
    private final JTextField nameField = new JTextField(12);
    private final JTextField categoryField = new JTextField(12);
    private final JTextField dayField = new JTextField(2);
    private final JTextField monthField = new JTextField(2);
    private final JTextField yearField = new JTextField(4);
    private final JTextField amountField = new JTextField(10);
    private final JPanel percentagePanel = new JPanel(new GridLayout(0, 2));
    private final List<JTextField> percentageFields = new ArrayList<>();

    private final List<JCheckBox> userCheckBoxes = new ArrayList<>();

    private final JPanel userSelectPanel = new JPanel();

    // username saving
    private final List<String> selectedUserNames = new ArrayList<>();

    // CA
    private final AddHouseholdEntryViewModel viewModel;
    private AddHouseholdEntryController addHouseholdEntryController;
    private UndoHouseholdEntryController undoController;
    private ViewManagerModel viewManagerModel;

    public AddHouseholdEntryView(AddHouseholdEntryViewModel viewModel) {
        // CA
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);

        // Init
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(350, 600));

        // Top bar with back button
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(42, 42, 42));
        topBar.setPreferredSize(new Dimension(350, 50));
        JButton backButton = new JButton("← Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setFocusPainted(false);
        backButton.setForeground(Color.white);
        backButton.setContentAreaFilled(false);
        backButton.setBorderPainted(false);
        backButton.addActionListener(e -> navigateBack());
        topBar.add(backButton, BorderLayout.WEST);
        this.add(topBar, BorderLayout.NORTH);

        // Enter name
        JPanel namePanel = new JPanel();
        namePanel.add(new JLabel("Entry Name:"));
        namePanel.add(nameField);

        // Enter category
        JPanel categoryPanel = new JPanel();
        categoryPanel.add(new JLabel("Entry Category:"));
        categoryPanel.add(categoryField);

        // Enter date
        JPanel datePanel = new JPanel();
        datePanel.add(new JLabel("Date of Entry (DD/MM/YYYY):"));
        datePanel.add(dayField);
        datePanel.add(new JLabel("/"));
        datePanel.add(monthField);
        datePanel.add(new JLabel("/"));
        datePanel.add(yearField);

        // Enter the total amount
        JPanel amountPanel = new JPanel();
        amountPanel.add(new JLabel("Amount of Entry (Up to 2 Decimals):"));
        amountPanel.add(amountField);

        JPanel inputs = new JPanel();
        inputs.setLayout(new BoxLayout(inputs, BoxLayout.Y_AXIS));
        inputs.add(namePanel);
        inputs.add(categoryPanel);
        inputs.add(datePanel);
        inputs.add(amountPanel);
        this.add(inputs, BorderLayout.NORTH);

        // Pick users to split between
        userSelectPanel.setLayout(new BoxLayout(userSelectPanel, BoxLayout.Y_AXIS));
        JScrollPane scroll = new JScrollPane(userSelectPanel);

        // Lock in selected users
        JButton selectButton = new JButton("Select User(s)");
        selectButton.addActionListener(e -> percentageInputs());

        JPanel center = new JPanel(new BorderLayout());
        center.add(scroll, BorderLayout.CENTER);
        center.add(selectButton, BorderLayout.SOUTH);
        this.add(center, BorderLayout.CENTER);

        // Enter percent for each user
        JPanel southWrapper = new JPanel(new BorderLayout());
        southWrapper.add(new JLabel("For each user, enter percent owed"), BorderLayout.NORTH);
        southWrapper.add(percentagePanel, BorderLayout.CENTER);

        // Finalize and add entry
        JButton addButton = new JButton("Add Entry");
        addButton.addActionListener(e -> onAdd());

        // Memento Pattern: Undo/Redo buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton undoButton = new JButton("Undo");
        JButton redoButton = new JButton("Redo");
        undoButton.addActionListener(e -> onUndo());
        redoButton.addActionListener(e -> onRedo());
        buttonPanel.add(undoButton);
        buttonPanel.add(redoButton);
        buttonPanel.add(addButton);

        southWrapper.add(buttonPanel, BorderLayout.SOUTH);

        this.add(southWrapper, BorderLayout.SOUTH);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        AddHouseholdEntryState state = viewModel.getState();

        if (state.isHasError()) {
            JOptionPane.showMessageDialog(
                    this,
                    state.getErrorMessage(),
                    "Error",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        // If entry was added successfully (no error and has user names), show success message
        // and optionally navigate back (user can use back button)
        if (!state.isHasError() && state.getAllUserNames() != null && state.getAllUserNames().length > 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Entry added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );
            // Clear fields
            nameField.setText("");
            categoryField.setText("");
            dayField.setText("");
            monthField.setText("");
            yearField.setText("");
            amountField.setText("");
            selectedUserNames.clear();
            percentagePanel.removeAll();
            percentageFields.clear();
            for (JCheckBox cb : userCheckBoxes) {
                cb.setSelected(false);
            }
        }
    }

    private void navigateBack() {
        if (viewManagerModel != null) {
            viewManagerModel.setState("home page");
            viewManagerModel.firePropertyChange();
        }
    }

    public void setViewManagerModel(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
    }

    /**
     * After hitting "select users", create new window with option to input percents
     */
    private void percentageInputs() {
        selectedUserNames.clear();
        percentagePanel.removeAll();
        percentageFields.clear();

        for (int i = 0; i < userCheckBoxes.size(); i++) {
            JCheckBox cb = userCheckBoxes.get(i);
            if (cb.isSelected()) {
                String userName = cb.getText();

                percentagePanel.add(new JLabel(userName));
                JTextField percent = new JTextField("0", 3);

                percentageFields.add(percent);
                percentagePanel.add(percent);

                selectedUserNames.add(userName);
            }
        }

        percentagePanel.revalidate();
        percentagePanel.repaint();
    }

    /**
     * Loads users from household and displays them as checkboxes
     */
    public void loadUsersFromHousehold(Household household) {
        if (household == null) {
            return;
        }

        userSelectPanel.removeAll();
        userCheckBoxes.clear();

        List<User> users = household.getUsers();
        for (User user : users) {
            JCheckBox cb = new JCheckBox(user.getName());
            cb.setFont(cb.getFont().deriveFont(18f));
            userCheckBoxes.add(cb);
            userSelectPanel.add(cb);
        }

        userSelectPanel.revalidate();
        userSelectPanel.repaint();
    }

    public String getViewName() {
        return viewName;
    }

    private void onAdd() {
        if (selectedUserNames.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Error: No users selected");
            return;
        }

        try {
            // Get information name/category/date
            String name = nameField.getText();

            String category = categoryField.getText();

            int day = Integer.parseInt(dayField.getText());
            int month = Integer.parseInt(monthField.getText());
            int year = Integer.parseInt(yearField.getText());

            // Create date object
            LocalDate entryDate = LocalDate.of(year, month, day);

            // Get contribution, round to 2 decimal places
            BigDecimal contribution = BigDecimal.valueOf(Float.parseFloat(amountField.getText()));
            contribution = contribution.setScale(2, RoundingMode.HALF_UP);
            float total = contribution.floatValue();

            // turn percents to string array
            float[] percents = new float[percentageFields.size()];

            for (int i = 0; i < percentageFields.size(); i++) {
                percents[i] = Float.parseFloat(percentageFields.get(i).getText());
            }

            // Push through to CA/Controller
            addHouseholdEntryController.execute(name, category, entryDate, total, selectedUserNames, percents);

        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public void setAddHouseholdEntryController(AddHouseholdEntryController addHouseholdEntryController) {
        this.addHouseholdEntryController = addHouseholdEntryController;
    }

    public void setUndoController(UndoHouseholdEntryController undoController) {
        this.undoController = undoController;
    }

    /**
     * Handles undo action - part of Memento pattern implementation
     */
    private void onUndo() {
        if (undoController != null) {
            boolean success = undoController.execute();
            if (success) {
                JOptionPane.showMessageDialog(
                        this,
                        "Entry undone successfully!",
                        "Undo",
                        JOptionPane.INFORMATION_MESSAGE
                );
                // Refresh the view if needed
                if (viewManagerModel != null) {
                    viewManagerModel.firePropertyChange();
                }
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Nothing to undo.",
                        "Undo",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        }
    }

    /**
     * Handles redo action - part of Memento pattern implementation
     */
    private void onRedo() {
        if (undoController != null) {
            boolean success = undoController.redo();
            if (success) {
                JOptionPane.showMessageDialog(
                        this,
                        "Entry redone successfully!",
                        "Redo",
                        JOptionPane.INFORMATION_MESSAGE
                );
                // Refresh the view if needed
                if (viewManagerModel != null) {
                    viewManagerModel.firePropertyChange();
                }
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Nothing to redo.",
                        "Redo",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        }
    }
}
