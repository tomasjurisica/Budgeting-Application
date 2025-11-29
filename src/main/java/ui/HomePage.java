//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ui;

import entity.Entry;
import entity.Household;
import entity.User;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

import viewmodels.HomePageViewModel;
import use_case.homedisplay.*;


public class HomePage extends JFrame {
    private Household household;
    private JPanel homeTopBar;
    private JPanel navTopBar;
    private JPanel currentTopBar;
    private final HomePageViewModel viewModel;
    private PieChartPanel piePanel;
    private JPanel categoryList;
    private JButton addButton;
    private JLabel categoryLabel;

    public HomePage(Household household, HomePageViewModel viewModel) {
        this.household = household;
     //   this.addEntryController = addEntryController;
        this.viewModel = viewModel;
        this.setTitle("Budgeting App");
        this.setSize(500, 600);
        this.setDefaultCloseOperation(3);
        this.setLayout(new BorderLayout());
        this.homeTopBar = new JPanel(new BorderLayout());
        this.homeTopBar.setBackground(new Color(42, 42, 42));
        this.homeTopBar.setPreferredSize(new Dimension(this.getWidth(), 50));
        JButton homeButton = new JButton("⌂");
        homeButton.setFont(new Font("SansSerif", 1, 20));
        homeButton.setFocusPainted(false);
        homeButton.setBorderPainted(false);
        homeButton.setContentAreaFilled(false);
        homeButton.setForeground(Color.white);
        this.homeTopBar.add(homeButton, "West");
        JButton menuButton = new JButton("☰");
        menuButton.setFont(new Font("SansSerif", 1, 24));
        menuButton.setFocusPainted(false);
        menuButton.setBorderPainted(false);
        menuButton.setContentAreaFilled(false);
        menuButton.setForeground(Color.white);
        this.homeTopBar.add(menuButton, "East");
        this.currentTopBar = this.homeTopBar;
        this.add(this.currentTopBar, "North");
        this.navTopBar = new JPanel(new BorderLayout());
        this.navTopBar.setBackground(new Color(42, 42, 42));
        this.navTopBar.setPreferredSize(new Dimension(this.getWidth(), 50));
        JButton closeNavButton = new JButton("X");
        closeNavButton.setFont(new Font("Arial", 1, 20));
        closeNavButton.setFocusPainted(false);
        closeNavButton.setBorderPainted(false);
        closeNavButton.setContentAreaFilled(false);
        closeNavButton.setForeground(Color.white);
        this.navTopBar.add(closeNavButton, "West");
        JPanel navButtonsPanel = new JPanel();
        navButtonsPanel.setBackground(new Color(42, 42, 42));
        navButtonsPanel.setLayout(new GridLayout(1, 6, 5, 0));

        for(int i = 1; i <= 6; ++i) {
            JButton iconButton = new JButton("★");
            iconButton.setFont(new Font("SansSerif", 1, 18));
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
        rightNavWrapper.add(separatorPanel, "West");
        rightNavWrapper.add(navButtonsPanel, "Center");
        this.navTopBar.add(rightNavWrapper, "Center");
        homeButton.addActionListener((e) -> this.refreshHome());
        menuButton.addActionListener((e) -> this.switchTopBar(this.navTopBar));
        closeNavButton.addActionListener((e) -> this.switchTopBar(this.homeTopBar));
        HomeDisplayInteractor interactor = new HomeDisplayInteractor(viewModel);
        List<Entry> allEntries = new ArrayList<>();
        for (User user : household.getUsers()) {
            allEntries.addAll(user.getEntries());
        }
        HomeDisplayRequestModel request = new HomeDisplayRequestModel(allEntries);

        HomeDisplayInteractor interactors = new HomeDisplayInteractor(viewModel);
        interactors.execute(request);

        Map<String, Float> categoryTotals = this.computeCategoryTotals(viewModel.getEntries());
        float totalSpent = (Float)categoryTotals.values().stream().reduce(0.0F, Float::sum);
        this.piePanel = new PieChartPanel(categoryTotals, totalSpent);
        this.piePanel.setPreferredSize(new Dimension(450, 450));
        this.categoryLabel = new JLabel("Category Spending: ");
        this.categoryLabel.setFont(new Font("SansSerif", 1, 16));
        this.categoryList = new JPanel();
        this.categoryList.setLayout(new BoxLayout(this.categoryList, 1));
        this.populateCategoryList(this.categoryList, categoryTotals);
        JScrollPane scrollPane = new JScrollPane(this.categoryList);
        JButton monthButton = new JButton("This Month ▼");
        monthButton.setFont(new Font("Arial", 1, 16));
        monthButton.setFocusPainted(false);
        monthButton.setBorderPainted(false);
        monthButton.setContentAreaFilled(false);
        monthButton.setForeground(Color.black);
        JPopupMenu monthMenu = new JPopupMenu();
        String[] months = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        int currentMonth = LocalDate.now().getMonthValue();

        for(int i = currentMonth - 1; i >= Math.max(0, currentMonth - 4); --i) {
            JMenuItem item = new JMenuItem(months[i]);
            item.addActionListener((e) -> monthButton.setText(item.getText() + "▼"));
            monthMenu.add(item);
        }

        monthMenu.add("Full Year");
        monthButton.addActionListener((e) -> monthMenu.show(monthButton, 0, monthButton.getHeight()));
        JPanel topCenterPanel = new JPanel(new FlowLayout(0));
        topCenterPanel.add(monthButton);
        JPanel pieWrapper = new JPanel(new FlowLayout(1, 0, -40));
        pieWrapper.add(this.piePanel);
        JPanel center = new JPanel(new BorderLayout());
        center.add(topCenterPanel, "North");
        center.add(pieWrapper, "Center");
        center.add(scrollPane, "South");
        this.add(center, "Center");
        this.addButton = new JButton("+");
        this.addButton.setFont(new Font("Arial", 1, 30));
        this.addButton.setPreferredSize(new Dimension(70, 70));
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(this.addButton);
        this.add(bottomPanel, "South");
//        this.addButton.addActionListener((ev) -> this.openAddEntryDialog());
        viewModel.addListener((entries) -> {
            Map<String, Float> newTotals = this.computeCategoryTotals(entries);
            float newTotal = (Float)newTotals.values().stream().reduce(0.0F, Float::sum);
            this.remove(this.piePanel);
            this.piePanel = new PieChartPanel(newTotals, newTotal);
            this.piePanel.setPreferredSize(new Dimension(450, 450));
            pieWrapper.removeAll();
            pieWrapper.add(this.piePanel);
            pieWrapper.revalidate();
            pieWrapper.repaint();
            this.categoryList.removeAll();
            this.populateCategoryList(this.categoryList, newTotals);
            this.categoryList.revalidate();
            this.categoryList.repaint();
        });
        this.setVisible(true);
    }

    private Map<String, Float> computeCategoryTotals(List<Entry> entries) {
        Map<String, Float> categoryTotals = new LinkedHashMap();
        float totalSpent = 0.0F;

        for(Entry e : entries) {
            if (e.getAmount() < 0.0F) {
                float amt = -e.getAmount();
                totalSpent += amt;
                categoryTotals.merge(e.getCategory(), amt, Float::sum);
            }
        }

        return categoryTotals;
    }

    private void populateCategoryList(JPanel categoryListPanel, Map<String, Float> categoryTotals) {
        categoryListPanel.add(this.categoryLabel);

        for(String category : categoryTotals.keySet()) {
            float amt = (Float)categoryTotals.get(category);
            JLabel label = new JLabel(category + ": $" + amt);
            label.setFont(new Font("Arial", 0, 18));
            categoryListPanel.add(label);
        }

    }

    private void switchTopBar(JPanel newTopBar) {
        this.remove(this.currentTopBar);
        this.currentTopBar = newTopBar;
        this.add(this.currentTopBar, "North");
        this.revalidate();
        this.repaint();
    }

    private void refreshHome() {
        this.revalidate();
        this.repaint();
        System.out.println("Home Refreshed");
    }
}
