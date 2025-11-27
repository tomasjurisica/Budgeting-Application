package test.use_case.add_entry;

import BudgetingObjects.Entry;
import org.junit.Test;

import use_case.add_entry.AddEntryInputData;
import use_case.add_entry.AddEntryInteractor;
import use_case.add_entry.AddEntryOutputBoundary;
import use_case.add_entry.AddEntryOutputData;
import use_case.add_entry.AddEntryUserDataAccessInterface;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


/**
 * Unit tests for AddEntryInteractor.
 */
public class AddEntryInteractorTest {

    /**
     * Simple in-memory implementation of the data access interface
     * so we can check what entries get saved.
     */
    private static class InMemoryUserDataAccess implements AddEntryUserDataAccessInterface {

        private final List<Entry> entries = new ArrayList<>();

        @Override
        public void addEntry(Entry entry) {
            entries.add(entry);
        }

        @Override
        public List<Entry> getAllEntries() {
            return new ArrayList<>(entries);
        }
    }

    /**
     * Presenter stub so we can see what the interactor tries to show.
     */
    private static class AddEntryPresenterStub implements AddEntryOutputBoundary {

        AddEntryOutputData lastSuccess;
        String lastError;
        boolean successCalled;
        boolean failCalled;

        @Override
        public void prepareSuccessView(AddEntryOutputData outputData) {
            lastSuccess = outputData;
            successCalled = true;
        }

        @Override
        public void prepareFailView(String errorMessage) {
            lastError = errorMessage;
            failCalled = true;
        }
    }

    @Test
    public void testAddEntrySuccess() {
        InMemoryUserDataAccess dao = new InMemoryUserDataAccess();
        AddEntryPresenterStub presenter = new AddEntryPresenterStub();
        AddEntryInteractor interactor = new AddEntryInteractor(dao, presenter);

        LocalDate date = LocalDate.of(2025, 10, 5);
        AddEntryInputData input = new AddEntryInputData(
                "Groceries", "Food", -50.0f, date);

        interactor.execute(input);

        // Presenter should be called with success
        assertTrue(presenter.successCalled);
        assertFalse(presenter.failCalled);
        assertNotNull(presenter.lastSuccess);
        assertEquals("Groceries", presenter.lastSuccess.getName());
        assertEquals("Food", presenter.lastSuccess.getCategory());
        assertEquals(-50.0f, presenter.lastSuccess.getAmount(), 0.0001);
        assertEquals(date, presenter.lastSuccess.getDate());
        assertEquals("Entry added successfully.", presenter.lastSuccess.getMessage());
        assertFalse(presenter.lastSuccess.isUseCaseFailed());

        // DAO should have exactly one entry saved
        List<Entry> saved = dao.getAllEntries();
        assertEquals(1, saved.size());
        Entry e = saved.get(0);
        assertEquals("Groceries", e.getName());
        assertEquals("Food", e.getCategory());
        assertEquals(-50.0f, e.getAmount(), 0.0001);
        assertEquals(date, e.getDate());
    }

    @Test
    public void testAddEntryFailsWhenNameEmpty() {
        InMemoryUserDataAccess dao = new InMemoryUserDataAccess();
        AddEntryPresenterStub presenter = new AddEntryPresenterStub();
        AddEntryInteractor interactor = new AddEntryInteractor(dao, presenter);

        AddEntryInputData input = new AddEntryInputData(
                "   ", "Food", -50.0f, LocalDate.now());

        interactor.execute(input);

        // Should call fail view and not save anything
        assertFalse(presenter.successCalled);
        assertTrue(presenter.failCalled);
        assertEquals("Entry name cannot be empty.", presenter.lastError);
        assertTrue(dao.getAllEntries().isEmpty());
    }

    @Test
    public void testCategoryDefaultsToNAWhenBlank() {
        InMemoryUserDataAccess dao = new InMemoryUserDataAccess();
        AddEntryPresenterStub presenter = new AddEntryPresenterStub();
        AddEntryInteractor interactor = new AddEntryInteractor(dao, presenter);

        AddEntryInputData input = new AddEntryInputData(
                "Paycheck", "   ", 200.0f, LocalDate.now());

        interactor.execute(input);

        assertTrue(presenter.successCalled);
        List<Entry> saved = dao.getAllEntries();
        assertEquals(1, saved.size());
        Entry e = saved.get(0);
        assertEquals("N/A", e.getCategory());
    }
}
