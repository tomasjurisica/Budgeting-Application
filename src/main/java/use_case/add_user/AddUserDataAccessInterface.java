package use_case.add_user;

import entity.Household;

public interface AddUserDataAccessInterface {
    void save(Household household);
}