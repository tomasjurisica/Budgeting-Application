//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package usecases.addentry;

import BudgetingObjects.Entry;
import java.time.LocalDate;

public class AddEntryRequestModel {
    public final String name;
    public final String category;
    public final float amount;
    public final LocalDate date;

    public AddEntryRequestModel(String name, String category, float amount, LocalDate date) {
        this.name = name;
        this.category = category;
        this.amount = amount;
        this.date = date;
    }

    public Entry toEntry() {
        return new Entry(this.name, this.category, this.amount, this.date);
    }
}
