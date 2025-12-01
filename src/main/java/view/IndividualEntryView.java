package view;
import data_access.FileUserDataAccessObject;
import entity.IndividualEntry;
import entity.Entry;
import use_case.individual_entry.IndividualEntryDataAccessInterface;
import use_case.individual_entry.add_entry.AddIndividualEntryInputBoundary;
import use_case.individual_entry.add_entry.AddIndividualEntryInputData;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class IndividualEntryView extends JFrame {

    private final String username;
    private final IndividualEntryDataAccessInterface dao;
    private final AddIndividualEntryInputBoundary interactor;
    private final DefaultTableModel model;

    public IndividualEntryView(String username,
                               IndividualEntryDataAccessInterface dao,
                               AddIndividualEntryInputBoundary interactor) {
        this.username = username;
        this.dao = dao;
        this.interactor = interactor;

        setTitle("Individual Entries - " + username);
        setSize(600, 400);
        setLocationRelativeTo(null);

        // ----- Table -----
        String[] columns = {"Name", "Category", "Amount", "Date"};
        model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        // ----- Buttons -----
        JButton addButton = new JButton("Add Entry");
        addButton.addActionListener(e -> onAddEntry());

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(addButton);
        bottomPanel.add(closeButton);

        // ----- Layout -----
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        reloadEntries();
    }

    private void reloadEntries() {
        model.setRowCount(0);
        List<IndividualEntry> entries = dao.loadEntriesForUser(username);
        for (IndividualEntry entry : entries) {
            model.addRow(new Object[]{
                    entry.getName(),
                    entry.getCategory(),
                    entry.getAmount(),
                    entry.getDate()
            });
        }
    }

    private void onAddEntry() {
        JTextField nameField = new JTextField();
        JTextField categoryField = new JTextField();
        JTextField amountField = new JTextField();

        Object[] message = {
                "Name:", nameField,
                "Category:", categoryField,
                "Amount:", amountField
        };

        int option = JOptionPane.showConfirmDialog(
                this,
                message,
                "Add Individual Entry",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (option == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText().trim();
                String category = categoryField.getText().trim();
                float amount = Float.parseFloat(amountField.getText().trim());
                LocalDate date = LocalDate.now();

                AddIndividualEntryInputData inputData =
                        new AddIndividualEntryInputData(username, name, category, amount, date);

                interactor.execute(inputData);

                reloadEntries();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Invalid amount.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
}
