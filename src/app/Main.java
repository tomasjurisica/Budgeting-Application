import BudgetingObjects.*;
import controllers.AddEntryController;
import presenters.AddEntryPresenterImpl;
import repositories.InMemoryEntryRepository;
import usecases.addentry.AddEntryInteractor;
import viewmodels.HomePageViewModel;
import ui.HomePage;

public class Main {
    public static void main(String[] args) {

        System.out.println("MAIN START");

        User u = new User("Name");
        u.addEntry(new Entry("Food", "Groceries", -120, java.time.LocalDate.now()));
        u.addEntry(new Entry("Clothes", "Shopping", -80, java.time.LocalDate.now()));
        Household household = new Household();
        household.addUser(u);


        System.out.println("MAIN START 2");

        InMemoryEntryRepository repo = new InMemoryEntryRepository();
        for (User user : household.getUsers()) {
            for (Entry e : user.getEntries()) repo.addEntry(e);
        }

        System.out.println("MAIN START 3");


        HomePageViewModel vm = new HomePageViewModel();
        vm.setEntries(repo.GetAllEntries());

        System.out.println("MAIN START 4");

        AddEntryPresenterImpl presenter = new AddEntryPresenterImpl(vm);
        AddEntryInteractor interactor = new AddEntryInteractor(repo, presenter);
        AddEntryController controller = new AddEntryController(interactor);

        System.out.println("MAIN START 5");

        new HomePage(household, controller, vm);
    }
}