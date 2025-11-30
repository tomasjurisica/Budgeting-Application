package use_case.home_display;

import entity.Entry;
import interface_adapter.home_page.HomePageViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HomeDisplayInteractorTest {

    private HomePageViewModel viewModel;
    private HomeDisplayInteractor interactor;

    @BeforeEach
    void setUp() {
        viewModel = new HomePageViewModel();
        interactor = new HomeDisplayInteractor(viewModel);
    }

    @Test
    void testExecuteForwardsEntriesToViewModel() {
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry("Food", "Groceries", -100, java.time.LocalDate.now()));
        entries.add(new Entry("Health", "Medicine", -50, java.time.LocalDate.now()));

        HomeDisplayRequestModel request = new HomeDisplayRequestModel(entries);
        interactor.execute(request);

        assertEquals(entries, viewModel.getEntries(), "ViewModel should receive same entries");
    }

    @Test
    void testPresenterImplSetsViewModelEntries() {
        HomeDisplayPresenterImpl presenter = new HomeDisplayPresenterImpl(viewModel);

        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry("Transport", "Bus fare", -20, java.time.LocalDate.now()));

        presenter.presentHomeData(entries);

        assertEquals(entries, viewModel.getEntries(), "Presenter should update ViewModel entries");
    }

    @Test
    void testEmptyEntriesList() {
        List<Entry> emptyEntries = new ArrayList<>();
        HomeDisplayRequestModel request = new HomeDisplayRequestModel(emptyEntries);

        interactor.execute(request);

        assertTrue(viewModel.getEntries().isEmpty(), "ViewModel should have empty list when given empty input");
    }

    @Test
    void testViewModelNotNull() {
        assertNotNull(viewModel, "ViewModel should be initialized in setUp()");
    }

    @Test
    void testResponseModelContainsEntries() {
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry("Food", "Snacks", -30, java.time.LocalDate.now()));
        HomeDisplayResponseModel response = new HomeDisplayResponseModel(entries);

        assertEquals(entries, response.entries, "Response model should store the entries list");
    }

    @Test
    void testRequestModelReturnsEntries() {
        List<Entry> entries = new ArrayList<>();
        HomeDisplayRequestModel request = new HomeDisplayRequestModel(entries);
        assertEquals(entries, request.getEntries(), "Request model should return the same entries list");
    }
}
