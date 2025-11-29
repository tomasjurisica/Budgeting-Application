package view;

import interface_adapter.household_dashboard.HouseholdDashboardState;
import interface_adapter.household_dashboard.HouseholdDashboardViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.select_user.SelectUserController;
import entity.User;

import javax.swing.*;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

/**
 * The View for the logged-in household dashboard.
 */
public class HouseholdDashboardView extends JPanel implements PropertyChangeListener {

    private final String viewName = "logged in";
    private final HouseholdDashboardViewModel householdDashboardViewModel;
    private LogoutController logoutController;
    private SelectUserController SelectUserController;

    private final JLabel householdID;
    private final JPanel roommatesPanel;

    public HouseholdDashboardView(HouseholdDashboardViewModel householdDashboardViewModel) {
        this.householdDashboardViewModel = householdDashboardViewModel;
        this.householdDashboardViewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout(10, 10));

        // --- Top panel ---
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel usernameInfo = new JLabel("Currently logged in: ");
        householdID = new JLabel();
        topPanel.add(usernameInfo);
        topPanel.add(householdID);
        add(topPanel, BorderLayout.NORTH);

        // --- Center panel for roommates ---
        JPanel centerWrapper = new JPanel();
        centerWrapper.setLayout(new BoxLayout(centerWrapper, BoxLayout.Y_AXIS));

        JLabel roommatesLabel = new JLabel("Roommates");
        roommatesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerWrapper.add(roommatesLabel);
        centerWrapper.add(Box.createVerticalStrut(10));

        // Add Roommate button
        JButton addRoommateButton = new JButton("Add Roommate");
        addRoommateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addRoommateButton.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(
                this,
                "Enter roommate name:",
                "Add Roommate",
                JOptionPane.PLAIN_MESSAGE
            );
            if (name != null && !name.trim().isEmpty()) {
                householdDashboardViewModel.addUser(name.trim());
            }
        });
        centerWrapper.add(addRoommateButton);
        centerWrapper.add(Box.createVerticalStrut(10));

        // Roommates buttons panel
        roommatesPanel = new JPanel();
        roommatesPanel.setLayout(new BoxLayout(roommatesPanel, BoxLayout.Y_AXIS));
        roommatesPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerWrapper.add(roommatesPanel);
        centerWrapper.add(Box.createVerticalGlue());

        add(centerWrapper, BorderLayout.CENTER);

        // --- Logout button at bottom ---
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton logOut = new JButton("Log Out");
        buttonsPanel.add(logOut);
        logOut.addActionListener(e -> {
            if (logoutController != null) logoutController.execute();
        });
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName())) {
            HouseholdDashboardState state = (HouseholdDashboardState) evt.getNewValue();

            // Check for error and show popup
            String errorMessage = state.getAddUserError();
            if (errorMessage != null && !errorMessage.isEmpty()) {
                JOptionPane.showMessageDialog(
                    this,
                    errorMessage,
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
                // Clear the error after showing it
                state.setAddUserError(null);
            }

            // Set household ID, with null check
            if (state.getHousehold() != null) {
                householdID.setText(state.getHousehold().getHouseholdID());
            } else {
                householdID.setText("");
            }

            // Clear old buttons
            roommatesPanel.removeAll();
            List<User> housemates = state.getUsers();

            if (housemates != null && !housemates.isEmpty()) {
                for (User roommate : housemates) {
                    JButton button = new JButton(roommate.getName());
                    button.setAlignmentX(Component.CENTER_ALIGNMENT);

                    // Wire button click to SelectUserController
                    button.addActionListener(e -> {
                        if (SelectUserController != null) {
                            SelectUserController.execute(roommate.getName());
                        }
                    });

                    roommatesPanel.add(button);
                    roommatesPanel.add(Box.createVerticalStrut(5));
                }
            } else {
                JLabel noRoommatesLabel = new JLabel("No roommates found");
                noRoommatesLabel.setFont(new Font("Arial", Font.ITALIC, 12));
                noRoommatesLabel.setForeground(Color.GRAY);
                noRoommatesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                roommatesPanel.add(noRoommatesLabel);
            }

            roommatesPanel.revalidate();
            roommatesPanel.repaint();
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setLogoutController(LogoutController logoutController) {
        this.logoutController = logoutController;
    }

    public void setSelectUserController(SelectUserController controller) {
        this.SelectUserController = controller;
    }
}
