import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import BudgetingObjects.*;
import controllers.AddEntryController;
import viewmodels.HomePageViewModel;

public class HomePage extends JFrame {
    private Household household;
    private JPanel homeTopBar;
    private JPanel navTopBar;
    private JPanel currentTopBar;

    private final AddEntryController addEntryController;
    private final HomePageViewModel viewModel;

    private PieChartPanel piePanel;
    private JPanel categoryList;
    private JButton addButton;
    private JLabel categoryLabel;

    public HomePage(Household household, AddEntryController addEntryController, HomePageViewModel viewModel) {
        this.household = household;
        this.addEntryController = addEntryController;
        this.viewModel = viewModel;

        setTitle("Budgeting App");
        setSize(350, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        homeTopBar = new JPanel(new BorderLayout());
        homeTopBar.setBackground(new Color(42, 42, 42));
        homeTopBar.setPreferredSize(new Dimension(getWidth(), 50));

        JButton homeButton = new JButton("\u2302");
        homeButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        homeButton.setFocusPainted(false);
        homeButton.setBorderPainted(false);
        homeButton.setContentAreaFilled(false);
        homeButton.setForeground(Color.white);
        homeTopBar.add(homeButton, BorderLayout.WEST);

        JButton menuButton = new JButton("\u2630");
        menuButton.setFont(new Font("SansSerif", Font.BOLD, 24));
        menuButton.setFocusPainted(false);
        menuButton.setBorderPainted(false);
        menuButton.setContentAreaFilled(false);
        menuButton.setForeground(Color.white);
        homeTopBar.add(menuButton, BorderLayout.EAST);

        currentTopBar = homeTopBar;
        add(currentTopBar, BorderLayout.NORTH);

        navTopBar = new JPanel(new BorderLayout());
        navTopBar.setBackground(new Color(42, 42, 42));
        navTopBar.setPreferredSize(new Dimension(getWidth(), 50));

        JButton closeNavButton = new JButton("X");
        closeNavButton.setFont(new Font("Arial", Font.BOLD, 20));
        closeNavButton.setFocusPainted(false);
        closeNavButton.setBorderPainted(false);
        closeNavButton.setContentAreaFilled(false);
        closeNavButton.setForeground(Color.white);
        navTopBar.add(closeNavButton, BorderLayout.WEST);

        JPanel navButtonsPanel = new JPanel();
        navButtonsPanel.setBackground(new Color(42, 42, 42));
        navButtonsPanel.setLayout(new GridLayout(1, 6, 5, 0));

        for (int i = 1; i <= 6; i++) {
            JButton iconButton = new JButton("★");
            iconButton.setFont(new Font("SansSerif", Font.BOLD, 18));
            iconButton.setFocusPainted(false);
            iconButton.setBorderPainted(false);
            iconButton.setContentAreaFilled(false);
            iconButton.setForeground(Color.white);
            navButtonsPanel.add(iconButton);
        }

        JPanel rightNavWrapper = new JPanel(new BorderLayout());
        rightNavWrapper.setBackground(new Color(42, 42, 42));

        JPanel separatorPanel = new JPanel();
        separatorPanel.setPreferredSize(new Dimension(2, 40));
        separatorPanel.setBackground(Color.LIGHT_GRAY);

        rightNavWrapper.add(separatorPanel, BorderLayout.WEST);
        rightNavWrapper.add(navButtonsPanel, BorderLayout.CENTER);

        navTopBar.add(rightNavWrapper, BorderLayout.CENTER);

        homeButton.addActionListener(e -> refreshHome());
        menuButton.addActionListener(e -> switchTopBar(navTopBar));
        closeNavButton.addActionListener(e -> switchTopBar(homeTopBar));

        List<Entry> initialEntries = new ArrayList<>();
        for (User user : household.getUsers()) {
            initialEntries.addAll(user.getEntries());
        }
        viewModel.setEntries(initialEntries);

        Map<String, Float> categoryTotals = computeCategoryTotals(viewModel.getEntries());
        float totalSpent = categoryTotals.values().stream().reduce(0f, Float::sum);

        piePanel = new PieChartPanel(categoryTotals, totalSpent);
        piePanel.setPreferredSize(new Dimension(450, 450));

        categoryLabel = new JLabel("Category Spending: ");
        categoryLabel.setFont(new Font("SansSerif", Font.BOLD, 16));

        categoryList = new JPanel();
        categoryList.setLayout(new BoxLayout(categoryList, BoxLayout.Y_AXIS));
        populateCategoryList(categoryList, categoryTotals);

        JScrollPane scrollPane = new JScrollPane(categoryList);

        JButton monthButton = new JButton("This Month \u25BC");
        monthButton.setFont(new Font("Arial", Font.BOLD, 16));
        monthButton.setFocusPainted(false);
        monthButton.setBorderPainted(false);
        monthButton.setContentAreaFilled(false);
        monthButton.setForeground(Color.black);

        JPopupMenu monthMenu = new JPopupMenu();
        String[] months = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
        int currentMonth = java.time.LocalDate.now().getMonthValue();

        for (int i = currentMonth - 1; i >= Math.max(0, currentMonth - 4); i--) {
            JMenuItem item = new JMenuItem(months[i]);
            item.addActionListener(e -> monthButton.setText(item.getText() + "\u25BC"));
            monthMenu.add(item);
        }
        monthMenu.add("Full Year");

        monthButton.addActionListener(e -> monthMenu.show(monthButton, 0, monthButton.getHeight()));

        JPanel topCenterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topCenterPanel.add(monthButton);

        JPanel pieWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, -40));
        pieWrapper.add(piePanel);

