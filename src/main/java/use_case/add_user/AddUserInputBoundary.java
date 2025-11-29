/**
 * The input boundary for the Add User use case.
 * <p>
 * This interface defines the request model for adding a new user.
 */

package use_case.add_user;

public interface AddUserInputBoundary {
    void addUser(AddUserInputData inputData);
}
