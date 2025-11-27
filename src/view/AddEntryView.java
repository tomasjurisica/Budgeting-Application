package view;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

// CA imports
import interface_adapter.add_entry.AddEntryViewModel;
import interface_adapter.add_entry.AddEntryState;
import use_case.add_entry.AddEntryOutputData;

// will be removed
import BudgetingObjects.*;


public class AddEntryView extends JFrame implements PropertyChangeListener {
    // panels
    private final JTextField amountField = new JTextField(10);
    private final JPanel percentagePanel = new JPanel(new GridLayout(0, 2));
    private final List<JTextField> percentageFields = new ArrayList<>();

    private final List<JCheckBox> userCheckBoxes = new ArrayList<>();

    private final JPanel userSelectPanel = new JPanel();

    // username saving
    private final List<String> selectedUserNames = new ArrayList<>();

    // CA
    private final AddEntryViewModel viewModel;

    public AddEntryView(List<User> users, AddEntryViewModel viewModel) {
        // CA
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);

        // Init
        setTitle("Add New Entry");
        setSize(350, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel main = new JPanel(new BorderLayout());
        // need to add user list

        // Enter name
        JPanel namePanel = new JPanel();
        namePanel.add(new JLabel("Entry Name:"));
        JTextField nameField = new JTextField(12);
        namePanel.add(nameField);

        // Enter category
        JPanel categoryPanel = new JPanel();
        categoryPanel.add(new JLabel("Entry Category:"));
        JTextField categoryField = new JTextField(12);
        categoryPanel.add(categoryField);

        // Enter date
        JPanel datePanel = new JPanel();
        datePanel.add(new JLabel("Date of Entry (DD/MM/YYYY)"));
        JTextField dayField = new JTextField(2);
        datePanel.add(dayField);
        datePanel.add(new JLabel("/"));
        JTextField monthField = new JTextField(2);
        datePanel.add(monthField);
        datePanel.add(new JLabel("/"));
        JTextField yearField = new JTextField(4);
        datePanel.add(yearField);
        datePanel.add(amountField);

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

        main.add(inputs, BorderLayout.NORTH);

        // Pick users to split between
        userSelectPanel.setLayout(new BoxLayout(userSelectPanel, BoxLayout.Y_AXIS));
        JScrollPane scroll = new JScrollPane(userSelectPanel);
        main.add(scroll, BorderLayout.CENTER);


        // Lock in selected users
        JButton selectButton = new JButton("Select User(s)");
        selectButton.addActionListener(e -> percentageInputs());

        JPanel center = new JPanel(new BorderLayout());
        center.add(scroll, BorderLayout.CENTER);
        center.add(selectButton, BorderLayout.SOUTH);
        main.add(center, BorderLayout.CENTER);


        // Enter percent for each user
        JPanel southWrapper = new JPanel(new BorderLayout());
        southWrapper.add(new JLabel("For each user, enter percent owed"), BorderLayout.NORTH);
        southWrapper.add(percentagePanel, BorderLayout.CENTER);


        // Finalize and add entry
        JButton addButton = new JButton("Add Entry");
        southWrapper.add(addButton, BorderLayout.SOUTH);
        addButton.addActionListener(e -> onAdd());

        main.add(southWrapper, BorderLayout.SOUTH);

        add(main);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        AddEntryState state = viewModel.getState();

        if (state.isHasError()) {
            JOptionPane.showMessageDialog(
                    this,
                    state.getErrorMessage(),
                    "Error",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        String[] userNames = state.getAllUserNames();

        for (String userName : userNames) {
            JCheckBox cb = new JCheckBox(userName);
            cb.setFont(cb.getFont().deriveFont(18f));
            userCheckBoxes.add(cb);
            userSelectPanel.add(cb);
        }
    }



    private void percentageInputs() {
        selectedUserNames.clear();
        percentagePanel.removeAll();
        percentageFields.clear();

        for (int i = 0; i < userCheckBoxes.size(); i++) {
            JCheckBox cb = userCheckBoxes.get(i);
            if (cb.isSelected()) {
                String userName = "Fill with proper input";

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

    private void onAdd() {
        try {
            // Get total, round to 2 decimals
            BigDecimal contribution = BigDecimal.valueOf(Float.parseFloat(amountField.getText()));
            contribution = contribution.setScale(2, RoundingMode.HALF_UP);
            float total = contribution.floatValue();


            /*
               !!!
               Need to check list of users isn't empty
               !!!
             */

            // Get users and their percents

            // Need to hook up to CA
            System.out.println("\n" + total + "\n");


        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
