package view;

import interface_adapter.detailed_spending.DetailedSpendingController;
import interface_adapter.detailed_spending.DetailedSpendingViewModel;
import interface_adapter.detailed_spending.DetailedSpendingState;
import use_case.detailed_spending.DetailedSpendingOutputData;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.format.DateTimeFormatter;
import java.util.List;
/**
 * The View for detailed spending
 */
public class DetailedSpendingView extends JFrame implements PropertyChangeListener {

    private final String viewName = "detailed spending";
    private final DetailedSpendingViewModel viewModel;
    private final DefaultListModel<String> listModel;
    private DetailedSpendingController detailedSpendingController;;
    private final JList<String> list;
    private final JLabel titleLabel;
    private final JLabel expenseAmountLabel;
    private final JLabel dateLabel;
    private final JLabel expenseNameLabel;

    public DetailedSpendingView(DetailedSpendingViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);

        setTitle("Expenses Breakdown");
        setSize(300, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        expenseAmountLabel = new JLabel("Amount:");
        dateLabel = new JLabel("Date:");
        titleLabel = new JLabel("Category: ");
        expenseNameLabel = new JLabel("Name:");
        expenseAmountLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        dateLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        expenseNameLabel.setFont(new Font("SansSerif", Font.BOLD, 12));

        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, titleLabel.getPreferredSize().height));

        expenseNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        expenseAmountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        dateLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel headerRow = new JPanel(new GridLayout(1, 3));
        headerRow.add(expenseNameLabel);
        headerRow.add(expenseAmountLabel);
        headerRow.add(dateLabel);

        listModel = new DefaultListModel<>();
        list = new JList<>(listModel);

        JScrollPane scrollPane = new JScrollPane(list);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(titleLabel);   // "Category: X"
        topPanel.add(headerRow);
        panel.add(scrollPane, BorderLayout.CENTER);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        setContentPane(panel);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        DetailedSpendingState state = viewModel.getState();

        if (state.isHasError()) {
            JOptionPane.showMessageDialog(
                    this,
                    state.getErrorMessage(),
                    "No Transactions",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        titleLabel.setText("Category: " + state.getCategoryName());

        listModel.clear();
        List<DetailedSpendingOutputData.Purchase> purchases = state.getPurchases();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM-dd-yyyy");

        list.setFont(new Font("Monospaced", Font.PLAIN, 14));
        for (DetailedSpendingOutputData.Purchase p : purchases) {
            String displayAmount = String.format("$%.2f", p.getAmount());
            String line = String.format("%-15s %-10s %s",
                    p.getPurchaseName(),
                    displayAmount,
                    fmt.format(p.getDate()));
            listModel.addElement(line);
        }

        if (!isVisible()) {
            setVisible(true);
        }
        toFront();
    }

    public String getViewName() {
        return viewName;
    }

    public void setDetailedSpendingController(DetailedSpendingController detailedSpendingController) {
        this.detailedSpendingController = detailedSpendingController;
    }
}



