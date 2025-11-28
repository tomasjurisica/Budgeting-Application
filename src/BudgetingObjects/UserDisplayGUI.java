package BudgetingObjects;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import interface_adapters.add_entry.AddEntryController;
import javax.swing.BoxLayout;
import java.awt.Component;


public class UserDisplayGUI extends JFrame {
    private final User user;
    private final DefaultTableModel model;
    private final JLabel statusLabel;
    private JTable table;
    private final AddEntryController addEntryController;

    public UserDisplayGUI(User user, AddEntryController addEntryController) {
        this.user = user;
        this.addEntryController = addEntryController;

        setTitle("My Finances - " + user.getName());
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // ----- Header -----
        JLabel titleLabel = new JLabel("My Finances", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));

        JLabel userLabel = new JLabel("User: " + user.getName(), SwingConstants.CENTER);
        userLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(titleLabel);
        headerPanel.add(userLabel);

        // ----- Table -----
        String[] columns = {"Name", "Amount", "Date", "Category"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        table.setRowSelectionAllowed(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);

        // ----- Status -----
        statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setFont(new Font("SansSerif", Font.ITALIC, 13));
        statusLabel.setForeground(Color.GRAY);

        // ----- Buttons -----
        JButton addButton = new JButton("Add Entry");
        addButton.setForeground(new Color(0, 150, 0));

        JButton categoryButton = new JButton("Category Display");
        categoryButton.setForeground(Color.BLUE);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(categoryButton);

        // ----- Layout -----
        setLayout(new BorderLayout());
        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);
        add(buttonPanel, BorderLayout.PAGE_END);

        // Load user data
        refreshEntries();

        // ----- Button Logic -----
        addButton.addActionListener(e -> {
            JTextField nameField = new JTextField();
            JTextField categoryField = new JTextField();
            JTextField amountField = new JTextField();

            Object[] message = {
                    "Name:", nameField,
                    "Category:", categoryField,
                    "Amount:", amountField
            };

            int option = JOptionPane.showConfirmDialog(this, message,
                    "Add New Entry", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {
                try {
                    String name = nameField.getText().trim();
                    String category = categoryField.getText().trim();
                    float amount = Float.parseFloat(amountField.getText().trim());

                    addEntryController.addEntry(name, category, amount, LocalDate.now());

                    refreshEntries();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid amount format!");
                }
            }
        });

        categoryButton.addActionListener(e -> {
            String category = JOptionPane.showInputDialog(this,
                    "Enter category name:");
            if (category != null) {
                ArrayList<Entry> filtered = user.getEntriesFromCategory(category);
                if (filtered.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "No entries found for category: " + category);
                } else {
                    StringBuilder sb = new StringBuilder("Entries in " + category + ":\n");
                    for (Entry entry : filtered) {
                        sb.append(entry.getName()).append(" - $")
                                .append(entry.getAmount()).append(" (")
                                .append(entry.getDate()).append(")\n");
                    }
                    JOptionPane.showMessageDialog(this, sb.toString());
                }
            }
        });
    }

    private void refreshEntries() {
        model.setRowCount(0);
        ArrayList<Entry> entries = user.getEntries();

        if (entries == null) {
            statusLabel.setText("File Error - Does Not Exist");
            return;
        }

        if (entries.isEmpty()) {
            statusLabel.setText("No recent entries");
            return;
        }

        for (Entry entry : entries) {
            model.addRow(new Object[]{
                    entry.getName(),
                    String.format("%.2f", entry.getAmount()),
                    entry.getDate(),
                    entry.getCategory()
            });
        }
        statusLabel.setText("");
    }
}

