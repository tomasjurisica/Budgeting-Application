package interface_adapter.signup;

import interface_adapter.ViewModel;

/**
 * The ViewModel for the Signup View.
 */
public class SignupViewModel extends ViewModel<SignupState> {

    public static final String HOUSEHOLDID_LABEL = "Household ID";
    public static final String PASSWORD_LABEL = "Household password";
    public static final String REPEAT_PASSWORD_LABEL = "Confirm password";
    public static final String SIGNUP_BUTTON_LABEL = "Sign up";
    public static final String TO_LOGIN_BUTTON_LABEL = "Go to Login";

    public SignupViewModel() {
        super("sign up");
        setState(new SignupState());
    }

}
