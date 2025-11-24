package view;

import interface_adapter.household_dashboard.HouseholdDashboardState;
import interface_adapter.household_dashboard.HouseholdDashboardViewModel;
import interface_adapter.logout.LogoutController;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The View for when the user is logged into the program.
 */
public class HouseholdDashboardView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "logged in";
    private final HouseholdDashboardViewModel householdDashboardViewModel;
    private LogoutController logoutController;

    private final JLabel username;

    private final JButton logOut;

    public HouseholdDashboardView(HouseholdDashboardViewModel householdDashboardViewModel) {
        this.householdDashboardViewModel = householdDashboardViewModel;
        this.householdDashboardViewModel.addPropertyChangeListener(this);

        final JLabel usernameInfo = new JLabel("Currently logged in: ");
        username = new JLabel();

        final JPanel buttons = new JPanel();
        logOut = new JButton("Log Out");
        buttons.add(logOut);
        logOut.addActionListener(e -> logoutController.execute());

        logOut.addActionListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(usernameInfo);
        this.add(username);

        this.add(buttons);
    }

    /**
     * React to a button click that results in evt.
     * @param evt the ActionEvent to react to
     */
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final HouseholdDashboardState state = (HouseholdDashboardState) evt.getNewValue();
            username.setText(state.getUsername());
        }

    }

    public String getViewName() {
        return viewName;
    }

    public void setLogoutController(LogoutController logoutController) {
        // Save the logout controller in the instance variable.
        this.logoutController = logoutController;
    }
}