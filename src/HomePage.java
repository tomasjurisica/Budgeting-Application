import javax.swing.*;
import java.awt.*;
import java.util.*;
import BudgetingObjects.*;

public class HomePage extends JFrame {
    private Household household;
    private JPanel homeTopBar;
    private JPanel navTopBar;
    private JPanel currentTopBar;

    public HomePage(Household household) {
        this.household = household;

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
        menuButton.setFont(new Font("SansSerif", Font.BOLD, 20));
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
        closeNavButton.setFont(new Font("SansSerif", Font.BOLD, 20));
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

        ArrayList<Entry> entries = new ArrayList<>();
        for(User user : household.getUsers()) {
            entries.addAll(user.getEntries());
        }

        float totalSpent = 0f;
        Map<String, Float> categoryTotals = new HashMap<>();

        for(Entry e: entries) {
            if (e.getAmount() < 0){
                float amt = -e.getAmount();
                totalSpent += amt;
                categoryTotals.merge(e.getCategory(), amt, Float::sum);
            }
        }

        PieChartPanel piePanel = new PieChartPanel(categoryTotals, totalSpent);
        piePanel.setPreferredSize(new Dimension(450, 450));

        JPanel categoryList = new JPanel();
        categoryList.setLayout(new BoxLayout(categoryList, BoxLayout.Y_AXIS));

        for (String category : categoryTotals.keySet()) {
            float amt = categoryTotals.get(category);
            JLabel label = new JLabel(category + ": $" + amt);
            label.setFont(new Font("Arial", Font.PLAIN, 18));
            categoryList.add(label);
        }

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

        monthButton.addActionListener(e -> monthMenu.show(monthButton, 0, monthButton.getHeight()));

        JPanel topCenterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topCenterPanel.setBackground(Color.WHITE);
        topCenterPanel.add(monthButton);

        JPanel pieWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        pieWrapper.add(piePanel);

        JPanel center = new JPanel(new BorderLayout());
        center.add(topCenterPanel, BorderLayout.NORTH);
        center.add(pieWrapper, BorderLayout.CENTER);
        center.add(scrollPane, BorderLayout.SOUTH);

        add(center, BorderLayout.CENTER);

        JButton addButton = new JButton("+");
        addButton.setFont(new Font("Arial", Font.BOLD, 30));
        addButton.setPreferredSize(new Dimension(70, 70));

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(addButton);

        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
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
    }
}
