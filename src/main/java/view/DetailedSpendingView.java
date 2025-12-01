package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import interface_adapter.detailed_spending.DetailedSpendingController;
import interface_adapter.detailed_spending.DetailedSpendingState;
import interface_adapter.detailed_spending.DetailedSpendingViewModel;
// import interface_adapter.detailed_spending.PurchaseUIModel;

// import use_case.detailed_spending.DetailedSpendingOutputData;

public class DetailedSpendingView extends JFrame implements PropertyChangeListener {

    private static final int POPUP_HEIGHT = 300;
    private static final int POPUP_WIDTH = 300;
    private static final int TITLE_SIZE = 16;
    private static final int HEADER_HEIGHT = 25;
    private static final int SCROLLABLE_WIDTH = 280;
    private static final int SCROLLABLE_HEIGHT = 200;
    private static final int FONT_SIZE = 14;
    private static final int CELL_HEIGHT = 20;
    private static final String VIEW_NAME = "detailed spending";

    private DetailedSpendingController detailedSpendingController;
    private final DetailedSpendingViewModel viewModel;
    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private final JList<String> list = new JList<>(listModel);
    private final JLabel titleLabel = new JLabel("Category:");

    public DetailedSpendingView(DetailedSpendingViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);

        setTitle("Expenses Breakdown");
        setSize(POPUP_WIDTH, POPUP_HEIGHT);
        setLocationRelativeTo(null);

        final JPanel mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);

        initializeTitleLabel();
        final JPanel headerRow = createHeader();
        final JScrollPane scrollPane = createScrollableList();

        final JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(titleLabel);
        topPanel.add(headerRow);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
    }

    // Helpers
    private void initializeTitleLabel() {
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, TITLE_SIZE));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private JPanel createHeader() {
        final JLabel expenseNameLabel = new JLabel("Name", SwingConstants.CENTER);
        final JLabel expenseAmountLabel = new JLabel("Amount", SwingConstants.CENTER);
        final JLabel dateLabel = new JLabel("Date", SwingConstants.CENTER);

        final JPanel headerRow = new JPanel(new java.awt.GridLayout(1, 3));
        headerRow.add(expenseNameLabel);
        headerRow.add(expenseAmountLabel);
        headerRow.add(dateLabel);

        headerRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, HEADER_HEIGHT));
        return headerRow;
    }

    private JScrollPane createScrollableList() {
        list.setFont(new Font("Monospaced", Font.PLAIN, FONT_SIZE));
        list.setFixedCellHeight(CELL_HEIGHT);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setFixedCellWidth(-1);

        final JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(SCROLLABLE_WIDTH, SCROLLABLE_HEIGHT));
        scrollPane.setViewportView(list);
        return scrollPane;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final DetailedSpendingState state = viewModel.getState();

        if (state.isHasError()) {
            JOptionPane.showMessageDialog(
                    this,
                    state.getErrorMessage(),
                    "No Transactions",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }

        updateCategory(state.getCategoryName());
        updateList(state.getPurchases());
        bringToFrontIfHidden();
    }

    private void updateCategory(String categoryName) {
        titleLabel.setText("Category: " + categoryName);
    }

    private void updateList(List<DetailedSpendingState.PurchaseUIModel> purchases) {
        listModel.clear();  // reset UI list
        final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM-dd-yyyy");

        for (DetailedSpendingState.PurchaseUIModel p : purchases) {
            final String displayAmount = String.format("$%.2f", p.getAmount());
            final String line = String.format("%-15s %-10s %s",
                    p.getName(),
                    displayAmount,
                    fmt.format(p.getDate()));

            listModel.addElement(line);
        }
    }


    private void bringToFrontIfHidden() {
        if (!isVisible()) {
            setVisible(true);
        }
        toFront();
    }

    /**
     * Returns the unique view name.
     *
     * @return the view name "detailed spending"
     */
    public String getViewName() {
        return VIEW_NAME;
    }

    /**
     * Sets the controller responsible for handling detailed spending actions.
     * @param detailedSpendingController the controller assigned to this view
     */

    public void setDetailedSpendingController(DetailedSpendingController detailedSpendingController) {
        this.detailedSpendingController = detailedSpendingController;
    }
}
