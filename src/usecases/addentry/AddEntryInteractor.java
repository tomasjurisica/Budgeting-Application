//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package usecases.addentry;

import BudgetingObjects.Entry;
import java.time.LocalDate;
import presenters.AddEntryPresenter;
import repositories.EntryRepository;

public class AddEntryInteractor {
    private final EntryRepository repository;
    private final AddEntryPresenter presenter;

    public AddEntryInteractor(EntryRepository repository, AddEntryPresenter presenter) {
        this.repository = repository;
        this.presenter = presenter;
    }

    public void execute(AddEntryRequestModel request) {
        if (request == null) {
            this.presenter.present(new AddEntryResponseModel(false, "Request cannot be null", (Entry)null));
        } else if (request.name != null && !request.name.trim().isEmpty()) {
            if (request.category != null && !request.category.trim().isEmpty()) {
                if (Math.abs(request.amount) < 1.0E-4F) {
                    this.presenter.present(new AddEntryResponseModel(false, "Amount cannot be negative", (Entry)null));
                } else {
                    if (request.date != null) {
                        LocalDate var10000 = request.date;
                    } else {
                        LocalDate.now();
                    }

                    Entry entry = request.toEntry();
                    this.repository.addEntry(entry);
                    this.presenter.present(new AddEntryResponseModel(true, "added", entry));
                }
            } else {
                this.presenter.present(new AddEntryResponseModel(false, "Category cannot be empty", (Entry)null));
            }
        } else {
            this.presenter.present(new AddEntryResponseModel(false, "Name cannot be empty", (Entry)null));
        }
    }
}
