package interface_adapter.login;

import interface_adapter.ViewManagerModel;
import interface_adapter.household_dashboard.HouseholdDashboardState;
import interface_adapter.household_dashboard.HouseholdDashboardViewModel;
import interface_adapter.signup.SignupViewModel;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;

/**
 * The Presenter for the Login Use Case.
 */
public class LoginPresenter implements LoginOutputBoundary {

    private final LoginViewModel loginViewModel;
    private final HouseholdDashboardViewModel householdDashboardViewModel;
    private final ViewManagerModel viewManagerModel;
    private final SignupViewModel signupViewModel;

    public LoginPresenter(ViewManagerModel viewManagerModel,
                          HouseholdDashboardViewModel householdDashboardViewModel,
                          LoginViewModel loginViewModel, SignupViewModel signupViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.householdDashboardViewModel = householdDashboardViewModel;
        this.loginViewModel = loginViewModel;
        this.signupViewModel = signupViewModel;
    }

    @Override
    public void prepareSuccessView(LoginOutputData response) {
        HouseholdDashboardState state = householdDashboardViewModel.getState();
        state.setUsername(response.getUsername());
        state.setHousehold(response.getHousehold()); // set the real household

        // Inject interactor with real household
        use_case.household.AddUserInteractor interactor = new use_case.household.AddUserInteractor(response.getHousehold(), householdDashboardViewModel);
        householdDashboardViewModel.setAddUserInteractor(interactor);

        householdDashboardViewModel.firePropertyChange();

        // switch views
        loginViewModel.setState(new LoginState());
        viewManagerModel.setState(householdDashboardViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String error) {
        final LoginState loginState = loginViewModel.getState();
        loginState.setLoginError(error);
        loginViewModel.firePropertyChange();
    }

    @Override
    public void switchToSignUpView() {
        viewManagerModel.setState(signupViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
