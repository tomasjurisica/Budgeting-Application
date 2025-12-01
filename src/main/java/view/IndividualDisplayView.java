package view;

import data_access.FileUserDataAccessObject;
import entity.Entry;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class IndividualDisplayView extends JPanel {

    private final FileUserDataAccessObject userDao;
    private final String householdId; // e.g. "ale"
    private final String username;    // e.g. "john"

    private final DefaultTableModel model;

    public IndividualDisplayView(FileUserDataAccessObject userDao,
                                 String householdId,
                                 String username) {
        this.userDao = userDao;
        this.householdId = householdId;
        this.username = username;

        setLayout(new BorderLayout());

        // ----- Header -----
        JLabel titleLabel = new JLabel("My Finances", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));

        JLabel userLabel = new JLabel("User: " + username, SwingConstants.CENTER);
        userLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.add(titleLabel);
        headerPanel.add(userLabel);

        // ----- Table -----
        String[] columns = {"Name", "Category", "Amount", "Date"};
        model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        // ----- Status -----
        JLabel statusLabel = new JLabel("", SwingConstants.CENTER);
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

        // Layout
        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);
        add(buttonPanel, BorderLayout.PAGE_END);

        // Load existing entries from users.json
        refreshEntries(statusLabel);

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

            int option = JOptionPane.showConfirmDialog(
                    this, message,
                    "Add New Entry", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {
                try {
                    String name = nameField.getText().trim();
                    String category = categoryField.getText().trim();
                    float amount = Float.parseFloat(amountField.getText().trim());

                    // Build Entry entity
                    Entry entry = new Entry(name, category, amount, LocalDate.now());

                    // SAVE TO users.json
                    userDao.addIndividualEntry(householdId, username, entry);

                    // Reload from users.json
                    refreshEntries(statusLabel);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid amount format!");
                } catch (RuntimeException ex) {
                    JOptionPane.showMessageDialog(this,
                            "Could not save entry: " + ex.getMessage());
                }
            }
        });

        categoryButton.addActionListener(e -> {
            // Simple category display using current entries
            List<Entry> entries = userDao.getIndividualEntries(householdId, username);
            if (entries.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "No entries found for user: " + username);
            } else {
                StringBuilder sb = new StringBuilder("Entries for " + username + ":\n");
                for (Entry entry : entries) {
                    sb.append(entry.getName())
                            .append(" - $").append(entry.getAmount())
                            .append(" (").append(entry.getCategory())
                            .append(", ").append(entry.getDate()).append(")\n");
                }
                JOptionPane.showMessageDialog(this, sb.toString());
            }
        });
    }

    private void refreshEntries(JLabel statusLabel) {
        model.setRowCount(0);

        List<Entry> entries =
                userDao.getIndividualEntries(householdId, username);

        if (entries.isEmpty()) {
            statusLabel.setText("No recent entries");
            return;
        }

        for (Entry entry : entries) {
            model.addRow(new Object[]{
                    entry.getName(),
                    entry.getCategory(),
                    String.format("%.2f", entry.getAmount()),
                    entry.getDate()
            });
        }
        statusLabel.setText("");
    }

    public String getViewName() {
        return "individual display";
    }
}
