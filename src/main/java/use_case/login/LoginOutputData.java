package use_case.login;

import entity.Household;

public class LoginOutputData {
    private final String username;
    private final Household household;

    public LoginOutputData(String username, Household household) {
        this.username = username;
        this.household = household;
    }

    public String getUsername() { return username; }
    public Household getHousehold() { return household; }
}
