package use_case.detailed_spending;
import BudgetingObjects.Entry;
// GOOD
import java.util.List;

/**
 * The data access interface for the Detailed Spending Use Case.
 */
public interface DetailedSpendingUserDataAccessInterface {

    /**
     * Returns all entries for the given user in the given year and month.
     */
    List<Entry> getEntries(String username, int year, int month);
}
