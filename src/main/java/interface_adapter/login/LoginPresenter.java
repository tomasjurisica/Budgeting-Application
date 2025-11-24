package interface_adapter.login;

import data_access.FileUserDataAccessObject;
import interface_adapter.ViewManagerModel;
import interface_adapter.household_dashboard.HouseholdDashboardViewModel;
import interface_adapter.signup.SignupViewModel;
import use_case.add_user.AddUserInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;

/**
 * The Presenter for the Login Use Case.
 */
public class LoginPresenter implements LoginOutputBoundary {
    private final FileUserDataAccessObject userDAO;
    private final LoginViewModel loginViewModel;
    private final HouseholdDashboardViewModel householdDashboardViewModel;
    private final ViewManagerModel viewManagerModel;
    private final SignupViewModel signupViewModel;

    public LoginPresenter(ViewManagerModel viewManagerModel,
                          HouseholdDashboardViewModel householdDashboardViewModel,
                          LoginViewModel loginViewModel,
                          SignupViewModel signupViewModel,
                          FileUserDataAccessObject userDAO) {
        this.viewManagerModel = viewManagerModel;
        this.householdDashboardViewModel = householdDashboardViewModel;
        this.loginViewModel = loginViewModel;
        this.signupViewModel = signupViewModel;
        this.userDAO = userDAO;
    }


    @Override
    public void prepareSuccessView(LoginOutputData response) {
        // Set household in the dashboard state
        householdDashboardViewModel.getState().setHousehold(response.getHousehold());

        // Create AddUserInteractor with DAO
        AddUserInteractor interactor = new AddUserInteractor(
                response.getHousehold(),
                householdDashboardViewModel,
                userDAO // now this works
        );
        householdDashboardViewModel.setAddUserInteractor(interactor);

        householdDashboardViewModel.setAddUserInteractor(interactor);

        // Refresh dashboard view
        householdDashboardViewModel.firePropertyChange();

        // Clear login state
        loginViewModel.setState(new LoginState());

        // Switch to the dashboard view
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
