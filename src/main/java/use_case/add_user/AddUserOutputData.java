package use_case.add_user;

import entity.User;

public class AddUserOutputData {
    private final User user;

    public AddUserOutputData(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