        JPanel center = new JPanel(new BorderLayout());
        center.add(topCenterPanel, BorderLayout.NORTH);
        center.add(pieWrapper, BorderLayout.CENTER);
        center.add(scrollPane, BorderLayout.SOUTH);

        add(center, BorderLayout.CENTER);

        addButton = new JButton("+");
        addButton.setFont(new Font("Arial", Font.BOLD, 30));
        addButton.setPreferredSize(new Dimension(70, 70));

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(addButton);

        add(bottomPanel, BorderLayout.SOUTH);

        addButton.addActionListener(ev -> openAddEntryDialog());

        viewModel.addListener(entries -> {
            Map<String, Float> newTotals = computeCategoryTotals(entries);
            float newTotal = newTotals.values().stream().reduce(0f, Float::sum);

            remove(piePanel);
            piePanel = new PieChartPanel(newTotals, newTotal);
            piePanel.setPreferredSize(new Dimension(450, 450));
            pieWrapper.removeAll();
            pieWrapper.add(piePanel);
            pieWrapper.revalidate();
            pieWrapper.repaint();

            categoryList.removeAll();
            populateCategoryList(categoryList, newTotals);
            categoryList.revalidate();
            categoryList.repaint();
        });

        setVisible(true);
    }

    private Map<String, Float> computeCategoryTotals(List<Entry> entries) {
        Map<String, Float> categoryTotals = new LinkedHashMap<>();
        float totalSpent = 0f;
        for (Entry e : entries) {
            if (e.getAmount() < 0) {
                float amt = -e.getAmount();
                totalSpent += amt;
                categoryTotals.merge(e.getCategory(), amt, Float::sum);
            }
        }
        return categoryTotals;
    }

    private void populateCategoryList(JPanel categoryListPanel, Map<String, Float> categoryTotals) {
        categoryListPanel.add(categoryLabel);
        for (String category : categoryTotals.keySet()) {
            float amt = categoryTotals.get(category);
            JLabel label = new JLabel(category + ": $" + amt);
            label.setFont(new Font("Arial", Font.PLAIN, 18));
            categoryListPanel.add(label);
        }
    }

    private void openAddEntryDialog() {
        JTextField nameField = new JTextField();
        JTextField categoryField = new JTextField();
        JTextField amountField = new JTextField();
        JTextField dateField = new JTextField(java.time.LocalDate.now().toString());

        final JComponent[] inputs = new JComponent[] {
                new JLabel("Name:"), nameField,
                new JLabel("Category:"), categoryField,
                new JLabel("Amount (use negative for expenses):"), amountField,
                new JLabel("Date (YYYY-MM-DD):"), dateField
        };
        int result = JOptionPane.showConfirmDialog(this, inputs, "Add Entry", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText();
                String category = categoryField.getText();
                float amount = Float.parseFloat(amountField.getText());
                LocalDate date = LocalDate.parse(dateField.getText());
                addEntryController.addEntry(name, category, amount, date);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage());
            }
        }
    }

    private void switchTopBar(JPanel newTopBar) {
        remove(currentTopBar);
        currentTopBar = newTopBar;
        add(currentTopBar, BorderLayout.NORTH);
        revalidate();
        repaint();
    }

    private void refreshHome() {
        revalidate();
        repaint();
        System.out.println("Home Refreshed");
    }
}
