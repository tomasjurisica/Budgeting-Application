package interface_adapter.login;

import interface_adapter.ViewModel;

/**
 * The View Model for the Login View.
 */
public class LoginViewModel extends ViewModel<LoginState> {
    public static final String HOUSEHOLDID_LABEL = "Household ID";
    public static final String PASSWORD_LABEL = "Household password";
    public static final String LOGIN_BUTTON_LABEL = "Login";
    public static final String TO_SIGNUP_BUTTON_LABEL = "Go to Sign up";

    public LoginViewModel() {
        super("log in");
        setState(new LoginState());
    }

}
