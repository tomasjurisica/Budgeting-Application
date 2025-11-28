package entity;

/**
 * Factory for creating CommonUser objects.
 */
public class HouseholdFactory {

    public Household create(String name, String password) {
        return new Household(name, password);
    }
}
