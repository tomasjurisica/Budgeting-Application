package app;

import javax.swing.*;


public class Main {
    public static void main(String[] args) {
        System.out.println("Working directory: " + System.getProperty("user.dir"));

        AppBuilder appBuilder = new AppBuilder();
        JFrame application = appBuilder
                .addSignupView()
                .addLoginView()
                .addLoggedInView()
                .addHomePageView()
                .addAddHouseholdEntryView()
                .addDetailedSpendingView()
                .addSignupUseCase()
                .addLoginUseCase()
                .addLogoutUseCase()
                .addHouseholdEntryUseCase()
                .initBudgetingObjects()
                .addSelectUserUseCase()
                .addDetailedSpendingUseCase()
                .build();

        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}