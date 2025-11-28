package app;

import BudgetingObjects.*;
import repositories.InMemoryEntryRepository;
import viewmodels.HomePageViewModel;
import ui.HomePage;

import javax.swing.*;

public class AppBuilder {
    private Household household;
    private InMemoryEntryRepository repo;
    private HomePageViewModel viewModel;

    public AppBuilder() {
        User u = new User("Name");
        u.addEntry(new Entry("Food", "Groceries", -120, java.time.LocalDate.now()));
        u.addEntry(new Entry("Health", "Health", -80, java.time.LocalDate.now()));
        household = new Household();
        household.addUser(u);

        repo = new InMemoryEntryRepository();
        for (User user : household.getUsers()) {
            for (Entry e : user.getEntries()) {
                repo.addEntry(e);
            }
        }
        viewModel = new HomePageViewModel();
        viewModel.setEntries(repo.GetAllEntries());
    }

    public JFrame build() {
        return new HomePage(household, viewModel);
    }
}
