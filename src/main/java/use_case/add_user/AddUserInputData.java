package use_case.add_user;

public class AddUserInputData {
    private final String name;

    public AddUserInputData(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
