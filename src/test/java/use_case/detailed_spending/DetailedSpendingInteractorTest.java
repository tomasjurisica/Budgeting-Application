package use_case.detailed_spending;

import entity.Entry;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DetailedSpendingInteractorTest {

    @Test
    void successCase_returnsFilteredPurchases() {
        // Fake DAO returns 2 entries, only 1 matches category
        DetailedSpendingUserDataAccessInterface fakeDAO = new DetailedSpendingUserDataAccessInterface() {
            @Override
            public List<Entry> getEntries(String username, int year, int month) {
                return List.of(
                        new Entry("Coffee", "Food", 20, LocalDate.of(2024, 11, 1)),
                        new Entry("Rent", "Bills", 100, LocalDate.of(2024, 11, 2))
                );
            }
        };

        DetailedSpendingInputData input = new DetailedSpendingInputData(
                "Alice",
                "Food",
                11,
                2024
        );

        DetailedSpendingOutputBoundary successPresenter = new DetailedSpendingOutputBoundary() {
            @Override
            public void prepareSuccessView(DetailedSpendingOutputData outputData) {
                assertEquals("Food", outputData.getCategoryName());
                assertEquals(1, outputData.getPurchases().size());

                DetailedSpendingOutputData.Purchase p = outputData.getPurchases().get(0);
                assertEquals("Coffee", p.purchaseName());
                assertEquals(LocalDate.of(2024,11,1), p.date());
                assertEquals(20, p.amount());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Did not expect failure: " + error);
            }
        };

        DetailedSpendingInputBoundary interactor = new DetailedSpendingInteractor(fakeDAO, successPresenter);
        interactor.execute(input);
    }

    @Test
    void failCase_whenNoMatchingPurchases() {

        final boolean[] failCalled = {false};
        final boolean[] successCalled = {false};

        DetailedSpendingUserDataAccessInterface fakeDAO = new DetailedSpendingUserDataAccessInterface() {
            @Override
            public List<Entry> getEntries(String username, int year, int month) {
                return List.of(
                        new Entry("Tea", "Drinks", 20, LocalDate.of(2024, 11, 1)),
                        new Entry("Tea", "Drinks", 50, LocalDate.of(2024, 11, 2))
                );
            }
        };

        DetailedSpendingInputData input = new DetailedSpendingInputData(
                "Bob",
                "Food",
                11,
                2024
        );

        DetailedSpendingOutputBoundary presenter = new DetailedSpendingOutputBoundary() {

            @Override
            public void prepareFailView(String error) {
                failCalled[0] = true;
                assertEquals("No purchases were found for Food", error);
            }

            @Override
            public void prepareSuccessView(DetailedSpendingOutputData outputData) {
                successCalled[0] = true;
                fail("Success should NOT be called when there are no matching purchases.");
            }
        };

        DetailedSpendingInputBoundary interactor = new DetailedSpendingInteractor(fakeDAO, presenter);
        interactor.execute(input);

        assertTrue(failCalled[0]);
        assertFalse(successCalled[0]);
    }
}