package use_case.login;

import entity.Household;

public record LoginOutputData(String username, Household household) {
}
