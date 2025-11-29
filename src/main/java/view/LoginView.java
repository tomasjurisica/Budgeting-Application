package view;

import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import interface_adapter.signup.SignupViewModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The View for when the user is logging into the program.
 */
public class LoginView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "log in";
    private final LoginViewModel loginViewModel;

    private final JTextField householdIDInputField = new JTextField(15);
    private final JLabel householdIDErrorField = new JLabel();

    private final JPasswordField passwordInputField = new JPasswordField(15);
    private final JLabel passwordErrorField = new JLabel();

    private final JButton logIn;
    private final JButton toSignUp;
    private LoginController loginController = null;

    public LoginView(LoginViewModel loginViewModel) {

        this.loginViewModel = loginViewModel;
        this.loginViewModel.addPropertyChangeListener(this);

        // Add logo
        java.net.URL logoURL = getClass().getResource("/logo.png");
        ImageIcon originalIcon = new ImageIcon(logoURL);
        Image scaledImage = originalIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        ImageIcon logoIcon = new ImageIcon(scaledImage);
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        final LabelTextPanel usernameInfo = new LabelTextPanel(
            new JLabel(LoginViewModel.HOUSEHOLDID_LABEL), householdIDInputField);
        final LabelTextPanel passwordInfo = new LabelTextPanel(
            new JLabel(LoginViewModel.PASSWORD_LABEL), passwordInputField);

        final JPanel buttons = new JPanel();
        toSignUp = new JButton(LoginViewModel.TO_SIGNUP_BUTTON_LABEL);
        buttons.add(toSignUp);
        logIn = new JButton(LoginViewModel.LOGIN_BUTTON_LABEL);
        buttons.add(logIn);

        Dimension fieldSize = new Dimension(300, 40);
        usernameInfo.setMaximumSize(fieldSize);
        passwordInfo.setMaximumSize(fieldSize);

        logIn.addActionListener(
            evt -> {
                if (evt.getSource().equals(logIn)) {
                    final LoginState currentState = loginViewModel.getState();

                    loginController.execute(
                        currentState.getUsername(),
                        currentState.getPassword()
                    );
                }
            }
        );

        toSignUp.addActionListener(
            evt -> loginController.switchToSignUpView()
        );

        householdIDInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final LoginState currentState = loginViewModel.getState();
                currentState.setUsername(householdIDInputField.getText());
                loginViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setPreferredSize(new Dimension(350, 600));

        passwordInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final LoginState currentState = loginViewModel.getState();
                currentState.setPassword(new String(passwordInputField.getPassword()));
                loginViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });

        this.add(logoLabel);
        this.add(Box.createVerticalStrut(15));
        this.add(usernameInfo);
        this.add(Box.createVerticalStrut(15));
        this.add(householdIDErrorField);
        this.add(passwordInfo);
        this.add(Box.createVerticalStrut(15));
        this.add(buttons);
    }

    /**
     * React to a button click that results in evt.
     *
     * @param evt the ActionEvent to react to
     */
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final LoginState state = (LoginState) evt.getNewValue();
        setFields(state);
        householdIDErrorField.setText(state.getLoginError());
    }

    private void setFields(LoginState state) {
        householdIDInputField.setText(state.getUsername());
        passwordInputField.setText(state.getPassword());
    }

    public String getViewName() {
        return viewName;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }
}
