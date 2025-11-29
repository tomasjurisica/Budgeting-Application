package app;

import data_access.FileUserDataAccessObject;
import entity.Entry;
import entity.Household;
import entity.HouseholdFactory;
import entity.User;
import interface_adapter.ViewManagerModel;
import interface_adapter.home_page.HomePageViewModel;
import interface_adapter.household_dashboard.HouseholdDashboardViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutPresenter;
import interface_adapter.select_user.SelectUserController;
import interface_adapter.select_user.SelectUserPresenter;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import view.HomePageView;
import view.AddHouseholdEntryView;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInteractor;
import use_case.logout.LogoutOutputBoundary;
import use_case.select_user.SelectUserInputBoundary;
import use_case.select_user.SelectUserInteractor;
import use_case.select_user.SelectUserOutputBoundary;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInteractor;
import use_case.signup.SignupOutputBoundary;
import view.HouseholdDashboardView;
import view.LoginView;
import view.SignupView;
import view.ViewManager;
import interface_adapter.add_household_entry.*;
import use_case.add_household_entry.*;

import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;

public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    final HouseholdFactory householdFactory = new HouseholdFactory();
    final ViewManagerModel viewManagerModel = new ViewManagerModel();
    ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    // set which data access implementation to use, can be any
    // of the classes from the data_access package

    // DAO version using local file storage
    final FileUserDataAccessObject userDataAccessObject =
            new FileUserDataAccessObject("src/main/java/data_access/users.json", householdFactory);

    // DAO version using a shared external database
    // final DBUserDataAccessObject userDataAccessObject = new DBUserDataAccessObject(userFactory);

    private SignupView signupView;
    private SignupViewModel signupViewModel;
    private LoginViewModel loginViewModel;
    private HouseholdDashboardViewModel householdDashboardViewModel;
    private HouseholdDashboardView householdDashboardView;
    private LoginView loginView;
    private Household household;
    private interface_adapter.home_page.HomePageViewModel homePageViewModel;
    private HomePageView homePage;
    private AddHouseholdEntryView addHouseholdEntryView;
    private AddHouseholdEntryViewModel addHouseholdEntryViewModel;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    public AppBuilder addSignupView() {
        signupViewModel = new SignupViewModel();
        signupView = new SignupView(signupViewModel);
        cardPanel.add(signupView, signupView.getViewName());
        return this;
    }

    public AppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel);
        cardPanel.add(loginView, loginView.getViewName());
        return this;
    }

    public AppBuilder addLoggedInView() {
        householdDashboardViewModel = new HouseholdDashboardViewModel(); // interactor injected after login
        householdDashboardView = new HouseholdDashboardView(householdDashboardViewModel);
        cardPanel.add(householdDashboardView, householdDashboardView.getViewName());
        return this;
    }

    public AppBuilder addHomePageView() {
        // Ensure homePageViewModel is initialized
        if (homePageViewModel == null) {
            homePageViewModel = new HomePageViewModel();
        }
        homePageViewModel.setDao(userDataAccessObject);
        homePage = new HomePageView(homePageViewModel, userDataAccessObject);
        cardPanel.add(homePage, homePage.getViewName());
        // Wire navigation
        homePage.setViewManagerModel(viewManagerModel);
        // Wire AddHouseholdEntryView if it already exists
        wireHomePageAndAddEntryView();
        return this;
    }

    public AppBuilder addAddHouseholdEntryView() {
        if (addHouseholdEntryViewModel == null) {
            addHouseholdEntryViewModel = new AddHouseholdEntryViewModel();
        }
        if (addHouseholdEntryView == null) {
            addHouseholdEntryView = new AddHouseholdEntryView(addHouseholdEntryViewModel);
            cardPanel.add(addHouseholdEntryView, addHouseholdEntryView.getViewName());
        }
        // Wire AddHouseholdEntryView to HomePageView if homePage already exists
        wireHomePageAndAddEntryView();
        return this;
    }


    public AppBuilder addSignupUseCase() {
        final SignupOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel,
                signupViewModel, loginViewModel);
        final SignupInputBoundary userSignupInteractor = new SignupInteractor(
                userDataAccessObject, signupOutputBoundary, householdFactory);

        SignupController controller = new SignupController(userSignupInteractor);
        signupView.setSignupController(controller);
        return this;
    }

    public AppBuilder addLoginUseCase() {
        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(
                viewManagerModel,
                householdDashboardViewModel,
                loginViewModel,
                signupViewModel,
                userDataAccessObject
        );
        final LoginInputBoundary loginInteractor = new LoginInteractor(
                userDataAccessObject, loginOutputBoundary, homePageViewModel);

        LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);
        return this;
    }

    /**
     * Adds the Logout Use Case to the application.
     *
     * @return this builder
     */
    public AppBuilder addLogoutUseCase() {
        final LogoutOutputBoundary logoutOutputBoundary = new LogoutPresenter(viewManagerModel,
                householdDashboardViewModel, loginViewModel);

        final LogoutInputBoundary logoutInteractor =
                new LogoutInteractor(userDataAccessObject, logoutOutputBoundary);

        final LogoutController logoutController = new LogoutController(logoutInteractor);
        householdDashboardView.setLogoutController(logoutController);
        return this;
    }

    /**
     * Adds the Add Household Entry Use Case to the application
     *
     * @return this builder
     */
    public AppBuilder addHouseholdEntryUseCase() {
        // Ensure view model and view are initialized
        if (addHouseholdEntryViewModel == null) {
            addHouseholdEntryViewModel = new AddHouseholdEntryViewModel();
        }
        if (addHouseholdEntryView == null) {
            addHouseholdEntryView = new AddHouseholdEntryView(addHouseholdEntryViewModel);
            cardPanel.add(addHouseholdEntryView, addHouseholdEntryView.getViewName());
        }

        final AddHouseholdEntryOutputBoundary addHouseholdEntryOutputBoundary = new
                AddHouseholdEntryPresenter(addHouseholdEntryViewModel);

        final AddHouseholdEntryInputBoundary addHouseholdInteractor =
                new AddHouseholdEntryInteractor(userDataAccessObject, addHouseholdEntryOutputBoundary);

        final AddHouseholdEntryController addHouseholdEntryController =
                new AddHouseholdEntryController(addHouseholdInteractor);
        addHouseholdEntryView.setAddHouseholdEntryController(addHouseholdEntryController);

        // Wire ViewManagerModel to AddHouseholdEntryView for navigation
        addHouseholdEntryView.setViewManagerModel(viewManagerModel);

        // Wire AddHouseholdEntryView to HomePageView for navigation
        if (homePage != null) {
            homePage.setAddHouseholdEntryView(addHouseholdEntryView);
        }
        return this;
    }

    // Helper method to ensure homePage and addHouseholdEntryView are wired
    private void wireHomePageAndAddEntryView() {
        if (homePage != null && addHouseholdEntryView != null) {
            homePage.setAddHouseholdEntryView(addHouseholdEntryView);
        }
    }

    /**
     * Adds the SelectUser Use Case to the application.
     *
     * @return this builder
     */
    public AppBuilder addSelectUserUseCase() {
        // Ensure homePageViewModel and homePageView are initialized
        FileUserDataAccessObject dao = new FileUserDataAccessObject("src/main/java/data_access/users.json", new HouseholdFactory());
        if (homePageViewModel == null) {
            homePageViewModel = new HomePageViewModel();
        }
        if (homePage == null) {
            homePage = new HomePageView(homePageViewModel, dao);
            cardPanel.add(homePage, homePage.getViewName());
        }

        final SelectUserOutputBoundary selectUserPresenter = new SelectUserPresenter(
                homePageViewModel,
                viewManagerModel,
                householdDashboardViewModel,
                homePage
        );

        final SelectUserInputBoundary selectUserInteractor = new SelectUserInteractor(selectUserPresenter);

        final SelectUserController selectUserController = new SelectUserController(selectUserInteractor);
        householdDashboardView.setSelectUserController(selectUserController);
        return this;
    }

    public AppBuilder initBudgetingObjects() {
        User u = new User("Name");
        ArrayList<Entry> foodEntry = new ArrayList<>();
        foodEntry.add(new Entry("Food", "Groceries", -120, java.time.LocalDate.now()));
        u.addEntry(foodEntry);

        ArrayList<Entry> healthEntry = new ArrayList<>();
        healthEntry.add(new Entry("Health", "Health", -80, java.time.LocalDate.now()));
        u.addEntry(healthEntry);

        household = new Household("defaultPassword", "defaultID");
        household.addUser(u);

        userDataAccessObject.save(household);

        userDataAccessObject.setCurrentUsername(household.getHouseholdID());

        homePageViewModel = new HomePageViewModel();
        homePageViewModel.setEntries(userDataAccessObject.get(household.getHouseholdID()).getAllEntries());

        return this;
    }

    public JFrame build() {
        final JFrame application = new JFrame("User Login Example");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        viewManagerModel.setState(signupView.getViewName());
        viewManagerModel.firePropertyChange();

        return application;
    }


}
