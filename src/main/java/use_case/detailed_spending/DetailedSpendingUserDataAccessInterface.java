package use_case.detailed_spending;
import entity.Entry;
// GOOD
import java.util.List;

/**
 * The data access interface for the Detailed Spending Use Case.
 */
public interface DetailedSpendingUserDataAccessInterface {

    /**
     * Returns all household entries for the given household in the given year and month.
     * @param householdID the household ID.
     * @param year year of the entries wanted.
     * @param month of the entries wanted.
     * @return all the household entries for the given household in the given year and month.
     */
    List<Entry> getEntries(String householdID, int year, int month);
}
