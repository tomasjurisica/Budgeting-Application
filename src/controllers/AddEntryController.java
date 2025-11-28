//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package controllers;

import java.time.LocalDate;
import usecases.addentry.AddEntryInteractor;
import usecases.addentry.AddEntryRequestModel;

public class AddEntryController {
    private final AddEntryInteractor interactor;

    public AddEntryController(AddEntryInteractor interactor) {
        this.interactor = interactor;
    }

    public void addEntry(String name, String category, float amount, LocalDate date) {
        AddEntryRequestModel request = new AddEntryRequestModel(name, category, amount, date);
        this.interactor.execute(request);
    }
}
