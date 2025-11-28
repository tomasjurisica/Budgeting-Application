//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package usecases.addentry;

import BudgetingObjects.Entry;

public class AddEntryResponseModel {
    public final boolean success;
    public final String message;
    public final Entry entry;

    public AddEntryResponseModel(boolean success, String message, Entry entry) {
        this.success = success;
        this.message = message;
        this.entry = entry;
    }
}
