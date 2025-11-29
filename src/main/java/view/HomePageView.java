package view;

import data_access.ExchangeRateDataAccess;
import data_access.FileUserDataAccessObject;
import entity.Entry;
import entity.Household;
import entity.User;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;

import interface_adapter.home_page.HomePageViewModel;
import interface_adapter.ViewManagerModel;
import view.*;
import view.AddHouseholdEntryView;
import use_case.home_display.*;


public class HomePageView extends JPanel {
    private final String viewName = "home page";
    private Household household;
    private JPanel homeTopBar;
    private JPanel navTopBar;
    private JPanel currentTopBar;
    private final HomePageViewModel viewModel;
    private PieChartPanel piePanel;
    private JPanel categoryList;
    private JButton addButton;
    private JLabel categoryLabel;
    private JPanel pieWrapper;
    private JScrollPane scrollPane;
    private final FileUserDataAccessObject userDao;
    private ViewManagerModel viewManagerModel;
    private AddHouseholdEntryView addHouseholdEntryView;

    public HomePageView(HomePageViewModel viewModel, FileUserDataAccessObject userDao) {
        this.viewModel = viewModel;
        this.userDao = userDao;
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(350, 600));

        initializeUI();
    }

    public void setViewManagerModel(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
    }

    public void setAddHouseholdEntryView(AddHouseholdEntryView addHouseholdEntryView) {
        this.addHouseholdEntryView = addHouseholdEntryView;
    }

    private void initializeUI() {
        // Top bar
        this.homeTopBar = new JPanel(new BorderLayout());
        this.homeTopBar.setBackground(new Color(42, 42, 42));
        this.homeTopBar.setPreferredSize(new Dimension(350, 50));
        JButton homeButton = new JButton("⌂");
        homeButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        homeButton.setFocusPainted(false);
        homeButton.setBorderPainted(false);
        homeButton.setContentAreaFilled(false);
        homeButton.setForeground(Color.white);
        this.homeTopBar.add(homeButton, BorderLayout.WEST);
        JButton menuButton = new JButton("☰");
        menuButton.setFont(new Font("SansSerif", Font.BOLD, 24));
        menuButton.setFocusPainted(false);
        menuButton.setBorderPainted(false);
        menuButton.setContentAreaFilled(false);
        menuButton.setForeground(Color.white);
        this.homeTopBar.add(menuButton, BorderLayout.EAST);
        this.currentTopBar = this.homeTopBar;
        this.add(this.currentTopBar, BorderLayout.NORTH);

        // Navigation bar
        this.navTopBar = new JPanel(new BorderLayout());
        this.navTopBar.setBackground(new Color(42, 42, 42));
        this.navTopBar.setPreferredSize(new Dimension(350, 50));
        JButton closeNavButton = new JButton("X");
        closeNavButton.setFont(new Font("Arial", Font.BOLD, 20));
        closeNavButton.setFocusPainted(false);
        closeNavButton.setBorderPainted(false);
        closeNavButton.setContentAreaFilled(false);
        closeNavButton.setForeground(Color.white);
        this.navTopBar.add(closeNavButton, BorderLayout.WEST);
        JPanel navButtonsPanel = new JPanel();
        navButtonsPanel.setBackground(new Color(42, 42, 42));
        navButtonsPanel.setLayout(new GridLayout(1, 6, 5, 0));

        for(int i = 1; i <= 3; ++i) {

            JButton iconButton = new JButton("★");
            iconButton.setFont(new Font("SansSerif", Font.BOLD, 18));
            iconButton.setFocusPainted(false);
            iconButton.setBorderPainted(false);
            iconButton.setContentAreaFilled(false);
            iconButton.setForeground(Color.white);
            navButtonsPanel.add(iconButton);

            if (i == 1) { // For example, first star opens currency converter
                iconButton.addActionListener(e -> openCurrencyConverterPopup());
            }
        }

        JPanel rightNavWrapper = new JPanel(new BorderLayout());
        rightNavWrapper.setBackground(new Color(42, 42, 42));
        JPanel separatorPanel = new JPanel();
        separatorPanel.setPreferredSize(new Dimension(2, 40));
        separatorPanel.setBackground(Color.LIGHT_GRAY);
        rightNavWrapper.add(separatorPanel, BorderLayout.WEST);
        rightNavWrapper.add(navButtonsPanel, BorderLayout.CENTER);
        this.navTopBar.add(rightNavWrapper, BorderLayout.CENTER);
        homeButton.addActionListener((e) -> this.refreshHome());
        menuButton.addActionListener((e) -> this.switchTopBar(this.navTopBar));
        closeNavButton.addActionListener((e) -> this.switchTopBar(this.homeTopBar));

        // Initialize center content (will be populated when household is set)
        this.categoryLabel = new JLabel("Category Spending: ");
        this.categoryLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        this.categoryList = new JPanel();
        this.categoryList.setLayout(new BoxLayout(this.categoryList, BoxLayout.Y_AXIS));

        // Create empty pie chart initially
        Map<String, Float> emptyTotals = new LinkedHashMap<>();
        this.piePanel = new PieChartPanel(emptyTotals, 0.0F);
        this.piePanel.setPreferredSize(new Dimension(300, 300));

        this.pieWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, -40));
        this.pieWrapper.add(this.piePanel);

        JButton monthButton = new JButton("This Month ▼");
        monthButton.setFont(new Font("Arial", Font.BOLD, 16));
        monthButton.setFocusPainted(false);
        monthButton.setBorderPainted(false);
        monthButton.setContentAreaFilled(false);
        monthButton.setForeground(Color.black);
        JPopupMenu monthMenu = new JPopupMenu();
        String[] months = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        int currentMonth = LocalDate.now().getMonthValue();

        for(int i = currentMonth - 1; i >= Math.max(0, currentMonth - 4); --i) {
            JMenuItem item = new JMenuItem(months[i]);
            int monthIndex = i + 1;
            item.addActionListener((e) -> {
                monthButton.setText(item.getText() + " ▼");
                updatePieChartForMonth(monthIndex);
            });
            monthMenu.add(item);
        }

        monthMenu.add("Full Year");
        monthButton.addActionListener((e) -> monthMenu.show(monthButton, 0, monthButton.getHeight()));
        JPanel topCenterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topCenterPanel.add(monthButton);

        this.scrollPane = new JScrollPane(this.categoryList);
        JPanel center = new JPanel(new BorderLayout());
        center.add(topCenterPanel, BorderLayout.NORTH);
        center.add(this.pieWrapper, BorderLayout.CENTER);
        center.add(this.scrollPane, BorderLayout.SOUTH);
        this.add(center, BorderLayout.CENTER);

        // Bottom button
        this.addButton = new JButton("+");
        this.addButton.setFont(new Font("Arial", Font.BOLD, 30));
        this.addButton.setPreferredSize(new Dimension(70, 70));
        this.addButton.addActionListener(e -> navigateToAddEntry());
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(this.addButton);
        this.add(bottomPanel, BorderLayout.SOUTH);

        // Listen to viewModel changes
        viewModel.addListener((entries) -> {
            Map<String, Float> newTotals = this.computeCategoryTotals(entries);
            float newTotal = (Float)newTotals.values().stream().reduce(0.0F, Float::sum);
            this.pieWrapper.remove(this.piePanel);
            this.piePanel = new PieChartPanel(newTotals, newTotal);
            this.piePanel.setPreferredSize(new Dimension(300, 300));
            this.pieWrapper.removeAll();
            this.pieWrapper.add(this.piePanel);
            this.pieWrapper.revalidate();
            this.pieWrapper.repaint();
            this.categoryList.removeAll();
            this.populateCategoryList(this.categoryList, newTotals);
            this.categoryList.revalidate();
            this.categoryList.repaint();
        });
    }

    public void setHousehold() {
        String householdID = userDao.getCurrentUsername();
        if(householdID == null) {
            this.household = userDao.get(householdID);
            refreshHome();
        }
        else {
            System.out.println("No current user in DAO");
        }
    }

    private List<Entry> filterEntriesByMonth(List<Entry> allEntries, int month) {
        List<Entry> filtered = new ArrayList<>();
        for (Entry e : allEntries) {
            if (e.getDate().getMonthValue() == month) {
                filtered.add(e);
            }
        }
        return filtered;
    }

    private void openCurrencyConverterPopup() {
        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(HomePageView.this), "Currency Converter", true);
        dialog.setSize(400,250);
        dialog.setLayout(new GridLayout(5, 2, 10, 10));

        JComboBox<String> fromCurrency = new JComboBox<>(getCurrencyCodes());
        JTextField amountField = new JTextField();
        JComboBox<String> toCurrency = new JComboBox<>(getCurrencyCodes());

        JLabel resultLabel = new JLabel("Converted amount: ");

        JButton convertButton = new JButton("Convert");

        ExchangeRateDataAccess exchangeRateDao = new ExchangeRateDataAccess();

        convertButton.addActionListener((e) -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                String from = (String) fromCurrency.getSelectedItem();
                String to = (String) toCurrency.getSelectedItem();
                double converted = exchangeRateDao.convertCurrency(from, to, amount);
                resultLabel.setText(String.format("Converted amount: %.2f", converted));

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Enter a valid number");
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(dialog, ex.getMessage());
            }
        });

        dialog.add(new JLabel("From:"));
        dialog.add(fromCurrency);
        dialog.add(new JLabel("Amount:"));
        dialog.add(amountField);
        dialog.add(new JLabel("To:"));
        dialog.add(toCurrency);
        dialog.add(new JLabel());
        dialog.add(convertButton);
        dialog.add(new JLabel());
        dialog.add(resultLabel);

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void updatePieChartForMonth(int month) {
        List<Entry> allEntries = viewModel.getEntries();
        List<Entry> monthEntries = filterEntriesByMonth(allEntries, month);

        Map<String, Float> newTotals = computeCategoryTotals(monthEntries);
        float newTotal = (Float)newTotals.values().stream().reduce(0.0F, Float::sum);

        this.pieWrapper.removeAll();
        this.piePanel = new PieChartPanel(newTotals, newTotal);
        this.piePanel.setPreferredSize(new Dimension(300, 300));
        this.pieWrapper.add(this.piePanel);
        this.pieWrapper.revalidate();
        this.pieWrapper.repaint();

        this.categoryList.removeAll();
        populateCategoryList(this.categoryList, newTotals);
        this.categoryList.revalidate();
        this.categoryList.repaint();
    }

    private void refreshData() {
        if (household == null) {
            return;
        }

        HomeDisplayInteractor interactor = new HomeDisplayInteractor(viewModel);
        List<Entry> allEntries = new ArrayList<>();
        for (User user : household.getUsers()) {
            allEntries.addAll(user.getEntries());
        }
        HomeDisplayRequestModel request = new HomeDisplayRequestModel(allEntries);
        interactor.execute(request);
    }

    private String[] getCurrencyCodes() {
        return new String[]{"USD", "EUR", "GBP", "CAD", "JPY", "AUD"};
    }

    private Map<String, Float> computeCategoryTotals(List<Entry> entries) {
        Map<String, Float> categoryTotals = new LinkedHashMap<>();
        float totalSpent = 0.0F;

        for(Entry e : entries) {
            float amt = Math.abs(e.getAmount());
            totalSpent += amt;
            categoryTotals.merge(e.getCategory(), amt, Float::sum);

            }


        return categoryTotals;
    }

    private void populateCategoryList(JPanel categoryListPanel, Map<String, Float> categoryTotals) {
        categoryListPanel.add(this.categoryLabel);

        for(String category : categoryTotals.keySet()) {
            float amt = (Float)categoryTotals.get(category);
            JLabel label = new JLabel(category + ": $" + amt);
            label.setFont(new Font("Arial", Font.PLAIN, 18));
            categoryListPanel.add(label);
        }
    }

    private void switchTopBar(JPanel newTopBar) {
        this.remove(this.currentTopBar);
        this.currentTopBar = newTopBar;
        this.add(this.currentTopBar, BorderLayout.NORTH);
        this.revalidate();
        this.repaint();
    }

    private void refreshHome() {
        this.revalidate();
        this.repaint();
        System.out.println("Home Refreshed");
    }

    private void navigateToAddEntry() {
        if (viewManagerModel != null && addHouseholdEntryView != null && household != null) {
            // Load users from household into AddHouseholdEntryView
            addHouseholdEntryView.loadUsersFromHousehold(household);

            // Navigate to Add Household Entry view
            viewManagerModel.setState("add entry");
            viewManagerModel.firePropertyChange();
        }
    }

    public String getViewName() {
        return viewName;
    }
}
